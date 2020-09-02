package com.example.demo.master.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_tenant_master")
@Getter
@Setter
@NoArgsConstructor
public class MasterTenant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_client_id")
    private Integer tenantClientId;

    @Size(max = 50)
    @Column(name = "db_name", nullable = false)
    private String dbName;

    @Size(max = 100)
    @Column(name = "url", nullable = false)
    private String url;

    @Size(max = 50)
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Size(max = 100)
    @Column(name = "password", nullable = false)
    private String password;
    @Size(max = 100)
    @Column(name = "driver_class", nullable = false)
    private String driverClass;
    @Size(max = 10)
    @Column(name = "status", nullable = false)
    private String status;
    
    private String emailId;

    public MasterTenant(@Size(max = 50) String dbName, @Size(max = 100) String url, @Size(max = 50) String userName,
                        @Size(max = 100) String password, @Size(max = 100) String driverClass,
                        @Size(max = 10) String status) {
        this.dbName = dbName;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.driverClass = driverClass;
        this.status = status;
    }

	public MasterTenant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getTenantClientId() {
		return tenantClientId;
	}

	public void setTenantClientId(Integer tenantClientId) {
		this.tenantClientId = tenantClientId;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "MasterTenant [tenantClientId=" + tenantClientId + ", dbName=" + dbName + ", url=" + url + ", userName="
				+ userName + ", password=" + password + ", driverClass=" + driverClass + ", status=" + status
				+ ", emailId=" + emailId + "]";
	}
    
    
}
