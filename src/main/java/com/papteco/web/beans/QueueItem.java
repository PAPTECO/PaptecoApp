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
	
	private String additional1;
	private String additional2;
	private String additional3;
	private String additional4;
	private String additional5;
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

}
