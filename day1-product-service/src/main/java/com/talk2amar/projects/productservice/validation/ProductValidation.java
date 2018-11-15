package com.talk2amar.projects.productservice.validation;


import org.springframework.stereotype.Component;

import com.talk2amar.projects.productservice.model.Product;

@Component
public class ProductValidation {

	private static final int PRODUCT_MAX_CHARS = 50;
	private static final int PRODUCT_MIN_CHARS = 1;
	private static final String EMPTY_PRODUCT_NAME = "Please provide the name";
	private static final String PRODUCT_NAME_LENGTH = "Product name length should not be more than" +PRODUCT_MAX_CHARS+ " characters";
	private static final String EMPTY_PRODUCT_SKU = "Please provide the SKU";
	private static final String PRODUCT_SKU_LENGTH = "Product SKU length should not be more than" +PRODUCT_MAX_CHARS+ " characters";
	private static final String PRODUCT_PRICE_INVALID = "Price should be more than zero";
	private static final String PRODUCT_QTY_INVALID = "Quantity should be more than zero";
	
	public String isValidProduct(Product product) {
		
		int productNameLength = 0;
		int productSKULength = 0;
		
		if(product.getName()!=null) {
			product.setName(product.getName().trim());
			productNameLength = product.getName().length();
		}
		
		if(product.getSku()!=null) {
			product.setSku(product.getSku().trim());
			productSKULength = product.getSku().length();
		}

		if(product.getName()==null || productNameLength<PRODUCT_MIN_CHARS)
			return EMPTY_PRODUCT_NAME;
		
		if(product.getName()!=null && productNameLength>=PRODUCT_MIN_CHARS && productNameLength>PRODUCT_MAX_CHARS)
			return PRODUCT_NAME_LENGTH;
		
		if(product.getSku()==null || productSKULength<PRODUCT_MIN_CHARS)
			return EMPTY_PRODUCT_SKU;
		
		if(product.getSku()!=null && productSKULength>=PRODUCT_MIN_CHARS && productSKULength>PRODUCT_MAX_CHARS)
			return PRODUCT_SKU_LENGTH;
		
		if(product.getPrice()<PRODUCT_MIN_CHARS)
			return PRODUCT_PRICE_INVALID;
		
		if(product.getQty()<PRODUCT_MIN_CHARS)
			return PRODUCT_QTY_INVALID;
		
		return null;
	}
}
