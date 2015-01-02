package com.fw.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.fw.entity.User;
import com.fw.entity.support.Repository;


@Controller
public class UserController {

	@Resource
	private Repository repository;
	
	@Resource
	private DataStoreManager dataStoreManager;
	
	@RequestMapping("/login")
	public ModelAndView showLogin(){
		return new ModelAndView("login/login");
	}
	
	@RequestMapping("/authenticate")
	public ModelAndView authenticate(@RequestParam String email, @RequestParam String password, HttpSession session){
		
		User user=repository.findUserByEmail(email);
		
		if(user!=null && user.getPassword().equals(password)){
			session.setAttribute("user", user);
			return new ModelAndView("redirect:/");		
			
		}else{
			return new ModelAndView("loging-failure");
		}
	}
	
	@RequestMapping("/signup")
	public ModelAndView showRegister(){
		return new ModelAndView("login/signup");
	}
	
	@RequestMapping("/signup-complete")
	public ModelAndView signupComplete(){
		return new ModelAndView("login/signup-complete");
	}
	
	@RequestMapping("/add-user")
	public ModelAndView addUser(@ModelAttribute User user){
		
		User foundUser=repository.findUserByEmail(user.getEmail());
		
		if(foundUser==null){
			dataStoreManager.save(user);
		}
		
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping("/")
	public ModelAndView showHome(HttpServletRequest request, HttpSession session){
		
		User user=(User) session.getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("user/index");
	}
	
}
