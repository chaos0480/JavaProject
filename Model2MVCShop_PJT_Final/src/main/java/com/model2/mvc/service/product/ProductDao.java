package com.model2.mvc.service.product;

import java.util.List;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductDao {

	public int addProduct(Product prodVO) throws Exception ;
	
	public Product getProduct(int prodId) throws Exception ;
	
	public int updateProduct(Product prodVO) throws Exception ;
	
	public int removeProduct(String userId) throws Exception;
	
	public List<Product> getProductList(Search search) throws Exception ;
	
	// 게시판 Page 처리를 위한 전체Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception ;
		
}
