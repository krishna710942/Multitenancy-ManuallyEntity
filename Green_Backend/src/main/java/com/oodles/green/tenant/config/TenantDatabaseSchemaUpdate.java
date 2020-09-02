package com.oodles.green.tenant.config;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Environment;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.stereotype.Service;

@Service
public class TenantDatabaseSchemaUpdate {

	private Set<Class<? extends Object>> getClassInPackage(String packagePath) {
		Reflections reflections = new Reflections(packagePath, new SubTypesScanner(false));
		Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
		return allClasses;
	}

	public void update() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put(Environment.HBM2DDL_AUTO, "update");
			map.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
			map.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
			map.put(Environment.URL, "jdbc:mysql://localhost:3306/tenant4");
			map.put(Environment.USER, "root");
			map.put(Environment.PASS, "root");
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
