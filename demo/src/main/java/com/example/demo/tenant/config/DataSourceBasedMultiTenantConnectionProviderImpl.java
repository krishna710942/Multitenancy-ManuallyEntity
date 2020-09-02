package com.example.demo.tenant.config;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import com.example.demo.master.config.DBContextHolder;
import com.example.demo.master.entity.MasterTenant;
import com.example.demo.master.repository.MasterTenantRepository;
import com.example.demo.util.DataSourceUtil;
import javax.sql.DataSource;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Configuration
public class DataSourceBasedMultiTenantConnectionProviderImpl
		extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final Logger LOG = LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);

	private static final long serialVersionUID = 1L;

	private final Map<String, DataSource> dataSourcesMtApp = new TreeMap<>();

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	private MasterTenantRepository masterTenantRepository;

	@Override
	protected DataSource selectAnyDataSource() {
		if (dataSourcesMtApp.isEmpty()) {
			List<MasterTenant> masterTenants = masterTenantRepository.findAll();
			System.out.println("selectAnyDataSource() method call...Total tenants:" + masterTenants.size());
			for (MasterTenant masterTenant : masterTenants) {
				dataSourcesMtApp.put(masterTenant.getDbName(),
						DataSourceUtil.createAndConfigureDataSource(masterTenant));
//				update(masterTenant);
			}
		}
		return this.dataSourcesMtApp.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		tenantIdentifier = initializeTenantIfLost(tenantIdentifier);
		if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
			List<MasterTenant> masterTenants = masterTenantRepository.findAll();
			System.out.println("selectDataSource() method call...Tenant:" + tenantIdentifier + " Total tenants:"
					+ masterTenants.size());
			for (MasterTenant masterTenant : masterTenants) {
				dataSourcesMtApp.put(masterTenant.getDbName(),
						DataSourceUtil.createAndConfigureDataSource(masterTenant));
			}
		}
		// check again if tenant exist in map after rescan master_db, if not, throw
		// UsernameNotFoundException
		if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
			System.out.println(
					"Trying to get tenant:" + tenantIdentifier + " which was not found in master db after rescan");
//            throw new UsernameNotFoundException(String.format("Tenant not found after rescan, " + " tenant=%s",
//                    tenantIdentifier));
		}
		return this.dataSourcesMtApp.get(tenantIdentifier);
	}

	private String initializeTenantIfLost(String tenantIdentifier) {
		if (tenantIdentifier != DBContextHolder.getCurrentDb()) {
			tenantIdentifier = DBContextHolder.getCurrentDb();
		}
		return tenantIdentifier;
	}
	
	private Set<Class<? extends Object>> getClassInPackage(String packagePath) {
		Reflections reflections = new Reflections(packagePath, new SubTypesScanner(false));
		Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
		return allClasses;
	}

	public void update(MasterTenant master) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put(Environment.HBM2DDL_AUTO, "update");
			map.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
			map.put(Environment.DRIVER, master.getDriverClass());
			map.put(Environment.URL, master.getUrl());
			map.put(Environment.USER, master.getUserName());
			map.put(Environment.PASS,master.getPassword());
			map.put(Environment.SHOW_SQL, "true");
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().applySettings(map).build();
			try {
				MetadataSources metaDataSource = new MetadataSources(ssr);
				Set<Class<? extends Object>> classes = getClassInPackage("com.example.demo.tenant");
				for (Class<? extends Object> c : classes) {
					metaDataSource.addAnnotatedClass(c);
					final MetadataImplementor metadata = (MetadataImplementor) metaDataSource.buildMetadata();
					metadata.validate();
					SchemaUpdate su=new SchemaUpdate();
					su.setHaltOnError(true);
	    	        su.setDelimiter(";");
	    	        su.setFormat(true);
	    	        su.execute(EnumSet.of(TargetType.DATABASE), metadata,ssr);
				}
			} finally {
				StandardServiceRegistryBuilder.destroy(ssr);
			}
		} catch (GenericJDBCException e) {
			e.printStackTrace();
		}
	}
}
