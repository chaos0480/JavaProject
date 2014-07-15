package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
	
	///Field
	@Autowired
	@Qualifier("purchaseDaoImpl")
	PurchaseDao purchaseDao;

	public void setPurchaseDao(PurchaseDao purchaseDao) {
		System.out.println("::"+getClass()+".setPurchaseDao() call....");
		this.purchaseDao = purchaseDao;
	}
	
	///Constructor
	public PurchaseServiceImpl() {
		System.out.println("::"+getClass()+"default »ý¼ºÀÚ Call....");
	}

	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.insertPurchase(purchase);
	}
	
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.findPurchase(tranNo);
	}
	
	public Purchase getPurchase2(int ProdNo) throws Exception {
		return purchaseDao.findPurchase2(ProdNo);
	}
	
	public Map<String , Object > getPurchaseList(Search search,String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("search", search);
		map.put("buyerId", buyerId);
		
		List<Purchase> list= purchaseDao.getPurchaseList(map);
		int totalCount = purchaseDao.getTotalCount(buyerId);
		
		map.remove("buyerId");
		map.remove("search");
				
		map.put("list", list );
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}
	
	public HashMap<String,Object> getSaleList(Search search) throws Exception {
		return null;
	}
	
	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}
	
	public void removePurcahse(String receiverName) throws Exception{
		purchaseDao.removePurchase(receiverName);
	}
}
