package com.papteco.web.beans;

import java.io.Serializable;

public class QueueItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2260222960404463556L;
	private String actionType;
	private String prjCde;
	private String param;
	private String status;
	
	public QueueItem(){}
	
	public QueueItem(String actionType, String prjCde, String param, String status){
		this.actionType = actionType;
		this.prjCde = prjCde;
		this.param = param;
		this.status = status;
	}
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getPrjCde() {
		return prjCde;
	}

	public void setPrjCde(String prjCde) {
		this.prjCde = prjCde;
	}

	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
