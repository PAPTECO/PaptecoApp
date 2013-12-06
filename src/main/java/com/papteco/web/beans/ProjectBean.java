package com.papteco.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ProjectBean implements Serializable,Cloneable {

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

	private String additional1;
	private String additional2;
	private String additional3;
	private String additional4;
	private String additional5;
	private Object additional6;
	private Object additional7;
	private Object additional8;
	private Object additional9;
	private Object additional10;
	
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

	public String getAdditional1() {
		return additional1;
	}

	public void setAdditional1(String additional1) {
		this.additional1 = additional1;
	}

	public String getAdditional2() {
		return additional2;
	}

	public void setAdditional2(String additional2) {
		this.additional2 = additional2;
	}

	public String getAdditional3() {
		return additional3;
	}

	public void setAdditional3(String additional3) {
		this.additional3 = additional3;
	}

	public String getAdditional4() {
		return additional4;
	}

	public void setAdditional4(String additional4) {
		this.additional4 = additional4;
	}

	public String getAdditional5() {
		return additional5;
	}

	public void setAdditional5(String additional5) {
		this.additional5 = additional5;
	}

	public Object getAdditional6() {
		return additional6;
	}

	public void setAdditional6(Object additional6) {
		this.additional6 = additional6;
	}

	public Object getAdditional7() {
		return additional7;
	}

	public void setAdditional7(Object additional7) {
		this.additional7 = additional7;
	}

	public Object getAdditional8() {
		return additional8;
	}

	public void setAdditional8(Object additional8) {
		this.additional8 = additional8;
	}

	public Object getAdditional9() {
		return additional9;
	}

	public void setAdditional9(Object additional9) {
		this.additional9 = additional9;
	}

	public Object getAdditional10() {
		return additional10;
	}

	public void setAdditional10(Object additional10) {
		this.additional10 = additional10;
	}

	public ProjectBean clone(){ 
		ProjectBean o = null; 
		try{ 
		o = (ProjectBean)super.clone(); 
		}catch(CloneNotSupportedException e){ 
		e.printStackTrace(); 
		} 
		return o; 
		} 
}
