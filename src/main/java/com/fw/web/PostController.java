package com.fw.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.evalua.entity.support.DataStoreManager;
import com.fw.entity.Post;
import com.fw.entity.User;
import com.fw.entity.support.Repository;


@Controller
public class PostController {

	@Resource
	private Repository repository;
	
	@Resource
	private DataStoreManager dataStoreManager;
	
	
	@RequestMapping("/posts")
	public ModelAndView showPosts(HttpSession session){
		User user=(User) session.getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/login");
		}
		
		List<Post> posts=repository.listPostsByUser(user);
		return new ModelAndView("user/posts").addObject("posts",posts);
	}
	
	
	@RequestMapping("/new-post")
	public ModelAndView addPost(@ModelAttribute Post post, HttpSession session){
		User user=(User) session.getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/login");
		}
		
		post.setUser(user);
		dataStoreManager.save(post);
		return new ModelAndView("redirect:/posts");
		
	}	
	
}
