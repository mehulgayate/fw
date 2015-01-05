package com.fw.web.support;

import org.apache.commons.lang.StringUtils;

import com.evalua.entity.support.DataStoreManager;
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
				return Status.BANNED;
			}
		}		
		return Status.VALID;
	}

}
