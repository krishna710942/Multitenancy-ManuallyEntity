package com.oodles.green.tenant.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PermissionsSaved implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "permission_Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long permissionId;
	@Column(name = "permission_Name", nullable = false,unique = true)
	private String permissionName;
	@Column(name = "special_Permission", nullable = false,unique = false)
	
	private String specialPermission;

	private Boolean isRead;

	private Boolean isWrite;
	
	private Boolean type;


}
