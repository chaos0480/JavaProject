package com.model2.mvc.service.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;


public interface PurchaseDao {

	public void insertPurchase(Purchase purchase) throws Exception;

	public Purchase findPurchase(int tranNo) throws Exception;
	
	public Purchase findPurchase2(int prodNo) throws Exception;
	
	public List<Purchase> getPurchaseList(Map<String, Object> map) throws Exception;
	
	public int getTotalCount(String buyerId) throws Exception;
	
	public HashMap<String, Object> getSaleList(Search search) throws Exception;
	
	public void updatePurchase(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
	public void removePurchase(String receiverName) throws Exception;
}
