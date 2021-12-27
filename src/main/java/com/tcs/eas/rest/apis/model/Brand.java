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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ApiModel
@Entity(name = "brand")
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brandId;

	@Column(unique = true)
	@NotNull(message = "brand name field is missing")
	@Size(min = 2, message = "minimum 2 characters are required for brand name")
	private String brandName;

	@NotNull(message = "brand origin field is missing")
	@Size(min = 2, message = "minimum 2 characters are required for brand name")
	private String brandOrigin;

	@NotNull(message = "brand description field is missing")
	@Size(min = 2, message = "minimum 10 characters are required for brand description")
	private String brandDescription;

	@Column
	@NotNull(message = "created by field is missing")
	private String createdBy;

	@Column
	private Date createdTimestamp;

	@Column
	private Date updatedTimestamp;

	/*
	 * @Lob private byte[] brandLogo;
	 */

	/**
	 * @param brandName
	 * @param brandOrigin
	 * @param brandDescription
	 * @param createdBy
	 */
	public Brand(
			@NotNull(message = "brand name field is missing") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandName,
			@NotNull(message = "brand origin field is missing") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandOrigin,
			@NotNull(message = "brand description field is missing") @Size(min = 2, message = "minimum 10 characters are required for brand description") String brandDescription,
			@NotNull(message = "created by field is missing") String createdBy) {
		super();
		this.brandName = brandName;
		this.brandOrigin = brandOrigin;
		this.brandDescription = brandDescription;
		this.createdBy = createdBy;
	}

	/**
	 * 
	 */
	public Brand() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param brandId
	 * @param brandName
	 * @param brandOrigin
	 * @param brandDescription
	 * @param createdBy
	 */
	public Brand(int brandId,
			@NotNull(message = "brand name field is missing") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandName,
			@NotNull(message = "brand origin field is missing") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandOrigin,
			@NotNull(message = "brand description field is missing") @Size(min = 2, message = "minimum 10 characters are required for brand description") String brandDescription,
			@NotNull(message = "created by field is missing") String createdBy) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
		this.brandOrigin = brandOrigin;
		this.brandDescription = brandDescription;
		this.createdBy = createdBy;
	}

	@PrePersist
	void createdAt() {
		this.createdTimestamp = this.updatedTimestamp = new Date();
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandOrigin() {
		return brandOrigin;
	}

	public void setBrandOrigin(String brandOrigin) {
		this.brandOrigin = brandOrigin;
	}

	public String getBrandDescription() {
		return brandDescription;
	}

	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
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

	public Date getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

}
