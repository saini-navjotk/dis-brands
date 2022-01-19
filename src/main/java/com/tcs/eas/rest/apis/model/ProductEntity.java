package com.tcs.eas.rest.apis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity(name = "product_entity")
@ApiModel
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productentityid")
	private int productEntityId;

	@Column(unique = false, name = "entitytype")
	@NotNull(message = "entity type field is missing")
	@Size(min = 2, message = "minimum 2 characters are required for entity type")
	private String entityType;

	@NotNull(message = "entity name field is missing")
	@Column(unique = false, name = "entityname")
	private String entityName;

	// @NotNull(message = "entity description field is missing")
	@Size(min = 5, message = "minimum 5 characters are required for brand description")
	@Column(name = "enitydescription")
	private String entityDescription;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "createdtimestamp")
	private Date createdTimestamp;

	@Column(name = "updatedby")
	private String updatedBy;

	@Column(name = "updatedtimestamp")
	private Date updatedTimestamp;

	@Column(name = "status")
	private String status;

	/**
	 * 
	 */

	@PrePersist
	void createdAt() {
		this.createdTimestamp = this.updatedTimestamp = new Date();
	}

	public int getProductEntityId() {
		return productEntityId;
	}

	public void setProductEntityId(int productEntityId) {
		this.productEntityId = productEntityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityDescription() {
		return entityDescription;
	}

	public void setEntityDescription(String entityDescription) {
		this.entityDescription = entityDescription;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 */
	public ProductEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
