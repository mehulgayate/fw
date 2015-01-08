package com.fw.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.evalua.entity.support.EntityBase;

@Entity
public class Post extends EntityBase{
	
	public enum Status{
		VALID, BANNED, NOT_VERIFIED, ADMIN_VERIFICATION;
	}
	
	private String postText;
	private String tags;
	private User user;
	private Status status=Status.NOT_VERIFIED;
	private User taggedBy;
	private Date postedDate=new Date();
	private Boolean ownFeed=true;
	private FileAttachment fileAttachment;
	
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public FileAttachment getFileAttachment() {
		return fileAttachment;
	}
	public void setFileAttachment(FileAttachment fileAttachment) {
		this.fileAttachment = fileAttachment;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	public User getTaggedBy() {
		return taggedBy;
	}
	public void setTaggedBy(User taggedBy) {
		this.taggedBy = taggedBy;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public Boolean getOwnFeed() {
		return ownFeed;
	}
	public void setOwnFeed(Boolean ownFeed) {
		this.ownFeed = ownFeed;
	}	
	
}
