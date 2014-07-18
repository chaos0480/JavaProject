package com.market.farm.web.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.apache.bcel.generic.RET;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.market.farm.HomeController;
import com.market.farm.common.Page;
import com.market.farm.common.Search;
import com.market.farm.service.domain.User;
import com.market.farm.service.user.UserService;

@Controller
public class UserController {

	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	///Constructor
	public UserController() {
		System.out.println("==> UserController default Constructor call.....");
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletResponse response) throws IOException {
		logger.info("Welcome home! The client locale is {}.", locale);
		System.out.println("index test...");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}
	
	@RequestMapping(value = "/ajaxJson.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject ajaxJson(HttpServletResponse response) throws IOException{
		
		System.out.println("Jquery Ajax JSON Test...");
		
		/*
		 * profileImg
		 * endDate
		prodImg
		prodName
		origin
		price
		unit*/
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", "박종호");
		jsonObj.put("profileImg", "farmer.jpg");
		jsonObj.put("endDate", "2014년 7월 18일");
		jsonObj.put("prodImg", "apple.jpg");
		jsonObj.put("prodName", "사과");
		jsonObj.put("origin", "충남 당진");
		jsonObj.put("price", new Integer("2000"));
		jsonObj.put("unit", "개");
		
		
		return jsonObj;
	}
	@RequestMapping("/checkDuplication.do")
	public ModelAndView checkDuplication(@RequestParam("userId") String userId) throws Exception {
		
		boolean result = userService.checkDuplication(userId);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/checkDuplication.jsp");
		modelAndView.addObject("result", new Boolean(result));
		modelAndView.addObject("userId", userId);
		
		return modelAndView;
	}

	@RequestMapping("/addUser.do")
	public ModelAndView addUser(@ModelAttribute("user") User user) throws Exception {
		userService.addUser(user);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/user/loginView.jsp");
		
		return modelAndView;
	}
		
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public ModelAndView login() throws Exception {
						
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/index.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("user") User user,
												HttpSession session) throws Exception {
		User dbVO = userService.loginUser(user);
		
//		String sessionId=((User)session.getAttribute("user")).getUserId();
//
//		if(sessionId.equals(user.getUserId())){
//			session.setAttribute("user", dbVO);
//		}
				
		session.setAttribute("user", dbVO);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/index.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/index.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/getUser.do")
	public ModelAndView getUser(@RequestParam("userId") String userId) throws Exception {
		User user = userService.getUser(userId);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/getUser.jsp");
		modelAndView.addObject("user", user);
		
		return modelAndView;
	}
	
	@RequestMapping("/updateUserView.do")
	public ModelAndView updateUserView(@RequestParam("userId") String userId) throws Exception {
		User user = userService.getUser(userId);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/updateUser.jsp");
		modelAndView.addObject("user", user);
		
		return modelAndView;
	}
	
	@RequestMapping("/updateUser.do")
	public ModelAndView updateUser(@ModelAttribute("user") User user,
														HttpSession session) throws Exception {
		userService.updateUser(user);
		
		String sessionId=((User)session.getAttribute("user")).getUserId();

		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userId", user.getUserId());
		modelAndView.setViewName("redirect:/getUser.do");
		
		return modelAndView;
	}
	
	@RequestMapping("/listUser.do")
	public ModelAndView listUser(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
													@ModelAttribute("search") Search search) throws Exception {
		search.setCurrentPage(currentPage);
		
		search.setPageSize(pageSize);
		
		Map<String , Object> map=userService.getUserList(search);
		
		Page resultPage	= 
					new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/listUser.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		return modelAndView;
	}
}
