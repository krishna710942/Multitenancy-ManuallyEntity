package com.oodles.green.master.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/*
Author : krishna pratap singh
 */
@Entity
@Getter
@Setter
@Table(name = "organisation_database_detail")
@NoArgsConstructor
public class OrganisationDatabaseDetail{

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "tenant_client_id")
	private Long id;
    private String dataBaseName;
    private String dataBaseUserName;
    private String dataBasePassword;

    private String databaseDriverClass;
    private String dataBaseUrl;
    private String organisationEmailId;
    private String organisationPassword;
    private boolean isInformationUpdated;

    private String organisationEmailSignature;

	public OrganisationDatabaseDetail(Long id, String dataBaseName, String dataBaseUserName, String dataBasePassword,
			String databaseDriverClass, String dataBaseUrl, String organisationEmailId, String organisationPassword,
			boolean isInformationUpdated, String organisationEmailSignature) {
		super();
		this.id = id;
		this.dataBaseName = dataBaseName;
		this.dataBaseUserName = dataBaseUserName;
		this.dataBasePassword = dataBasePassword;
		this.databaseDriverClass = databaseDriverClass;
		this.dataBaseUrl = dataBaseUrl;
		this.organisationEmailId = organisationEmailId;
		this.organisationPassword = organisationPassword;
		this.isInformationUpdated = isInformationUpdated;
		this.organisationEmailSignature = organisationEmailSignature;
	}

	public OrganisationDatabaseDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getDataBaseUserName() {
		return dataBaseUserName;
	}

	public void setDataBaseUserName(String dataBaseUserName) {
		this.dataBaseUserName = dataBaseUserName;
	}

	public String getDataBasePassword() {
		return dataBasePassword;
	}

	public void setDataBasePassword(String dataBasePassword) {
		this.dataBasePassword = dataBasePassword;
	}

	public String getDatabaseDriverClass() {
		return databaseDriverClass;
	}

	public void setDatabaseDriverClass(String databaseDriverClass) {
		this.databaseDriverClass = databaseDriverClass;
	}

	public String getDataBaseUrl() {
		return dataBaseUrl;
	}

	public void setDataBaseUrl(String dataBaseUrl) {
		this.dataBaseUrl = dataBaseUrl;
	}

	public String getOrganisationEmailId() {
		return organisationEmailId;
	}

	public void setOrganisationEmailId(String organisationEmailId) {
		this.organisationEmailId = organisationEmailId;
	}

	public String getOrganisationPassword() {
		return organisationPassword;
	}

	public void setOrganisationPassword(String organisationPassword) {
		this.organisationPassword = organisationPassword;
	}

	public boolean isInformationUpdated() {
		return isInformationUpdated;
	}

	public void setInformationUpdated(boolean isInformationUpdated) {
		this.isInformationUpdated = isInformationUpdated;
	}

	public String getOrganisationEmailSignature() {
		return organisationEmailSignature;
	}

	public void setOrganisationEmailSignature(String organisationEmailSignature) {
		this.organisationEmailSignature = organisationEmailSignature;
	}

	@Override
	public String toString() {
		return "OrganisationDatabaseDetail [id=" + id + ", dataBaseName=" + dataBaseName + ", dataBaseUserName="
				+ dataBaseUserName + ", dataBasePassword=" + dataBasePassword + ", databaseDriverClass="
				+ databaseDriverClass + ", dataBaseUrl=" + dataBaseUrl + ", organisationEmailId=" + organisationEmailId
				+ ", organisationPassword=" + organisationPassword + ", isInformationUpdated=" + isInformationUpdated
				+ ", organisationEmailSignature=" + organisationEmailSignature + "]";
	}

    
}
