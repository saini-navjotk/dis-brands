package com.tcs.eas.rest.apis.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@ApiModel
public class ProductBrandApiModel {

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brandId;

	// @Column(unique = true)
	@NotNull(message = "brand name field is missing *")
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

	public ProductBrandApiModel(int brandId,
			@NotNull(message = "brand name field is missing**") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandName,
			@NotNull(message = "brand origin field is missing") @Size(min = 2, message = "minimum 2 characters are required for brand name") String brandOrigin,
			@NotNull(message = "brand description field is missing") @Size(min = 2, message = "minimum 10 characters are required for brand description") String brandDescription) {
		this.brandId = brandId;
		this.brandName = brandName;
		this.brandOrigin = brandOrigin;
		this.brandDescription = brandDescription;
	}

	public ProductBrandApiModel() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * @Lob private byte[] brandLogo;
	 */

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

}
