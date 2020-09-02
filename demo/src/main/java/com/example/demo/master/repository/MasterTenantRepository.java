package com.example.demo.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.master.entity.MasterTenant;

public interface MasterTenantRepository extends JpaRepository<MasterTenant, Integer> {
    MasterTenant findByTenantClientId(Integer clientId);

	MasterTenant findByEmailId(String emailId);
}
