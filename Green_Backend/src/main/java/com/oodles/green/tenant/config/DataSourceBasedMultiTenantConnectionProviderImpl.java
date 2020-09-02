package com.oodles.green.tenant.config;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.oodles.green.master.config.DBContextHolder;
import com.oodles.green.master.entity.OrganisationDatabaseDetail;
import com.oodles.green.master.repository.OrganisationDatabaseDetailRepository;
import com.oodles.green.util.DataSourceUtil;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

	public static final String SCRIPT_FILE = "exportScript.sql";

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	private OrganisationDatabaseDetailRepository masterTenantRepository;

	@Override
	protected DataSource selectAnyDataSource() {
		boolean flag = true;
		if (dataSourcesMtApp.isEmpty()) {
			List<OrganisationDatabaseDetail> masterTenants = masterTenantRepository.findAll();
			System.out.println("selectAnyDataSource() method call...Total tenants:" + masterTenants.size());
			for (OrganisationDatabaseDetail masterTenant : masterTenants) {
				System.out.println("inside the loop " + masterTenant);
				dataSourcesMtApp.put(masterTenant.getDataBaseName(),
						DataSourceUtil.createAndConfigureDataSource(masterTenant));
				update(masterTenant);
			}
		}
		return this.dataSourcesMtApp.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		tenantIdentifier = initializeTenantIfLost(tenantIdentifier);
		if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
			List<OrganisationDatabaseDetail> masterTenants = masterTenantRepository.findAll();
			System.out.println("selectDataSource() method call...Tenant:" + tenantIdentifier + " Total tenants:"
					+ masterTenants.size());
			for (OrganisationDatabaseDetail masterTenant : masterTenants) {
				dataSourcesMtApp.put(masterTenant.getDataBaseName(),
						DataSourceUtil.createAndConfigureDataSource(masterTenant));
			}
		}
		if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
			System.out.println(
					"Trying to get tenant:" + tenantIdentifier + " which was not found in master db after rescan");
            throw new UsernameNotFoundException(String.format("Tenant not found after rescan, " + " tenant=%s",
                    tenantIdentifier));
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

	private void update(OrganisationDatabaseDetail master) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Environment.HBM2DDL_AUTO, "update");
		map.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
		map.put(Environment.DRIVER, master.getDatabaseDriverClass());
		map.put(Environment.URL, master.getDataBaseUrl());
		map.put(Environment.USER, master.getDataBaseUserName());
		map.put(Environment.PASS, master.getDataBasePassword());
		map.put(Environment.SHOW_SQL, "true");
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(map).build();
		Set<Class<? extends Object>> classes = getClassInPackage("com.oodles.green.tenant");
		MetadataSources sources = new MetadataSources(standardRegistry);
		for (Class<?> entityClass : classes) {
			sources.addAnnotatedClass(entityClass);
		}
		MetadataImplementor metadata = (MetadataImplementor) sources.getMetadataBuilder().build();
		EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.SCRIPT);
		try {
			Files.delete(Paths.get(SCRIPT_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		SchemaUpdate update = new SchemaUpdate();
		update.execute(EnumSet.of(TargetType.DATABASE), metadata);
		System.out.println("Schema update ");
		SchemaExport schemaExport = new SchemaExport();
		schemaExport.setDelimiter(";");
		schemaExport.setOutputFile(SCRIPT_FILE);
		schemaExport.setFormat(true);
		schemaExport.createOnly(targetTypes, metadata);
	}
}
