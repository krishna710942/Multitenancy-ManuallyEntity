package com.oodles.green.tenant.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.oodles.green.tenant.entity.OrgInformation;


@Repository
public interface OrgRepository extends JpaRepository<OrgInformation, Long> {

	@SuppressWarnings("unchecked")
	public OrgInformation save(OrgInformation orgInformation);
	
    public List<OrgInformation> findAll();
	
	Optional<OrgInformation> findById(Long id);
	@Query("SELECT o FROM OrgInformation o WHERE o.companyId = ?1")
	public OrgInformation findByOrgId(Long orgId);
	
	@Query("SELECT o FROM OrgInformation o WHERE o.companyName = ?1")
	public OrgInformation findByOrgName(String name);

    OrgInformation findByOrganisationEmail(String email);

	OrgInformation findByCompanyId(Long id);

	@Query("select og from OrgInformation og where og.isActive=true and og.organisationExpiryDate <=?1")
    List<OrgInformation> findByOrganSationExpiryDateAndActiveTrue(Date currentDate);

    Page<OrgInformation> findAll(Specification<OrgInformation> spec, Pageable page);

    @Query("Select og from OrgInformation og where og.companyId=?1")
	Page<OrgInformation> findByCompanyIds(Long companyId, Pageable page);

    @Query("select og.companyName from OrgInformation og where og.companyId=?1")
    String findByCompanyIdName(Long companyId);
}
