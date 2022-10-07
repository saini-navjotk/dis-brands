package com.tcs.eas.rest.apis.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;


@ApiModel
public class ProductBrand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brandId;

	@Column(unique = true)
	@NotNull(message = "brand name field is missing")
	@Size(min = 2, message = "minimum 2 characters are required for brand name")
	private String brandName;

	@Column(unique = true)
	@NotNull(message = "brand origin field is missing")
	@Size(min = 2, message = "minimum 2 characters are required for brand name")
	private String brandOrigin;

	@Column(unique = true)
	@NotNull(message = "brand description field is missing")
	@Size(min = 2, message = "minimum 10 characters are required for brand description")
	private String brandDescription;

	@Column
	private String createdBy;

	public ProductBrand(int brandId,
			@NotNull(message = "brand name field is missing") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandName,
			@NotNull(message = "brand origin field is missing") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandOrigin,
			@NotNull(message = "brand description field is missing") @Size(min = 2, message = "minimum 10 characters are required for brand description") String brandDescription,
			@NotNull(message = "created by field is missing") String createdBy) {
		this.brandId = brandId;
		this.brandName = brandName;
		this.brandOrigin = brandOrigin;
		this.brandDescription = brandDescription;
		this.createdBy = createdBy;
	}

	public ProductBrand() {
	
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

}
