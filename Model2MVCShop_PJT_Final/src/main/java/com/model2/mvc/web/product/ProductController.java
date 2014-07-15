package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {

	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	///Constructor
	public ProductController() {
		System.out.println("==> ProductController default Constructor call.....");
	}

	///Method
	@RequestMapping("/addProduct.do")
	public ModelAndView addProduct(HttpServletRequest request) throws Exception {
		
		if (FileUpload.isMultipartContent(request)) {
			//==> Eclipse worksppace / Project ����� ������ ��
			//String temDir = "D:\\workspace03(analysis)\\1.Model2MVCShop(ins)\\WebContent\\images\\uploadFiles\\";
			String temDir = "C:\\workspace\\Model2MVCShop_PJT_Final\\WebContent\\images\\uploadFiles\\";
			//String temDir = ".";
			//String temDir2 = "/uploadFiles/";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			// setSizeThreshold�� ũ�⸦ ����� �Ǹ� ������ ��ġ�� �ӽ÷� �����Ѵ�.
			fileUpload.setSizeMax(1024 * 1024 * 10);
			// �ִ� 1�ް����� ���ε� ���� (1024 * 1024 * 100) <- 100MB
			fileUpload.setSizeThreshold(1024 * 100); // �ѹ��� 100k ������ �޸𸮿� ����

			if (request.getContentLength() < fileUpload.getSizeMax()) {
				Product prodVO = new Product();

				StringTokenizer token = null;

				// parseRequest()�� FileItem�� �����ϰ� �ִ� ListŸ���� �����Ѵ�.
				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size(); // html page���� ���� ������ ������ ���Ѵ�.
				for (int i = 0; i < Size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);
					// isFormField()�� ���ؼ� ������������ �Ķ�������� �����Ѵ�. �Ķ���Ͷ�� true
					if (fileItem.isFormField()) {
						if (fileItem.getFieldName().equals("manuDate")){
							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
							String manuDate = token.nextToken()	+ token.nextToken() + token.nextToken();
							prodVO.setManuDate(manuDate);
						}
						else if(fileItem.getFieldName().equals("prodName"))
							prodVO.setProdName(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("prodDetail"))
							prodVO.setProdDetail(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("price"))
							prodVO.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
					} else { // ���������̸�..
						// out.print("���� : " + fileItem.getFieldName() + " = " +
						// fileItem.getName());
						// out.print("(" + fileItem.getSize() + " byte)<br>");

						if (fileItem.getSize() > 0) { // ������ �����ϴ� if
							int idx = fileItem.getName().lastIndexOf("\\");
							// getName()�� ��θ� �� �������� ������ lastIndexOf�� �߶󳽴�
							if (idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1);
							prodVO.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}
						}else{
							prodVO.setFileName("/images/empty.GIF");
						}
					}// else
				}// for

				productService.addProduct(prodVO);
				
				request.setAttribute("prodVO", prodVO);
			} else { // ���ε��ϴ� ������ setSizeMax���� ū���
				int overSize = (request.getContentLength() / 1000000);
				System.out
						.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮��"
								+ overSize + "MB�Դϴ�');");
				System.out.println("history.back();</script>");
			}
		} else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�..");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/product/productView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/getProduct.do")
	public ModelAndView getProduct(@RequestParam("prodNo") int prodNo,
														@RequestParam("menu") String menu,
														@RequestParam("tranCode") String tranCode,
														HttpServletRequest request, 
														HttpServletResponse response) throws Exception {
		
		Product vo = productService.getProduct(prodNo);
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		///��� ��ǰ ������ ��Ű�� ��´�.
		///////////////////////////////////////////////////////////////////////////////////////////////
		response.setCharacterEncoding("euc-kr");
		request.setCharacterEncoding("euc-kr");
		
		String history=null;
		
		Integer no = new Integer(vo.getProdNo());
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					history = cookie.getValue();
				
				}
			}
		}
		history+=","+no.toString();
		Cookie cookie = new Cookie("history",history);
		response.addCookie(cookie);
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prodVO", vo);
		modelAndView.addObject("menu", menu);
		modelAndView.addObject("tranCode", tranCode);
		modelAndView.addObject("prodNo", prodNo);
		if(menu.equals("manage")){
			modelAndView.setViewName("redirect:/updateProductView.do");
		}else{
			modelAndView.setViewName("/product/readProduct.jsp");	
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/updateProductView.do")
	public ModelAndView updateProductView(@RequestParam("prodNo") int prodNo,
																	@RequestParam("menu") String menu,
																	@RequestParam("tranCode") String tranCode) throws Exception {
		
		Product prod = productService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/product/updateProductView.jsp");
		modelAndView.addObject("prodVO", prod);
		modelAndView.addObject("menu", menu);
		modelAndView.addObject("tranCode", tranCode);
		
		return modelAndView;
	}
	
	@RequestMapping("/updateProduct.do")
	public ModelAndView updateProduct(HttpServletRequest request) throws Exception {
		
		String tranCode = request.getParameter("tranCode");
		int prodNo=0;

		if (FileUpload.isMultipartContent(request)) {
			//==> Eclipse worksppace / Project ����� ������ ��
			//String temDir = "D:\\workspace03(analysis)\\1.Model2MVCShop(ins)\\WebContent\\images\\uploadFiles\\";
			String temDir = "C:\\workspace\\Model2MVCShop_PJT_Final\\WebContent\\images\\uploadFiles\\";
			//String temDir2 = "/uploadFiles/";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			// setSizeThreshold�� ũ�⸦ ����� �Ǹ� ������ ��ġ�� �ӽ÷� �����Ѵ�.
			fileUpload.setSizeMax(1024 * 1024 * 10);
			// �ִ� 1�ް����� ���ε� ���� (1024 * 1024 * 100) <- 100MB
			fileUpload.setSizeThreshold(1024 * 100); // �ѹ��� 100k ������ �޸𸮿� ����

			if (request.getContentLength() < fileUpload.getSizeMax()) {
				Product prodVO = new Product();
				StringTokenizer token = null;

				// parseRequest()�� FileItem�� �����ϰ� �ִ� ListŸ���� �����Ѵ�.
				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size(); // html page���� ���� ������ ������ ���Ѵ�.
				for (int i = 0; i < Size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);
					// isFormField()�� ���ؼ� ������������ �Ķ�������� �����Ѵ�. �Ķ���Ͷ�� true
					if (fileItem.isFormField()) {
						if (fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem
									.getString("euc-kr"), "-");

							String manuDate = token.nextToken();
							while(token.hasMoreTokens())
									manuDate+= token.nextToken();
							prodVO.setManuDate(manuDate);
						} else if (fileItem.getFieldName().equals("prodName"))
							prodVO.setProdName(fileItem.getString("euc-kr"));
						else if (fileItem.getFieldName().equals("prodDetail"))
							prodVO.setProdDetail(fileItem.getString("euc-kr"));
						else if (fileItem.getFieldName().equals("price"))
							prodVO.setPrice(Integer.parseInt(fileItem
									.getString("euc-kr")));
						else if (fileItem.getFieldName().equals("prodNo")) {
							prodNo = Integer.parseInt(fileItem
									.getString("euc-kr"));
							prodVO.setProdNo(prodNo);
						}
					} else { // ���������̸�..
						// out.print("���� : " + fileItem.getFieldName() + " = " +
						// fileItem.getName());
						// out.print("(" + fileItem.getSize() + " byte)<br>");

						if (fileItem.getSize() > 0) { // ������ �����ϴ� if
							int idx = fileItem.getName().lastIndexOf("\\");
							// getName()�� ��θ� �� �������� ������ lastIndexOf�� �߶󳽴�
							if (idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1);
							prodVO.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}
						}
					}// else
				}// for
				productService.updateProduct(prodVO);

				request.setAttribute("prodVO", prodVO);
			} else { // ���ε��ϴ� ������ setSizeMax���� ū���
				int overSize = (request.getContentLength() / 1000000);
				System.out
						.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮��"
								+ overSize + "MB�Դϴ�');");
				System.out.println("history.back();</script>");
			}
		} else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�..");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/getProduct.do");
		modelAndView.addObject("prodNo", prodNo);
		modelAndView.addObject("tranCode", tranCode);
		modelAndView.addObject("prodNo", "ok");
		return modelAndView;
	}
	
	@RequestMapping("/listProduct.do")
	public ModelAndView listProduct(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
														@RequestParam("menu") String menu,
														@ModelAttribute("search") Search search) throws Exception {
		search.setCurrentPage(currentPage);
		
		search.setPageSize(pageSize);
		
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage	= 
					new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/product/listProduct.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("menu", menu);
		
		return modelAndView;
	}
}
