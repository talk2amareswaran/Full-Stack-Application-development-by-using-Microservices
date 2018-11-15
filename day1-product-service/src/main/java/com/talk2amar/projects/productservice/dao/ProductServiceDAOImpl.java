package com.talk2amar.projects.productservice.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.talk2amar.projects.productservice.model.Product;

@Repository
public class ProductServiceDAOImpl implements ProductServiceDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String PRODUCT_INSERT_SQL = "insert into products (name,sku,price,qty,createddate,createdby,lastupdateddate,lastupdatedby) values (?,?,?,?,?,?,?,?)";
	private static final String PRODUCT_SQL_QUERY = "select id, name, sku, price, qty, createddate, createdby, lastupdateddate, lastupdatedby from products";
	private static final String PRODUCT_DELETE_QUERY = "delete from products where id=?";
	private static final String PRODUCT_UPDATE_QUERY = "update products set name=?, sku=?, price=?, qty=?, lastupdateddate=?,lastupdatedby=? where id=?";
	private static final String SKU_EXISTS_SQL_QUERY = "select count(sku) from products where sku=?";
	private static final String PRODUCT_ID_EXISTS_SQL_QUERY = "select count(id) from products where id=?";
	private static final String SELECT_PRODUCT_BY_ID_SQL = PRODUCT_SQL_QUERY + " where id=?";
	private static final String INITIAL_RECORD = "intial-record";
	private static final String CREATED_BY = "createdby";
	private static final String CREATED_DATE = "createddate";
	private static final String LAST_UPDATED_BY = "lastupdatedby";
	private static final String LAST_UPDATED_DATE = "lastupdateddate";
	private static final String PRODUCT_ID = "id";
	private static final String PRODUCT_NAME = "name";
	private static final String PRODUCT_QTY = "qty";
	private static final String PRODUCT_SKU = "sku";
	private static final String PRODUCT_PRICE = "price";

	@Override
	public void createProduct(Product product) {
		jdbcTemplate.update(PRODUCT_INSERT_SQL,
				new Object[] { product.getName(), product.getSku(), product.getPrice(), product.getQty(),
						(System.currentTimeMillis() / 1000), INITIAL_RECORD, (System.currentTimeMillis() / 1000),
						INITIAL_RECORD });

	}

	@Override
	public List<Product> getProducts() {

		Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(PRODUCT_SQL_QUERY);
		List<Product> productsList = new ArrayList<>();
		rows.stream().map((row) -> {
			Product product = new Product();
			product.setCreatedby((String) row.get(CREATED_BY));
			product.setCreateddate(String.valueOf(row.get(CREATED_DATE)));
			product.setId((Integer) row.get(PRODUCT_ID));
			product.setLastupdatedby((String) row.get(LAST_UPDATED_BY));
			product.setLastupdateddate(String.valueOf(row.get(LAST_UPDATED_DATE)));
			product.setName((String) row.get(PRODUCT_NAME));
			product.setPrice((Float) row.get(PRODUCT_PRICE));
			product.setQty((Integer) row.get(PRODUCT_QTY));
			product.setSku((String) row.get(PRODUCT_SKU));
			return product;
		}).forEach((ss) -> {
			productsList.add(ss);
		});
		return productsList;

	}

	@Override
	public void deleteProduct(String id) {
		jdbcTemplate.update(PRODUCT_DELETE_QUERY, new Object[] { id });
	}

	@Override
	public void updateProduct(Product product, String id) {
		jdbcTemplate.update(PRODUCT_UPDATE_QUERY, new Object[] { product.getName(), product.getSku(),
				product.getPrice(), product.getQty(), (System.currentTimeMillis() / 1000), INITIAL_RECORD, id });

	}

	@Override
	public boolean isSkuExists(String sku) {
		return jdbcTemplate.queryForObject(SKU_EXISTS_SQL_QUERY, new Object[] { sku }, Integer.class) > 0;
	}

	@Override
	public boolean isProductExists(String id) {
		return jdbcTemplate.queryForObject(PRODUCT_ID_EXISTS_SQL_QUERY, new Object[] { id }, Integer.class) > 0;
	}

	@Override
	public Product getProductById(String id) {
		Product product = new Product();
		try {
			jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID_SQL, new Object[] { id }, (ResultSet rs, int rowNum) -> {
				product.setCreatedby(rs.getString(CREATED_BY));
				product.setCreateddate(rs.getString(CREATED_DATE));
				product.setId(rs.getInt(PRODUCT_ID));
				product.setLastupdatedby(rs.getString(LAST_UPDATED_BY));
				product.setLastupdateddate(rs.getString(LAST_UPDATED_DATE));
				product.setName(rs.getString(PRODUCT_NAME));
				product.setPrice(rs.getFloat(PRODUCT_PRICE));
				product.setQty(rs.getInt(PRODUCT_QTY));
				product.setSku(rs.getString(PRODUCT_SKU));
				return product;
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
		return product;
	}

}
