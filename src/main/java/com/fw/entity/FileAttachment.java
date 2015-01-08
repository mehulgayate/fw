package com.fw.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.persistence.Column;
import javax.persistence.Entity;
import org.apache.commons.io.IOUtils;
import com.evalua.entity.support.EntityBase;

@Entity
public class FileAttachment extends EntityBase{

	public enum FileType{
		IMAGE, VIDEO, OTHER;
	}

	private String name;
	private String extension;
	
	private String mimeType;
	private int size;
	private byte[] attachment = new byte[0];
	private FileType fileType=FileType.IMAGE;
	
	
	private Status status;
	

	public enum Status {
		ACTIVE, BANNED, INACTIVE;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}	

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	// @Column(nullable = false)
	public String getMimeType() {
		return mimeType;
	}	

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Column(length = 131072)
	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] bs) {
		this.attachment = bs;
		this.size = bs.length;
	}

	public void readAttachmentFrom(InputStream in) throws IOException {
		setAttachment(IOUtils.toByteArray(in));
	}

	public void writeAttachmentTo(OutputStream out) throws IOException {
		IOUtils.write(getAttachment(), out);
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}		
}
