package com.oodles.green.tenant.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrgUnit implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_OrgUnit", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idOrgUnit;
	@Column(name = "org_Unit_Name", nullable = false, unique = true)
	private String orgUnitName;
	@Column(name = "org_Unit_Desc", nullable = false )
	private String orgUnitDesc;


}
