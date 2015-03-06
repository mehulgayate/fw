package com.fw.web;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.evalua.entity.support.DataStoreManager;
import com.fw.entity.FileAttachment;
import com.fw.entity.FileAttachment.FileType;
import com.fw.entity.GraphData;
import com.fw.entity.Post;
import com.fw.entity.GraphData.GraphType;
import com.fw.entity.Post.Status;
import com.fw.entity.User;
import com.fw.entity.User.PrivacyStatus;
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
	
	
	@RequestMapping("/remove-post")
	public ModelAndView deletePosts(@RequestParam Long id, @RequestParam(required = false) String type){
		Post post=repository.findPostById(id);
		dataStoreManager.delete(post);
		if(StringUtils.isNotBlank(type)){
			return new ModelAndView("redirect:/admin/verify-posts-list");
		}
		return new ModelAndView("redirect:/posts");
	}
	
	@RequestMapping("/verify-post")
	public ModelAndView veryfyPosts(@RequestParam Long id,@RequestParam(required = false) String type){
		Post post=repository.findPostById(id);
		post.setStatus(Status.VALID);
		dataStoreManager.save(post);
		if(StringUtils.isNotBlank(type)){
			return new ModelAndView("redirect:/admin/verify-posts-list");
		}
		return new ModelAndView("redirect:/posts");
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session){	

		session.invalidate();
		return new ModelAndView("redirect:/login");
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
	public ModelAndView addPost(@ModelAttribute Post post, HttpSession session,HttpServletRequest request){
		User user=(User) session.getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/login");
		}

		if(user.getStatus()==com.fw.entity.User.Status.BLACK_LISTED){
			return new ModelAndView("redirect:/black-listed-alert");
		}
		
		Status status=postService.processPost(post, user);
		post.setStatus(status);
		
		FileAttachment fileAttachment =null;
		String[] imageExtentions={"png","jpg","jpeg"};
		String[] videoExtentions={"mp4","flv","mpeg"};
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("file");
			
			
			
			if (file != null && !file.isEmpty()) {
				try {
					fileAttachment = new FileAttachment();
					fileAttachment.setAttachment(file.getBytes());
					fileAttachment.setExtension(file.getContentType());
					fileAttachment.setName(file.getOriginalFilename());
					
					String[] splited=file.getOriginalFilename().split("\\.");
					String extention= splited[splited.length-1];
					List<String> imageList=Arrays.asList(imageExtentions);
					List<String> videoList=Arrays.asList(videoExtentions);
					
					if(imageList.contains(extention.toLowerCase())){
						fileAttachment.setFileType(FileType.IMAGE);
					}else if( videoList.contains(extention.toLowerCase())){
						fileAttachment.setFileType(FileType.VIDEO);
					}else{
						fileAttachment.setFileType(FileType.OTHER);
					}
					
					dataStoreManager.save(fileAttachment);
					post.setStatus(Status.ADMIN_VERIFICATION);

				} catch (Exception e) {
					System.out.println("Unable to store Image");
					e.printStackTrace();
				}
			}
		}
		

		
		
		post.setUser(user);		
		post.setFileAttachment(fileAttachment);
		dataStoreManager.save(post);

		if(post.getStatus().equals(Status.BANNED)){
			user.setBannedWordsCount(user.getBannedWordsCount()+1);
			if(user.getBannedWordsCount()>postConfiguration.getBannedWordsAllowed()){
				user.setStatus(com.fw.entity.User.Status.BLACK_LISTED);			
				GraphData graphData=repository.findGraphData(new Date(), GraphType.BLACK_LIST);
				if(graphData==null){
					graphData =new GraphData();
					graphData.setDate(new Date());
					graphData.setGraphType(GraphType.BLACK_LIST);
				}				
				graphData.setCount(graphData.getCount()+1);
				dataStoreManager.save(graphData);
			}
			dataStoreManager.save(user);
		}

		//Tagging 
		if(StringUtils.isNotBlank(post.getTags())){
			String[] tags=post.getTags().split(",");
			String[] ignoreProperties = {"id" , "taggedBy", "user","ownFeed"};
			for (String tag : tags) {
				List<User> users =repository.listUsersByName(tag.trim());
				for (User taggedUser : users) {
					
					Post postForUser= new Post();
					BeanUtils.copyProperties(post, postForUser, ignoreProperties);
					postForUser.setOwnFeed(false);
					postForUser.setStatus(Status.NOT_VERIFIED);
					postForUser.setTaggedBy(user);
					postForUser.setUser(taggedUser);
					postForUser.setFileAttachment(fileAttachment);

					Status newStatus=postService.processPost(postForUser, taggedUser);
					if(newStatus.equals(Post.Status.BANNED)){
						postForUser.setStatus(newStatus);
					}
					dataStoreManager.save(postForUser);
				}
			}
		}

		return new ModelAndView("redirect:/posts");

	}	

}
