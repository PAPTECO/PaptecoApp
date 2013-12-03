package com.papteco.web.beans;

import java.io.Serializable;

public class ClientRequestBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5767199660140603279L;
	private char actionType;
	private String prjCde;
	private Object prjObj;
	private QueueItem qItem;
	private IPItem ipItem;
	private String reqUser;
	
	public ClientRequestBean(char actionType){
		this.actionType = actionType;
	}
	
	public ClientRequestBean(char actionType,String prjCde){
		this.actionType = actionType;
		this.prjCde = prjCde;
	}
	
	public char getActionType() {
		return actionType;
	}

	public void setActionType(char actionType) {
		this.actionType = actionType;
	}

	public String getPrjCde() {
		return prjCde;
	}
	public void setPrjCde(String prjCde) {
		this.prjCde = prjCde;
	}
	public Object getPrjObj() {
		return prjObj;
	}
	public void setPrjObj(Object prjObj) {
		this.prjObj = prjObj;
	}

	public QueueItem getqItem() {
		return qItem;
	}

	public void setqItem(QueueItem qItem) {
		this.qItem = qItem;
	}

	public IPItem getIpItem() {
		return ipItem;
	}

	public void setIpItem(IPItem ipItem) {
		this.ipItem = ipItem;
	}

	public String getReqUser() {
		return reqUser;
	}

	public void setReqUser(String reqUser) {
		this.reqUser = reqUser;
	}

}
