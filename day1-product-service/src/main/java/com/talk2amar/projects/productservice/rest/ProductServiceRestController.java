package com.talk2amar.projects.productservice.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.talk2amar.projects.productservice.model.Product;
import com.talk2amar.projects.productservice.service.ApplicationService;
import com.talk2amar.projects.productservice.utils.AppConstants;
import com.talk2amar.projects.productservice.utils.Utils;
import com.talk2amar.projects.productservice.validation.ProductValidation;

@RestController
public class ProductServiceRestController {

	@Autowired
	ApplicationService applicationService;
	@Autowired
	ProductValidation productValidation;

	@RequestMapping(value = AppConstants.PRODUCTS_URI, method = RequestMethod.POST)
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {

		String errorMessage = productValidation.isValidProduct(product);
		
		if(errorMessage!=null)
			return new ResponseEntity<>(Utils.getApiResponse(AppConstants.BAD_REQUEST, errorMessage), HttpStatus.BAD_REQUEST);
		
		if(applicationService.isSkuExists(product.getSku()))
			return new ResponseEntity<>(Utils.getApiResponse(AppConstants.CONFLICIT, AppConstants.SKU_EXISTS_ALREADY_MSG), HttpStatus.CONFLICT);
		
			applicationService.createProduct(product);
			return new ResponseEntity<>(Utils.getApiResponse(AppConstants.SUCCESS_CODE, AppConstants.PRODUCT_CREATE_SUCCESS_MSG), HttpStatus.CREATED);

	}
	
	@RequestMapping(value = AppConstants.PRODUCTS_URI, method = RequestMethod.GET)
	public ResponseEntity<Object> getProduct() {
		List<Product> productList = applicationService.getProducts();
		if(productList!=null && !productList.isEmpty())
			return new ResponseEntity<>(applicationService.getProducts(),HttpStatus.OK);
		else
			return new ResponseEntity<>(Utils.getApiResponse(AppConstants.NOT_FOUND, AppConstants.NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = AppConstants.PRODUCTS_BY_ID_URI, method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteProduct(@PathVariable(AppConstants.PATH_VARIABLE_ID) String id) {
		if(!applicationService.isProductExists(id.trim())) 
			return new ResponseEntity<>(Utils.getApiResponse(AppConstants.NOT_FOUND, AppConstants.PRODUCT_NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
		applicationService.deleteProduct(id.trim());
		return new ResponseEntity<>(Utils.getApiResponse(AppConstants.SUCCESS_CODE,AppConstants.PRODUCT_DELETE_SUCCESS_MSG),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = AppConstants.PRODUCTS_BY_ID_URI, method = RequestMethod.PUT)
	public ResponseEntity<Object> updateProduct(@RequestBody Product requestProduct, @PathVariable(AppConstants.PATH_VARIABLE_ID) String id) {
		Product product = applicationService.getProductById(id.trim());
		if(product.getId()<=0)
			return new ResponseEntity<>(Utils.getApiResponse(AppConstants.NOT_FOUND, AppConstants.PRODUCT_NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
		if(requestProduct.getSku()!=null && requestProduct.getSku().trim().length()>0 && 
					!product.getSku().trim().equalsIgnoreCase(requestProduct.getSku().trim()) && 
					applicationService.isSkuExists(requestProduct.getSku().trim())) {
			return new ResponseEntity<>(Utils.getApiResponse(AppConstants.CONFLICIT, AppConstants.SKU_EXISTS_ALREADY_MSG), HttpStatus.CONFLICT);
			}
		Utils.getUpdatedProduct(requestProduct,product);
		String errorMessage = productValidation.isValidProduct(product);
		if(errorMessage!=null)
			return new ResponseEntity<>(Utils.getApiResponse(AppConstants.BAD_REQUEST, errorMessage), HttpStatus.BAD_REQUEST);
		
		applicationService.updateProduct(product,id);
		return new ResponseEntity<>(Utils.getApiResponse(AppConstants.SUCCESS_CODE,AppConstants.PRODUCT_UPDATE_SUCCESS_MSG),HttpStatus.OK);
	}
	
	@RequestMapping(value = AppConstants.PRODUCTS_BY_ID_URI, method = RequestMethod.GET)
	public ResponseEntity<Object> getProductById(@PathVariable(AppConstants.PATH_VARIABLE_ID) String id) {
		Product product = applicationService.getProductById(id.trim());
		if(product.getId()<=0)
			return new ResponseEntity<>(Utils.getApiResponse(AppConstants.NOT_FOUND, AppConstants.PRODUCT_NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(product,HttpStatus.OK);
	}
}
