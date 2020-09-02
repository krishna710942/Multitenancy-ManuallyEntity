package com.oodles.green.tenant.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.oodles.green.tenant.entity.OrgUnit;

@Repository
public interface OrgUnitRepository  extends JpaRepository<OrgUnit, Long>{
	
	public List<OrgUnit> findAll();

	Optional<OrgUnit> findById(Long id);

	@SuppressWarnings("unchecked")
	public OrgUnit save(OrgUnit orgUnit);

	public void delete(OrgUnit orgUnit);
	@Query("SELECT o FROM OrgUnit o WHERE o.id = ?1")
	public OrgUnit findByOrgId(Long orgId);

}
