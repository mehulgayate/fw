package com.fw.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.fw.entity.GraphData;
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
	
	
	@RequestMapping("/admin/grapth-data")
	@ResponseBody
	public String getGraphData(){		
		List<GraphData> graphDatas=repository.listGraphData();
		return GraphData.listToJSON(graphDatas).toString();		
	}
	
	
}
