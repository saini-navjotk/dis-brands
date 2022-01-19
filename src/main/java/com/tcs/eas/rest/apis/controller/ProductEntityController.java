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

import com.tcs.eas.rest.apis.db.ProductEntityDaoService;
import com.tcs.eas.rest.apis.exception.BrandNotFound;
import com.tcs.eas.rest.apis.log.LoggingService;
import com.tcs.eas.rest.apis.model.ProductEntity;
import com.tcs.eas.rest.apis.utility.Utility;

@RestController
@RequestMapping("api/v1/dis/productentity")
public class ProductEntityController {

	@Autowired
	private ProductEntityDaoService productEntityDaoService;

	@Autowired
	LoggingService loggingService;

	@GetMapping
	public ResponseEntity<List<ProductEntity>> getAllProductEntity(@RequestHeader Map<String, String> headers) {

		ArrayList<ProductEntity> entities = (ArrayList<ProductEntity>) productEntityDaoService.getAllProductEntity();
		loggingService.writeProcessLog("GET", "productentity", "getAllProductEntity", entities);
		return ResponseEntity.status(200).headers(Utility.getCustomResponseHeaders(headers)).body(entities);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductEntity> getProductEntityById(@PathVariable int id,
			@RequestHeader Map<String, String> headers) {

		ProductEntity entity = productEntityDaoService.getProductEntityById(id);

		if (entity == null) {
			throw new BrandNotFound("Entity id " + id + " does not exist");
		}

		loggingService.writeProcessLog("GET", "productentity", "getProductEntityById by id", entity);
		return ResponseEntity.status(200).headers(Utility.getCustomResponseHeaders(headers)).body(entity);
	}

	@PostMapping
	public ResponseEntity<ProductEntity> addProductEntity(@Valid @RequestBody ProductEntity entity,
			@RequestHeader Map<String, String> headers) {

		loggingService.writeProcessLog("POST", "productentity", "addProductEntity", entity);
		return ResponseEntity.status(201).headers(Utility.getCustomResponseHeaders(headers))
				.body(productEntityDaoService.addProductEntity(entity));
	}

	@PutMapping
	public ResponseEntity<ProductEntity> updateProductEntityById(@Valid @RequestBody ProductEntity entity,
			@RequestHeader Map<String, String> headers) {

		ProductEntity productEntity = productEntityDaoService.getProductEntityById(entity.getProductEntityId());

		if (productEntity == null) {
			throw new BrandNotFound("productEntity id " + entity.getProductEntityId() + " does not exist");
		}

		loggingService.writeProcessLog("PUT", "productentity", "updateProductEntityById", productEntity);
		return ResponseEntity.status(200).headers(Utility.getCustomResponseHeaders(headers))
				.body(productEntityDaoService.updateProductEntityById(entity));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteProductEntityById(@PathVariable Integer id,
			@RequestHeader Map<String, String> headers) {

		ProductEntity entity = productEntityDaoService.getProductEntityById(id);

		if (entity == null) {
			throw new BrandNotFound("Product Entity id " + id + " does not exist");
		}

		loggingService.writeProcessLog("DELETE", "productentity", "deleteProductEntityById", entity);
		return ResponseEntity.ok().headers(Utility.getCustomResponseHeaders(headers))
				.body(productEntityDaoService.deleteEntityById(id));
	}
}
