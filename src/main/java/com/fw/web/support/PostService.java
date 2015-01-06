package com.fw.web.support;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.evalua.entity.support.DataStoreManager;
import com.fw.entity.GraphData;
import com.fw.entity.GraphData.GraphType;
import com.fw.entity.Post;
import com.fw.entity.Post.Status;
import com.fw.entity.support.Repository;

public class PostService {
	
	private Repository repository;
	private DataStoreManager dataStoreManager;
	private PostConfiguration postConfiguration;
	
	public void setPostConfiguration(PostConfiguration postConfiguration) {
		this.postConfiguration = postConfiguration;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public void setDataStoreManager(DataStoreManager dataStoreManager) {
		this.dataStoreManager = dataStoreManager;
	}
	
	
	public Status processPost(Post post){
		
		for (String word : postConfiguration.getBannedWords()) {
			if(StringUtils.contains(post.getPostText().toLowerCase(), word)){
				GraphData graphData=repository.findGraphData(new Date(), GraphType.WORDS);
				if(graphData==null){
					graphData =new GraphData();
					graphData.setDate(new Date());
				}
				graphData.setCount(graphData.getCount()+1);
				dataStoreManager.save(graphData);
				System.out.println("ABUSE words detected...");
				return Status.BANNED;
			}
		}	
		
		String[] wordsInPost =post.getPostText().split(" ");
		System.out.println("**** Total words "+wordsInPost.length);
		int capitalWordsCount=0;
		for (String string : wordsInPost) {
			boolean capitalWord=true;
			for(int i=0; i < string.length() ;i++){
				if(!Character.isUpperCase(string.charAt(i))){
					capitalWord=false;
					break;
				}
			}
			if(capitalWord){
				capitalWordsCount++;
			}			
		}
		
		System.out.println("**** Total Capital words "+capitalWordsCount);
		
		if(capitalWordsCount > (wordsInPost.length / 2)){
			GraphData graphData=repository.findGraphData(new Date(), GraphType.WORDS);
			if(graphData==null){
				graphData =new GraphData();
				graphData.setDate(new Date());
			}
			graphData.setCount(graphData.getCount()+1);
			dataStoreManager.save(graphData);
			
			return Status.BANNED;
		}
		
		
		return Status.VALID;
	}

}
