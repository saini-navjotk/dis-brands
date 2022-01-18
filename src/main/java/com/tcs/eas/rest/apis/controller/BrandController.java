package com.tcs.eas.rest.apis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.tcs.eas.rest.apis.model.ProductBrandApiModel;
import com.tcs.eas.rest.apis.utility.Utility;

@RestController
@RequestMapping("api/v1/dis/brands")
public class BrandController {

	@Autowired
	private BrandDaoService brandDaoService;

	@Autowired
	LoggingService loggingService;

	@GetMapping
	public ResponseEntity<List<ProductBrandApiModel>> getAllBrands(@RequestHeader Map<String, String> headers) {

		ArrayList<ProductBrandApiModel> brands = (ArrayList<ProductBrandApiModel>) brandDaoService.getAllBrands();
		loggingService.writeProcessLog("GET", "brands", "getAllBrands", brands);
		return ResponseEntity.status(200).headers(Utility.getCustomResponseHeaders(headers)).body(brands);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductBrandApiModel> getBrandById(@PathVariable int id,
			@RequestHeader Map<String, String> headers) {

		ProductBrandApiModel brand = brandDaoService.getBrandById(id);

		if (brand == null) {
			throw new BrandNotFound("Brand id " + id + " does not exist");
		}

		loggingService.writeProcessLog("GET", "brands", "getBrand by id", brand);
		return ResponseEntity.status(200).headers(Utility.getCustomResponseHeaders(headers)).body(brand);
	}

	@PostMapping
	public ResponseEntity<List<ProductBrandApiModel>> addBrands(@Valid @RequestBody List<ProductBrandApiModel> brands,
			@RequestHeader Map<String, String> headers) {

		loggingService.writeProcessLog("POST", "brands", "addBrands", brands);
		List<ProductBrandApiModel> brandList = brandDaoService.addBrands(brands);

		return ResponseEntity.status(201).headers(Utility.getCustomResponseHeaders(headers)).body(brandList);
	}

	@PutMapping
	public ResponseEntity<ProductBrandApiModel> updateBrandById(@Valid @RequestBody ProductBrandApiModel brand,
			@RequestHeader Map<String, String> headers) {

		ProductBrandApiModel productBrand = brandDaoService.updateBrandById(brand);

		loggingService.writeProcessLog("PUT", "brands", "update brand by id", brand);
		return ResponseEntity.status(200).headers(Utility.getCustomResponseHeaders(headers)).body(productBrand);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteBrandById(@PathVariable Integer id,
			@RequestHeader Map<String, String> headers) {

		loggingService.writeProcessLog("DELETE", "brands", "getAllBrands", id);

		String returnMessage = brandDaoService.deleteBrandById(id);

		return ResponseEntity.ok().headers(Utility.getCustomResponseHeaders(headers)).body(returnMessage);
	}
}
