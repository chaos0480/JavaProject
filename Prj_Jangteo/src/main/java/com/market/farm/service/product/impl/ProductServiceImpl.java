package com.market.farm.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.market.farm.common.Search;
import com.market.farm.service.domain.Product;
import com.market.farm.service.product.ProductDao;
import com.market.farm.service.product.ProductService;


//==> 惑前包府 辑厚胶 备泅
@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	///Field
	@Autowired
	@Qualifier("productDaoImpl")
	ProductDao productDAO;
	
	public void setProductDao(ProductDao productDao) {
		System.out.println("::"+getClass()+".setProductDao() call....");
		this.productDAO = productDao;
	}

	///Constructor
	public ProductServiceImpl() {
		System.out.println("::"+getClass()+"default 积己磊 Call....");
	}
	
	///Method
	public void addProduct(Product product) throws Exception {
		productDAO.addProduct(product);
	}

	public Product getProduct(int prodNo) throws Exception {
		return productDAO.getProduct(prodNo);
	}

	public void updateProduct(Product product) throws Exception {
		productDAO.updateProduct(product);
	}
	
	public void removeProduct(String userId) throws Exception{
		productDAO.removeProduct(userId);
	}
	
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		List<Product> list= productDAO.getProductList(search);
		int totalCount = productDAO.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}
}
