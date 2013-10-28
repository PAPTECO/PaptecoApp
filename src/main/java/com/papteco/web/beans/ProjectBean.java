package com.papteco.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ProjectBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8155353323885191907L;

	@PrimaryKey
	private String projectId;
	
	private String projectCde;
	
//	@SecondaryKey(relate = Relationship.ONE_TO_ONE)
	private String clientNo;
	
//	@SecondaryKey(relate = Relationship.ONE_TO_ONE)
	private String createDate;
	
//	@SecondaryKey(relate = Relationship.ONE_TO_ONE)
	private String uniqueNo;

	private List<FolderBean> folderTree = new ArrayList<FolderBean>();

	private String createdBy;

	private Date createdAt;

	private String shortDesc;
	
	private String longDesc;
	
	private List<String> totalFileList = new ArrayList<String>();

	public List<String> getTotalFileList() {
		return totalFileList;
	}

	public void setTotalFileList(List<String> totalFileList) {
		this.totalFileList = totalFileList;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<FolderBean> getFolderTree() {
		return folderTree;
	}

	public void setFolderTree(List<FolderBean> folderTree) {
		this.folderTree = folderTree;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getProjectCde() {
		return projectCde;
	}

	public void setProjectCde(String projectCde) {
		this.projectCde = projectCde;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

}
