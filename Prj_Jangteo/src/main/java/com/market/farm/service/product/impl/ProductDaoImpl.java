package com.market.farm.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.market.farm.common.Search;
import com.market.farm.service.domain.Product;
import com.market.farm.service.product.ProductDao;

@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {

	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("::"+getClass()+".setSqlSession() call....");
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public ProductDaoImpl() {
		System.out.println("::"+getClass()+"default 생성자 Call....");
	}

	///Method
	public int addProduct(Product prodVO) throws Exception {
		return sqlSession.insert("ProductMapper.addProduct", prodVO);
	}

	public Product getProduct(int prodId) throws Exception {
		return (Product)sqlSession.selectOne("ProductMapper.getProduct", prodId);
	}

	public int updateProduct(Product prodVO) throws Exception {
		return sqlSession.update("ProductMapper.updateProduct", prodVO);
	}

	public int removeProduct(String prodId) throws Exception {
		return sqlSession.delete("ProductMapper.removeProduct", prodId);
	}

	public List<Product> getProductList(Search search) throws Exception {
		return sqlSession.selectList("ProductMapper.getProductList", search);			
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}

}
