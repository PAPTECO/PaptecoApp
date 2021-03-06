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
	private String timestamp;
	private String mailfileSuffix;
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMailfileSuffix() {
		return mailfileSuffix;
	}

	public void setMailfileSuffix(String mailfileSuffix) {
		this.mailfileSuffix = mailfileSuffix;
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

}
