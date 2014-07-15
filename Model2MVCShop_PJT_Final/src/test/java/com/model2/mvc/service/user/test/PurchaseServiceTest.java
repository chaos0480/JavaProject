package com.model2.mvc.service.user.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Test
	public void testAddPurchase() throws Exception {

		User user = new User("user18","주몽","user04","010-1234-5678","우주","AAA@AAA.com");
		Product prod = new Product(10041,"자전거","똥강아지","20140529",100000,"root/temp");
		Purchase purch = new Purchase();
		
		purch.setPurchaseProd(prod);
		purch.setBuyer(user);
		purch.setPaymentOption("1");
		purch.setReceiverName("최영호");
		purch.setReceiverPhone("010-1234-5678");
		
		purch.setDivyAddr("서울");
		purch.setDivyRequest("빨리빨리");
		purch.setDivyDate("20140602");
		
		purchaseService.addPurchase(purch);
		purch = purchaseService.getPurchase(10043);
				
		//==> console 확인
		System.out.println(purch);
		
		//==> API 확인
		Assert.assertEquals(10041, purch.getPurchaseProd().getProdNo());
		Assert.assertEquals("user18", purch.getBuyer().getUserId());
		Assert.assertEquals("1", purch.getPaymentOption());
		Assert.assertEquals("최영호", purch.getReceiverName());
		Assert.assertEquals("010-1234-5678", purch.getReceiverPhone());
		Assert.assertEquals("서울", purch.getDivyAddr());
		Assert.assertEquals("빨리빨리", purch.getDivyRequest());
		Assert.assertEquals("1", purch.getTranCode());
		Assert.assertEquals("2014-06-02 00:00:00.0", purch.getDivyDate());
	}
	
	//@Test
	public void testGetPurchaseTranNo() throws Exception {
		
		Purchase purch = new Purchase();
		//==> 필요하다면...
//		purch.setPurchaseProd(prod);
//		purch.setBuyer(user);
//		purch.setPaymentOption("1");
//		purch.setReceiverName("최영호");
//		purch.setReceiverPhone("010-1234-5678");
//		purch.setDivyAddr("서울");
//		purch.setDivyRequest("빨리빨리");
//		purch.setTranCode("2");
//		purch.setDivyDate("20140602");
		
		purch = purchaseService.getPurchase(10043);
		
		//==> console 확인
		System.out.println(purch);
		
		//==> API 확인
		Assert.assertEquals(10041, purch.getPurchaseProd().getProdNo());
		Assert.assertEquals("user18", purch.getBuyer().getUserId());
		Assert.assertEquals("1", purch.getPaymentOption());
		Assert.assertEquals("최영호", purch.getReceiverName());
		Assert.assertEquals("010-1234-5678", purch.getReceiverPhone());
		Assert.assertEquals("서울", purch.getDivyAddr());
		Assert.assertEquals("빨리빨리", purch.getDivyRequest());
		Assert.assertEquals("1", purch.getTranCode());
		Assert.assertEquals("2014-06-02 00:00:00.0", purch.getDivyDate());

		Assert.assertNotNull(purchaseService.getPurchase(10001));
		Assert.assertNotNull(purchaseService.getPurchase(10043));
	}
	
	//@Test
	public void testGetPurchaseProdNo() throws Exception {
		
		Purchase purch = new Purchase();
		//==> 필요하다면...
//		purch.setPurchaseProd(prod);
//		purch.setBuyer(user);
//		purch.setPaymentOption("1");
//		purch.setReceiverName("최영호");
//		purch.setReceiverPhone("010-1234-5678");
//		purch.setDivyAddr("서울");
//		purch.setDivyRequest("빨리빨리");
//		purch.setTranCode("2");
//		purch.setDivyDate("20140602");
		
		purch = purchaseService.getPurchase2(10041);
		
		//==> console 확인
		System.out.println(purch);
		
		//==> API 확인
		Assert.assertEquals(10041, purch.getPurchaseProd().getProdNo());
		Assert.assertEquals("user18", purch.getBuyer().getUserId());
		Assert.assertEquals("1", purch.getPaymentOption());
		Assert.assertEquals("최영호", purch.getReceiverName());
		Assert.assertEquals("010-1234-5678", purch.getReceiverPhone());
		Assert.assertEquals("서울", purch.getDivyAddr());
		Assert.assertEquals("빨리빨리", purch.getDivyRequest());
		Assert.assertEquals("1", purch.getTranCode());
		Assert.assertEquals("2014-06-02 00:00:00.0", purch.getDivyDate());

		Assert.assertNotNull(purchaseService.getPurchase2(10002));
		Assert.assertNotNull(purchaseService.getPurchase2(10041));
	}	
	
	//@Test
	 public void testUpdatePurchase() throws Exception{
		
		Purchase purch = purchaseService.getPurchase(10043);
		
		Assert.assertNotNull(purch);

		Assert.assertEquals("1", purch.getPaymentOption());
		Assert.assertEquals("최영호", purch.getReceiverName());
		Assert.assertEquals("010-1234-5678", purch.getReceiverPhone());
		Assert.assertEquals("서울", purch.getDivyAddr());
		Assert.assertEquals("빨리빨리", purch.getDivyRequest());
		Assert.assertEquals("2014-06-02 00:00:00.0", purch.getDivyDate());

		purch.setPaymentOption("0");
		purch.setReceiverName("개새끼");
		purch.setReceiverPhone("010-9999-9999");
		purch.setDivyAddr("부산");
		purch.setDivyRequest("천천히천천히");
		purch.setDivyDate("22221212");
		
		System.out.println(purch);
		
		purchaseService.updatePurcahse(purch);
		
		purch = purchaseService.getPurchase(10043);
		
		Assert.assertNotNull(purch);
		
		//==> console 확인
		System.out.println(purch);
			
		//==> API 확인
		Assert.assertEquals("0", purch.getPaymentOption());
		Assert.assertEquals("개새끼", purch.getReceiverName());
		Assert.assertEquals("010-9999-9999", purch.getReceiverPhone());
		Assert.assertEquals("부산", purch.getDivyAddr());
		Assert.assertEquals("천천히천천히", purch.getDivyRequest());
		Assert.assertEquals("2222-12-12 00:00:00.0", purch.getDivyDate());
	 }
	 
	//@Test
	public void testUpdatePurchaseTranCode() throws Exception{
		
		Purchase purch = purchaseService.getPurchase(10043);
			
		Assert.assertNotNull(purch);
		Assert.assertEquals("1", purch.getTranCode());
		
		purch.setTranCode("2");
		System.out.println(purch);
		
		purchaseService.updateTranCode(purch);
		
		Assert.assertNotNull(purch);
		System.out.println(purch);
		
		Assert.assertEquals("2", purch.getTranCode());
	}
	 
	 //==>  주석을 풀고 실행하면....
	 //@Test
	 public void testGetProdductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(5);
	 	Map<String,Object> map = purchaseService.getPurchaseList(search, "user18");
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(4, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 }
	 
}