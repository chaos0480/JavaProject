package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
public class PurchaseController {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
		
	public PurchaseController() {
		System.out.println("==> PurchaseController default Constructor call.....");
	}

	@RequestMapping("/addPurchase.do")
	public String addPurchase(@ModelAttribute("purchase") Purchase purchVO,
											@ModelAttribute("product") Product prodVO,
											@ModelAttribute("user") User userVO,
											Model model) throws Exception {
		
		System.out.println("/addPurchase.do");
		
		purchVO.setPurchaseProd(prodVO);
		purchVO.setBuyer(userVO);
		purchaseService.addPurchase(purchVO);
		model.addAttribute("purchVO", purchVO);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	@RequestMapping("/addPurchaseView.do")
	public String addPurchaseView(@RequestParam("prod_no") int prodNo,
													Model model,
													HttpSession session) throws Exception {
		
		System.out.println("/addPurchaseView.do");
	
		Product prodVO = productService.getProduct(prodNo);
		User user = (User) session.getAttribute("user");
		
		model.addAttribute("prodVO", prodVO);
		model.addAttribute("user", user);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping("/getPurchase.do")
	public String getPurchase(@RequestParam("tranNo") int tran, Model model) throws Exception {
		
		System.out.println("/getPurchase.do");
		
		Purchase purchVO = purchaseService.getPurchase(tran);
		model.addAttribute("purchVO", purchVO);
		
		return "forward:/purchase/getPurchaseView.jsp";
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public String updatePurchaseView(@RequestParam("tranNo") int tran, Model model) throws Exception {
	
		System.out.println("/updatePurchaseView.do");
		
		Purchase purchVO = purchaseService.getPurchase(tran);
		model.addAttribute("purchVO", purchVO);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	@RequestMapping("/updatePurchase.do")
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase,
												@RequestParam("tranNo") int tran, 
												Model model) throws Exception	{
		
		System.out.println("/updatePurchase.do");
		
		purchaseService.updatePurcahse(purchase);
		model.addAttribute("tranNo", tran);
		
		return "forward:/getPurchase.do";	
		}
	
	@RequestMapping("/listPurchase.do")
	public String listPurchase(@ModelAttribute("search") Search search ,
											Model model , HttpSession session) throws Exception{
			
			System.out.println("/listPurchase.do");
			
			User user = (User)session.getAttribute("user");
			
			if(search.getCurrentPage() ==0 ){
				search.setCurrentPage(1);
			}
			search.setPageSize(pageSize);
			
			// Business logic 수행
			Map<String , Object> map=purchaseService.getPurchaseList(search, user.getUserId());
			
			Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
			System.out.println("ListPurchaseAction ::"+resultPage);
			
			// Model 과 View 연결
			model.addAttribute("list", map.get("list"));
			model.addAttribute("resultPage", resultPage);
			model.addAttribute("search", search);
			
			return "forward:/purchase/listPurchase.jsp";
	}
	
	@RequestMapping("/updateTranCode.do")
	public String updateTranCode(@RequestParam("tranNo") int tranNo, 
												@RequestParam("tranCode") String tranCode,
												Model model) throws Exception {
		
		System.out.println("/updateTranCode.do");
		
		Purchase purvhaseVO = purchaseService.getPurchase(tranNo);
		purvhaseVO.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purvhaseVO);
		
		return "forward:/listPurchase.do";
	}
	
	@RequestMapping("/updateTranCodeByProd.do")
	public String updateTranCodeByProd(@RequestParam("prodNo") int prodNo, 
														@RequestParam("tranCode") String tranCode,
														Model model) throws Exception {
		
		System.out.println("/updateTranCodeByProd.do");
		
		Purchase purchVO = purchaseService.getPurchase2(prodNo);
		purchVO.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchVO);
		
		return "forward:/listProduct.do?menu=manage";
	}
}






