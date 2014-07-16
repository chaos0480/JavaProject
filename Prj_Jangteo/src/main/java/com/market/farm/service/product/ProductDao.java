package com.market.farm.service.product;

import java.util.List;

import com.market.farm.common.Search;
import com.market.farm.service.domain.Product;

public interface ProductDao {

	public int addProduct(Product prodVO) throws Exception ;
	
	public Product getProduct(int prodId) throws Exception ;
	
	public int updateProduct(Product prodVO) throws Exception ;
	
	public int removeProduct(String userId) throws Exception;
	
	public List<Product> getProductList(Search search) throws Exception ;
	
	// �Խ��� Page ó���� ���� ��üRow(totalCount)  return
	public int getTotalCount(Search search) throws Exception ;
		
}
