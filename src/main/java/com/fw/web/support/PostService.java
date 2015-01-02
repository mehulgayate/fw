package com.fw.web.support;

import com.evalua.entity.support.DataStoreManager;
import com.fw.entity.support.Repository;

public class PostService {
	
	private Repository repository;
	private DataStoreManager dataStoreManager;
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public void setDataStoreManager(DataStoreManager dataStoreManager) {
		this.dataStoreManager = dataStoreManager;
	}
	
	
	

}
