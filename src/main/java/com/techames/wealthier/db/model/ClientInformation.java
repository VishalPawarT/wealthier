package com.techames.wealthier.db.model;
// Generated 2 Sep, 2018 1:04:52 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ClientInformation generated by hbm2java
 */
@Entity
@Table(name = "client_information")
public class ClientInformation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int clientId;
	private String clientName;
	private String clientPassword;
	private String clientRole;
	private Date createdAt;
	private Date modifiedAt;
	private Integer accountId;
	private Date accountSubsEndedAt;

	public ClientInformation() {
	}

	public ClientInformation(int clientId) {
		this.clientId = clientId;
	}

	public ClientInformation(int clientId, String clientName, String clientPassword, String clientRole, Date createdAt,
			Date modifiedAt, Integer accountId, Date accountSubsEndedAt) {
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientPassword = clientPassword;
		this.clientRole = clientRole;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.accountId = accountId;
		this.accountSubsEndedAt = accountSubsEndedAt;
	}

	@Id

	@Column(name = "client_id", unique = true, nullable = false)
	public int getClientId() {
		return this.clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Column(name = "client_name", length = 45)
	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Column(name = "client_password", length = 45)
	public String getClientPassword() {
		return this.clientPassword;
	}

	public void setClientPassword(String clientPassword) {
		this.clientPassword = clientPassword;
	}

	@Column(name = "client_role", length = 45)
	public String getClientRole() {
		return this.clientRole;
	}

	public void setClientRole(String clientRole) {
		this.clientRole = clientRole;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", length = 19)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_at", length = 19)
	public Date getModifiedAt() {
		return this.modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	@Column(name = "account_id")
	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "account_subs_ended_at", length = 19)
	public Date getAccountSubsEndedAt() {
		return this.accountSubsEndedAt;
	}

	public void setAccountSubsEndedAt(Date accountSubsEndedAt) {
		this.accountSubsEndedAt = accountSubsEndedAt;
	}

}
