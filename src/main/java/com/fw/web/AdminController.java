package com.fw.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.fw.entity.GraphData;
import com.fw.entity.User;
import com.fw.entity.GraphData.GraphType;
import com.fw.entity.User.Status;
import com.fw.entity.support.Repository;


@Controller
public class AdminController {

	@Resource
	private DataStoreManager dataStoreManager;

	@Resource
	private Repository repository;


	@RequestMapping("/admin")
	public ModelAndView login(HttpSession httpSession){
		ModelAndView mv=new ModelAndView("admin/index");
		String user=(String) httpSession.getAttribute("user");
		if(user ==null || !user.equals("admin")){
			return new ModelAndView("redirect:/admin/login");
		}
		
		mv.addObject("users",repository.listUsers());
		mv.addObject("blockedUsers",repository.listBlockedUsers());
		mv.addObject("latestUsers",repository.listLatestUsers());
		mv.addObject("posts",repository.listPostsForAdmin());
		return mv;
	}

	@RequestMapping("/admin/black-listed-users-list")
	public ModelAndView blockedUsers(HttpSession httpSession){
		ModelAndView mv=new ModelAndView("admin/black-listed-users-list");

		mv.addObject("users",repository.listBlockedUsers());
		mv.addObject("latestUsers",repository.listLatestUsers());
		mv.addObject("posts",repository.listPostsForAdmin());
		return mv;
	}

	@RequestMapping("/admin/latest-users-list")
	public ModelAndView latestRegistration(HttpSession httpSession){
		ModelAndView mv=new ModelAndView("admin/latest-users-list");
		mv.addObject("users",repository.listLatestUsers());
		mv.addObject("blockedUsers",repository.listBlockedUsers());
		mv.addObject("posts",repository.listPostsForAdmin());
		return mv;
	}
	
	@RequestMapping("/admin/verify-posts-list")
	public ModelAndView verifyPostsList(HttpSession httpSession){
		ModelAndView mv=new ModelAndView("admin/verify-posts-list");
		mv.addObject("latestUsers",repository.listLatestUsers());
		mv.addObject("blockedUsers",repository.listBlockedUsers());
		mv.addObject("posts",repository.listPostsForAdmin());
		return mv;
	}

	@RequestMapping("/admin/add-new-movie")
	public ModelAndView showAddNew(HttpSession httpSession){
		ModelAndView mv=new ModelAndView("admin/add-new-movie");

		return mv;
	}	

	@RequestMapping("/admin/mailbox")
	public ModelAndView showMailbox(){
		return new ModelAndView("admin/mailbox");
	}

	@RequestMapping("/admin/upload-movies")
	public ModelAndView uploadBulk(){
		return new ModelAndView("admin/upload-movies");
	}

	@RequestMapping("/admin/graphs")
	public ModelAndView showGraph(){
		return new ModelAndView("admin/graph");
	}

	@RequestMapping("/admin/delete-user")
	public ModelAndView deleteUser(@RequestParam Long id){
		User user=repository.findUserById(id);
		user.setStatus(Status.DELETED);
		dataStoreManager.save(user);

		return new ModelAndView("redirect:/admin");
	}

	@RequestMapping("/admin/activate-user")
	public ModelAndView activateUser(@RequestParam Long id){
		User user=repository.findUserById(id);
		user.setStatus(Status.ACTIVE);
		dataStoreManager.save(user);

		return new ModelAndView("redirect:/admin");
	}


	@RequestMapping("/admin/grapth-data-words")	
	public ModelAndView showGraphsWords(){		
		return new ModelAndView("admin/grapth-data-words");	
	}

	@RequestMapping("/admin/login")	
	public ModelAndView login(){		
		return new ModelAndView("admin/login");	
	}

	@RequestMapping("/admin/authenticate")	
	public ModelAndView authenticate(@RequestParam String email, @RequestParam String password, HttpSession session){	
		if(email.equals("admin") && password.equals("1234")){
			session.setAttribute("user", "admin");
			return new ModelAndView("redirect:/admin/");	
		}
		return new ModelAndView("redirect:/admin/login");
	}

	@RequestMapping("/admin/logout")	
	public ModelAndView logout(HttpSession session){		
		session.invalidate();
		return new ModelAndView("redirect:/admin/login");	
	}

	@RequestMapping("/admin/grapth-data-black-list")	
	public ModelAndView showGraphsBlack(){		
		return new ModelAndView("admin/grapth-data-black-list");	
	}


	@RequestMapping("/admin/grapth-data-words.json")
	@ResponseBody
	public String getGraphData(){		
		List<GraphData> graphDatas=repository.listGraphData(GraphType.WORDS);
		return GraphData.listToJSON(graphDatas).toString();		
	}

	@RequestMapping("/admin/grapth-data-black-list.json")
	@ResponseBody
	public String getBlackListGraphData(){		
		List<GraphData> graphDatas=repository.listGraphData(GraphType.BLACK_LIST);
		return GraphData.listToJSON(graphDatas).toString();		
	}


}
