package model;

import java.io.File;
import java.io.Serializable;
import java.util.Set;

public class Message implements Serializable {
	private Users  from;
	private String filename;
	private long fileSize;
	private MessageType  type;
	private Set<File> files;
	 private static final long serialVersionUID =1L;
	public Set<File> getFiles() {
		return files;
	}
	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public String toString() {
		return  from + ", filename=" + filename ;
	}
	public Message() {
		super();
	}
	public Message(Users from, String filename, long fileSize, MessageType type) {
		super();
		this.from = from;
		this.filename = filename;
		this.fileSize = fileSize;
		this.type = type;
	}
	public Users getFrom() {
		return from;
	}
	public void setFrom(Users from) {
		this.from = from;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	

}
