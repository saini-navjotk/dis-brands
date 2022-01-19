package com.tcs.eas.rest.apis.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tcs.eas.rest.apis.model.ProductEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductEntityDaoService {

	@Autowired
	private ProductEntityRepository productEntityRepository;

	@Value(value = "${ADMIN_USER}")
	private String adminUser;

	@Value(value = "${API_USER}")
	private String apiUser;

	public ProductEntity addProductEntity(ProductEntity productEntity) {

		productEntity.setCreatedBy(adminUser);
		productEntity.setCreatedTimestamp(new Date());
		productEntity.setUpdatedBy(adminUser);
		productEntity.setUpdatedTimestamp(new Date());
		productEntity.setStatus("A");
		productEntity.setEntityName(productEntity.getEntityName().toUpperCase());

		ProductEntity entity = productEntityRepository.save(productEntity);

		return entity;
	}

	public List<ProductEntity> getAllProductEntity() {

		List<ProductEntity> entityList = productEntityRepository.findAll();

		return entityList;
	}

	public ProductEntity getProductEntityById(int id) {
		Optional<ProductEntity> optionalEntity = productEntityRepository.findById(id);
		ProductEntity entity = null;

		if (optionalEntity.isPresent()) {
			entity = optionalEntity.get();
		}
		return entity;
	}

	public String deleteEntityById(Integer id) {

		Optional<ProductEntity> optionalEntity = productEntityRepository.findById(id);

		if (optionalEntity.isPresent()) {
			productEntityRepository.delete(optionalEntity.get());

			return "Entity Deleted Successfully !";
		}
		return "Entity Not Found !";
	}

	public ProductEntity updateProductEntityById(ProductEntity productEntity) {

		Optional<ProductEntity> optionalEntity = productEntityRepository.findById(productEntity.getProductEntityId());

		if (optionalEntity.isPresent()) {
			ProductEntity oldEntity = optionalEntity.get();
			oldEntity.setEntityType(productEntity.getEntityType());
			oldEntity.setEntityName(productEntity.getEntityName().toUpperCase());
			oldEntity.setEntityDescription(productEntity.getEntityDescription());
			oldEntity.setUpdatedBy(adminUser);
			oldEntity.setUpdatedTimestamp(new Date());
			oldEntity = productEntityRepository.save(oldEntity);

			return oldEntity;
		}

		return new ProductEntity();
	}

}
