package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {

	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("::"+getClass()+".setSqlSession() call....");
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public PurchaseDaoImpl() {
		System.out.println("::"+getClass()+"default 생성자 Call....");
	}
	
	///Method
	public void insertPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	}

	public Purchase findPurchase(int tranNo) throws Exception {
		return (Purchase)sqlSession.selectOne("PurchaseMapper.getPurchase01", tranNo);
	}

	public Purchase findPurchase2(int prodNo) throws Exception {
		return (Purchase)sqlSession.selectOne("PurchaseMapper.getPurchase02", prodNo);
	}

	public List<Purchase> getPurchaseList(Map<String, Object> map) throws Exception {
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	public int getTotalCount(String buyerId) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", buyerId);
	}

	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		return null;
	}

	public void updatePurchase(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}
	
	public void removePurchase(String receiverName) throws Exception {
		sqlSession.delete("PurchaseMapper.removePurchase", receiverName);
	}

}
