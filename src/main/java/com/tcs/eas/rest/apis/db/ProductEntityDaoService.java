package com.tcs.eas.rest.apis.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tcs.eas.rest.apis.model.ProductEntity;
import com.tcs.eas.rest.apis.model.ProductEntityApiModel;

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

	public ProductEntityApiModel addProductEntity(ProductEntityApiModel productEntityApiModel) {
		ProductEntity productEntity = new ProductEntity(productEntityApiModel);
		productEntity.setCreatedBy(adminUser);
		productEntity.setCreatedTimestamp(new Date());
		productEntity.setUpdatedBy(adminUser);
		productEntity.setUpdatedTimestamp(new Date());
		productEntity.setStatus("A");
		productEntity.setEntityName(productEntityApiModel.getEntityName().toUpperCase());

		return new ProductEntityApiModel( productEntityRepository.save(productEntity));

	}

	public List<ProductEntityApiModel> getAllProductEntity() {

		List<ProductEntity> productEntities = productEntityRepository.findAll();
		return productEntities.stream().map(b -> new ProductEntityApiModel(b.getProductEntityId(), 
				 b.getEntityType(), b.getEntityName(), b.getEntityDescription(), b.getStatus()))
				.collect(Collectors.toList());

		
	}

	public ProductEntityApiModel getProductEntityById(int id) {
		Optional<ProductEntity> optionalEntity = productEntityRepository.findById(id);
		ProductEntity entity = null;
		ProductEntityApiModel model = null;
		if (optionalEntity.isPresent()) {
			entity = optionalEntity.get();
			model = new ProductEntityApiModel(entity);
		}
		return model;
	}

	public String deleteEntityById(Integer id) {

		Optional<ProductEntity> optionalEntity = productEntityRepository.findById(id);

		if (optionalEntity.isPresent()) {
			productEntityRepository.delete(optionalEntity.get());

			return "Entity Deleted Successfully !";
		}
		return "Entity Not Found !";
	}

	public ProductEntityApiModel updateProductEntityById(ProductEntityApiModel productEntity) {

		ProductEntityApiModel oldEntity = getProductEntityById(productEntity.getProductEntityId());

		if (oldEntity != null) {
			
			
			productEntity.setUpdatedTimestamp(new Date());
			new ProductEntityApiModel( productEntityRepository.save(new ProductEntity(productEntity)));

			return oldEntity;
		}

		return new ProductEntityApiModel();
	}

}
