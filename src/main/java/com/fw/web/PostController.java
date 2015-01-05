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
import com.fw.entity.Post.Status;
import com.fw.entity.User;
import com.fw.entity.support.Repository;
import com.fw.web.support.PostConfiguration;
import com.fw.web.support.PostService;


@Controller
public class PostController {

	@Resource
	private Repository repository;
	
	@Resource
	private DataStoreManager dataStoreManager;
	
	@Resource
	private PostConfiguration postConfiguration;
	
	@Resource
	private PostService postService;
	
	
	@RequestMapping("/posts")
	public ModelAndView showPosts(HttpSession session){
		User user=(User) session.getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/login");
		}
		
		List<Post> posts=repository.listPostsByUser(user);
		return new ModelAndView("user/posts").addObject("posts",posts);
	}
	
	@RequestMapping("/black-listed-alert")
	public ModelAndView showBlackListedAlert(HttpSession session){
		User user=(User) session.getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/login");
		}
		
		
		return new ModelAndView("user/black-listed-alert");
	}
	
	
	@RequestMapping("/new-post")
	public ModelAndView addPost(@ModelAttribute Post post, HttpSession session){
		User user=(User) session.getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/login");
		}
		
		if(user.getStatus()==com.fw.entity.User.Status.BLACK_LISTED){
			return new ModelAndView("redirect:/black-listed-alert");
		}
		
		Status status=postService.processPost(post);
		post.setStatus(status);
		post.setUser(user);		
		dataStoreManager.save(post);
		
		if(post.getStatus().equals(Status.BANNED)){
			user.setBannedWordsCount(user.getBannedWordsCount()+1);
			if(user.getBannedWordsCount()>postConfiguration.getBannedWordsAllowed()){
				user.setStatus(com.fw.entity.User.Status.BLACK_LISTED);				
			}
			dataStoreManager.save(user);
		}
		
		return new ModelAndView("redirect:/posts");
		
	}	
	
}
