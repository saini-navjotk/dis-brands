package com.tcs.eas.rest.apis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity(name = "brand")
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "brandid")
	private int brandId;

	@Column(unique = true, name = "brandname")
	@NotNull(message = "brand name field is missing")
	@Size(min = 2, message = "minimum 2 characters are required for brand name")
	private String brandName;

	@NotNull(message = "brand description field is missing")
	@Size(min = 2, message = "minimum 10 characters are required for brand description")
	@Column(name = "branddescription")
	private String brandDescription;

	@Column(name = "createdby")
	@NotNull(message = "created by field is missing")
	private String createdBy;

	@Column(name = "createtimestamp")
	private Date createdTimestamp;

	@Column(name = "updatedby")
	@NotNull(message = "updated by field is missing")
	private String updatedBy;

	@Column(name = "updatedtimestamp")
	private Date updatedTimestamp;

	/*
	 * @Lob private byte[] brandLogo;
	 */

	@ManyToOne(optional = false)
	@JoinColumn(name = "brandorigin", referencedColumnName = "productEntityId")
	private ProductEntity brandOrigin;

	/**
	 * 
	 */

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

	public ProductEntity getBrandOrigin() {
		return brandOrigin;
	}

	public void setBrandOrigin(ProductEntity brandOrigin) {
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

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
	 * @param updatedBy
	 */
	public Brand(int brandId,
			@NotNull(message = "brand name field is missing") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandName,
			@NotNull(message = "brand origin field is missing") ProductEntity brandOrigin,
			@NotNull(message = "brand description field is missing") @Size(min = 2, message = "minimum 10 characters are required for brand description") String brandDescription,
			@NotNull(message = "created by field is missing") String createdBy,
			@NotNull(message = "updated by field is missing") String updatedBy) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
		this.brandOrigin = brandOrigin;
		this.brandDescription = brandDescription;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	/**
	 * @param brandName
	 * @param brandOrigin
	 * @param brandDescription
	 * @param createdBy
	 * @param updatedBy
	 */
	public Brand(
			@NotNull(message = "brand name field is missing") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandName,
			@NotNull(message = "brand origin field is missing") ProductEntity brandOrigin,
			@NotNull(message = "brand description field is missing") @Size(min = 2, message = "minimum 10 characters are required for brand description") String brandDescription,
			@NotNull(message = "created by field is missing") String createdBy,
			@NotNull(message = "updated by field is missing") String updatedBy) {
		super();
		this.brandName = brandName;
		this.brandOrigin = brandOrigin;
		this.brandDescription = brandDescription;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

}
