package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tenant.config.TenantDatabaseSchemaUpdate;

@RestController
public class TestingController {

	
	@Autowired
	TenantDatabaseSchemaUpdate tenantConfig;
	
	@PostMapping("/api/v1")
	public String postRequest(@RequestParam Integer id) {
//		tenantConfig.masterEntityManagerFactory(id);
		tenantConfig.update();
		return "s";
	}
}
