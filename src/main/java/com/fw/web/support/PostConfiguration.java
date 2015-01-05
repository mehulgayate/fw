package com.fw.web.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostConfiguration {

	private String bannedWordsString;
	private Integer bannedWordsAllowed;

	public String getBannedWordsString() {
		return bannedWordsString;
	}

	public void setBannedWordsString(String bannedWordsString) {
		this.bannedWordsString = bannedWordsString;
	}
	
	private List<String> bannedWords = new ArrayList<String>(0);	
	
	public List<String> getBannedWords() {
		return bannedWords;
	}

	public void setBannedWords(List<String> bannedWords) {
		this.bannedWords = bannedWords;
	}
	
	public Integer getBannedWordsAllowed() {
		return bannedWordsAllowed;
	}

	public void setBannedWordsAllowed(Integer bannedWordsAllowed) {
		this.bannedWordsAllowed = bannedWordsAllowed;
	}

	public void init(){
		
		String[] words = bannedWordsString.split(",");		
		bannedWords=Arrays.asList(words);
	}
	
}
