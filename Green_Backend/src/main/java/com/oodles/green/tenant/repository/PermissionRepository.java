package com.oodles.green.tenant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.oodles.green.tenant.entity.PermissionsSaved;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionsSaved, Long> {

	Page<PermissionsSaved> findAll(Pageable pageable);
//	@Query("SELECT p FROM PermissionsSaved p WHERE p.orgInformation.companyId = ?1 and p.permissionName= ?2" )
//	PermissionsSaved findByNmaeAndOrg(Long companyId, String permissionName);

	@SuppressWarnings("unchecked")
	PermissionsSaved save(PermissionsSaved permissionsSaved);
	@Query("SELECT p FROM PermissionsSaved p WHERE p.permissionId = ?1")
	PermissionsSaved findByPerId(Long permissionsID);

	List<PermissionsSaved> findByPermissionName(String allOrganisation);
}
