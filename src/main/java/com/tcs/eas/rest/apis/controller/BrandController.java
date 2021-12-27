package com.tcs.eas.rest.apis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.eas.rest.apis.db.BrandDaoService;
import com.tcs.eas.rest.apis.exception.BrandNotFound;
import com.tcs.eas.rest.apis.log.LoggingService;
import com.tcs.eas.rest.apis.model.ProductBrand;
import com.tcs.eas.rest.apis.utility.Utility;

@RestController
@RequestMapping("api/v1/dis/brands")
public class BrandController {

	@Autowired
	private BrandDaoService brandDaoService;

	@Autowired
	LoggingService loggingService;

	/* @PostMapping(value = "/brands" *//*
										 * , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces =
										 * {MediaType.APPLICATION_JSON_VALUE}
										 *//*
											 * ) public ResponseEntity<List<Brand>> addBrands(
											 *//* @RequestPart MultipartFile brandLogo, *//*
																							 * @RequestBody List<Brand>
																							 * brands){ return
																							 * ResponseEntity.status(201
																							 * ).body(brandService.
																							 * addBrands(brands
																							 *//* , brandLogo *//*
																												 * )); }
																												 */

	@GetMapping
	public ResponseEntity<List<ProductBrand>> getAllBrands(@RequestHeader Map<String, String> headers) {

		ArrayList<ProductBrand> brands = (ArrayList<ProductBrand>) brandDaoService.getAllBrands();
		loggingService.writeProcessLog("GET", "brands", "getAllBrands", brands);
		return ResponseEntity.status(200).headers(Utility.getCustomResponseHeaders(headers)).body(brands);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductBrand> getBrandById(@PathVariable int id, @RequestHeader Map<String, String> headers) {

		ProductBrand brand = brandDaoService.getBrandById(id);

		if (brand == null) {
			throw new BrandNotFound("Brand id " + id + " does not exist");
		}

		loggingService.writeProcessLog("GET", "brands", "getBrand by id", brand);
		return ResponseEntity.status(200).headers(Utility.getCustomResponseHeaders(headers)).body(brand);
	}

	@PostMapping
	public ResponseEntity<List<ProductBrand>> addBrands(@RequestBody List<ProductBrand> brands,
			@RequestHeader Map<String, String> headers) {

		loggingService.writeProcessLog("POST", "brands", "addBrands", brands);
		return ResponseEntity.status(201).headers(Utility.getCustomResponseHeaders(headers))
				.body(brandDaoService.addBrands(brands));
	}

	@PutMapping
	public ResponseEntity<ProductBrand> updateBrandById(@RequestBody ProductBrand brand,
			@RequestHeader Map<String, String> headers) {

		ProductBrand productBrand = brandDaoService.getBrandById(brand.getBrandId());

		if (productBrand == null) {
			throw new BrandNotFound("Brand id " + brand.getBrandId() + " does not exist");
		}

		loggingService.writeProcessLog("PUT", "brands", "update brand by id", brand);
		return ResponseEntity.status(200).headers(Utility.getCustomResponseHeaders(headers))
				.body(brandDaoService.updateBrandById(brand));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteBrandById(@PathVariable Integer id,
			@RequestHeader Map<String, String> headers) {

		ProductBrand productBrand = brandDaoService.getBrandById(id);

		if (productBrand == null) {
			throw new BrandNotFound("Brand id " + id + " does not exist");
		}

		loggingService.writeProcessLog("DELETE", "brands", "getAllBrands", productBrand);
		return ResponseEntity.ok().headers(Utility.getCustomResponseHeaders(headers))
				.body(brandDaoService.deleteBrandById(id));
	}
}
