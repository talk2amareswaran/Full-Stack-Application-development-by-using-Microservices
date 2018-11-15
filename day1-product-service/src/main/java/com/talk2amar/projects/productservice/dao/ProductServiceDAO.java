package com.talk2amar.projects.productservice.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.talk2amar.projects.productservice.model.Product;

@Transactional
public interface ProductServiceDAO {

	public void createProduct(Product product);

	public List<Product> getProducts();

	public void deleteProduct(String id);

	public void updateProduct(Product product, String id);
	
	public boolean isSkuExists(String sku);
	
	public boolean isProductExists(String id);

	public Product getProductById(String id);
}
