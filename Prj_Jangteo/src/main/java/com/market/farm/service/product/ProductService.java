package com.market.farm.service.product;

import java.util.Map;

import com.market.farm.common.Search;
import com.market.farm.service.domain.Product;

//==> 상품관리에서 서비스할 내용 추상화/캡슐화한 Service  Interface Definition
public interface ProductService {
	
	//상품추가
	public void addProduct(Product product) throws Exception;

	//상품정보확인
	public Product getProduct(int prodNo) throws Exception;
	
	//상품정리리스트
	public Map<String,Object> getProductList(Search search) throws Exception;

	//상품정보수정
	public void updateProduct(Product product) throws Exception;
	
	//상품정보삭제
	public void removeProduct(String userId) throws Exception;
	
}