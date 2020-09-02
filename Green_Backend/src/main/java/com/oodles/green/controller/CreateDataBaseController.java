package com.oodles.green.controller;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Environment;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.hbm2ddl.SchemaExport.Action;
import org.hibernate.tool.schema.TargetType;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.oodles.green.master.entity.OrganisationDatabaseDetail;
import com.oodles.green.master.repository.OrganisationDatabaseDetailRepository;
import com.zaxxer.hikari.HikariDataSource;

@RestController
public class CreateDataBaseController {

	@Autowired
	private OrganisationDatabaseDetailRepository organisationDatabaseDetailRepository;
	
	 public static final String SCRIPT_FILE = "exportScript.sql";
	 private final String SAMPLE_DATA = "classpath:exportScript.sql";
	 
	@PostMapping("/create")
	public String createDataBase() {
		OrganisationDatabaseDetail detail=databaseCreation("tenant11");
		System.out.println(detail);
		try {
			update(detail);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "completed";
	}
	
	
	 private OrganisationDatabaseDetail databaseCreation(String dataBaseName){
         final String DB_URL = "jdbc:mysql://localhost:3306/";
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,"root", "root");
            //STEP 4: Execute a query
            System.out.println("Creating database..."+dataBaseName);
            stmt = conn.createStatement();
            String sql = "CREATE DATABASE "+dataBaseName.toLowerCase();
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
            OrganisationDatabaseDetail detail=new OrganisationDatabaseDetail();
            detail.setDatabaseDriverClass("com.mysql.cj.jdbc.Driver");
            detail.setDataBaseName(dataBaseName.toLowerCase());
            detail.setDataBasePassword("root");
            detail.setDataBaseUrl(DB_URL+""+dataBaseName.toLowerCase());
            detail.setDataBaseUserName("root");
            detail.setInformationUpdated(false);
            detail.setOrganisationEmailId("mm@oodlestechnologies.com");
            detail.setOrganisationEmailSignature("@oodlestechnologies.com");
            detail.setOrganisationPassword("Krishnasingh@1");
            OrganisationDatabaseDetail organisationDatabaseDetail=organisationDatabaseDetailRepository.save(detail);
            return detail;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }//end try
        System.out.println("Goodbye!");
return null;
    }
	 
	 private Set<Class<? extends Object>> getClassInPackage(String packagePath) {
			Reflections reflections = new Reflections(packagePath, new SubTypesScanner(false));
			Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
			return allClasses;
		}
	 private static SchemaExport getSchemaExport() {
		 
	       SchemaExport export = new SchemaExport();
	       // Script file.
	       File outputFile = new File(SCRIPT_FILE);
	       String outputFilePath = outputFile.getAbsolutePath(); 
	       System.out.println("Export file: " + outputFilePath); 
	       export.setDelimiter(";");
	       export.setOutputFile(outputFilePath);        
	       // No Stop if Error
	       export.setHaltOnError(false);
	       //
	       return export;
	   }
	 public static void dropDataBase(SchemaExport export, Metadata metadata) {
	       // TargetType.DATABASE - Execute on Databse
	       // TargetType.SCRIPT - Write Script file.
	       // TargetType.STDOUT - Write log to Console.
	       EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT, TargetType.STDOUT);
	 
	       export.drop(targetTypes, metadata);
	   }
	 
	 public static void createDataBase(SchemaExport export, Metadata metadata) {
	       // TargetType.DATABASE - Execute on Databse
	       // TargetType.SCRIPT - Write Script file.
	       // TargetType.STDOUT - Write log to Console.
	  
	       EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT, TargetType.STDOUT);
	 
	       SchemaExport.Action action = SchemaExport.Action.CREATE;
	       //
	       export.execute(targetTypes, action, metadata);
	 
	       System.out.println("Export OK");       
	   }
	 

		public void update(OrganisationDatabaseDetail organisationDatabaseDetail) throws ScriptException, SQLException {
			HikariDataSource ds = new HikariDataSource();
	        ds.setUsername(organisationDatabaseDetail.getDataBaseUserName());
	        ds.setPassword(organisationDatabaseDetail.getDataBasePassword());
	        ds.setJdbcUrl(organisationDatabaseDetail.getDataBaseUrl());
	        ds.setDriverClassName(organisationDatabaseDetail.getDatabaseDriverClass());
	        // HikariCP settings - could come from the master_tenant table but
	        // hardcoded here for brevity
	        // Maximum waiting time for a connection from the pool
	        ds.setConnectionTimeout(20000);
	        // Minimum number of idle connections in the pool
	        ds.setMinimumIdle(3);
	        // Maximum number of actual connection in the pool
	        ds.setMaximumPoolSize(500);
	        // Maximum time that a connection is allowed to sit idle in the pool
	        ds.setIdleTimeout(300000);
	        ds.setConnectionTimeout(20000);
	        // Setting up a pool name for each tenant datasource
	        String tenantConnectionPoolName = organisationDatabaseDetail.getDataBaseName() + "-connection-pool";
	        ds.setPoolName(tenantConnectionPoolName);
	        File outputFile = new File(SCRIPT_FILE);
		       String outputFilePath = outputFile.getAbsolutePath(); 
		       System.out.println("Export file: " + outputFilePath);
		       
	       ScriptUtils.executeSqlScript(ds.getConnection(),new FileSystemResource("exportScript.sql")); 
		}
}
