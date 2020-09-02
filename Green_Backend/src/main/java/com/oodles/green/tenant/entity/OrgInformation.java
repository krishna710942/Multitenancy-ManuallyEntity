package com.oodles.green.tenant.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrgInformation implements Serializable {

	private static final long serialVersionUID = 3395656047573277687L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_Id")
	private Long companyId;

	@Column(name="driver_capacity")
	private int driverCapacity;

	@Column(name = "companyName", nullable = false, unique = true)
	private String companyName;

	@Column(name = "personnelArea", nullable = false)
	private String personnelArea;

	@Column(name = "cost_Center", nullable = false)
	private String costCenter;

	@Column(name = "chief_OrgUnit", nullable = false)
	private String chiefOrgUnit;

	@Column(name = "chief_Name", nullable = false)
	private String chiefName;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private OrgUnit orgunit;
	
	@Column(name = "org_Address", nullable = true)
	private String orgAddress;

	private String organisationPassword;

	@Column(name = "start_Time", nullable = false, updatable = true)	
	private LocalTime startTime;

	@Column(name = "end_Time", nullable = false, updatable = true)
	private LocalTime endTime;
	@Column(name = "organistaion_email")
	private String organisationEmail;
	private Date organisationExpiryDate;
	@Column(columnDefinition="tinyint(1) default 1")
	private Boolean isActive=true;

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "organisation_permission",
			joinColumns = { @JoinColumn(name = "org_information_company_id") },
			inverseJoinColumns = { @JoinColumn(name = "permissions_id" , unique = false)})
	private List<PermissionsSaved>permissionsSave;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "image_url")
	private String imageUrl;

	private Long dailyPlantSupplyVolume;

}
