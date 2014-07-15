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
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
//==> Meta-Data �� �پ��ϰ� Wiring ����...
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {

		Product prod = new Product();
		prod.setProdName("testProduct");
		prod.setProdDetail("testProduct_Detail");
		prod.setManuDate("20140602");
		prod.setPrice(10000);
		prod.setFileName("root/testProduct_fileName");
		
		productService.addProduct(prod);
		prod = productService.getProduct(10061);
		
		//==> console Ȯ��
		System.out.println(prod);
		
		//==> API Ȯ��
		Assert.assertEquals("testProduct", prod.getProdName());
		Assert.assertEquals("testProduct_Detail", prod.getProdDetail());
		Assert.assertEquals("20140602", prod.getManuDate());
		Assert.assertEquals(10000, prod.getPrice());
		Assert.assertEquals("root/testProduct_fileName", prod.getFileName());
	}
	
	@Test
	public void testGetProduct() throws Exception {
		
		Product prod = new Product();
		//==> �ʿ��ϴٸ�...
//		prod.setProdName("testProduct");
//		prod.setProdDetail("testProduct_Detail");
//		prod.setManuDate("20140602");
//		prod.setPrice(10000);
//		prod.setFileName("root/testProduct_fileName");
		
		prod = productService.getProduct(10061);
		
		//==> console Ȯ��
		System.out.println(prod);
		
		//==> API Ȯ��
		Assert.assertEquals("testProduct", prod.getProdName());
		Assert.assertEquals("testProduct_Detail", prod.getProdDetail());
		Assert.assertEquals("20140602", prod.getManuDate());
		Assert.assertEquals(10000, prod.getPrice());
		Assert.assertEquals("root/testProduct_fileName", prod.getFileName());

		Assert.assertNotNull(productService.getProduct(10002));
		Assert.assertNotNull(productService.getProduct(10003));
	}
	
	//@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product prod = productService.getProduct(10061);
		
		Assert.assertNotNull(prod);

		Assert.assertEquals("testProduct", prod.getProdName());
		Assert.assertEquals("testProduct_Detail", prod.getProdDetail());
		Assert.assertEquals("20140602", prod.getManuDate());
		Assert.assertEquals(10000, prod.getPrice());
		Assert.assertEquals("root/testProduct_fileName", prod.getFileName());

		prod.setProdName("�������Ʊ�");
		prod.setProdDetail("�翬���ٲ����??");
		prod.setManuDate("20151212");
		prod.setPrice(55555);
		prod.setFileName("system/temp");
		System.out.println(prod);
		
		productService.updateProduct(prod);
				
		prod = productService.getProduct(10061);
		Assert.assertNotNull(prod);
		
		//==> console Ȯ��
		System.out.println(prod);
			
		//==> API Ȯ��
		Assert.assertEquals("�������Ʊ�", prod.getProdName());
		Assert.assertEquals("�翬���ٲ����??", prod.getProdDetail());
		Assert.assertEquals("20151212", prod.getManuDate());
		Assert.assertEquals(55555, prod.getPrice());
		Assert.assertEquals("system/temp", prod.getFileName());
	 }
	 
	 //==>  �ּ��� Ǯ�� �����ϸ�....
	 @Test
	 public void testGetProdductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 @Test
	 public void testGetProductListByProdNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10061");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 @Test
	 public void testGetProductListByProdName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("�������Ʊ�");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(2, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
	 
	 @Test
	 public void testGetProductListByProdPrice() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("2");
	 	search.setSearchKeyword("55555");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(2, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}