package com.talk2amar.projects.productservice.utils;

public class AppConstants {

	public static final String PRODUCTS_URI = "/products";
	public static final String PRODUCTS_BY_ID_URI = PRODUCTS_URI+"/{id}";
	
	public static final String SUCCESS_CODE = "P-200";
	public static final String NOT_FOUND = "P-400";
	public static final String CONFLICIT = "P-409";
	public static final String BAD_REQUEST = "P-403";
	
	public static final String PATH_VARIABLE_ID = "id";
	
	public static final String PRODUCT_CREATE_SUCCESS_MSG = "Product created successfully";
	public static final String NOT_FOUND_MSG = "No record(s) to display";
	public static final String PRODUCT_DELETE_SUCCESS_MSG = "Product deleted successfully";
	public static final String PRODUCT_UPDATE_SUCCESS_MSG = "Product updated successfully";
	public static final String SKU_EXISTS_ALREADY_MSG = "Product SKU already exists";
	public static final String PRODUCT_NOT_FOUND_MSG = "Product not found";
	
	
}
