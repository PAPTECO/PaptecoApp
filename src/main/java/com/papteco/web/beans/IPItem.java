package com.papteco.web.beans;

import java.io.Serializable;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class IPItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3553784673278572357L;
	@PrimaryKey
	private String PCID;
	private String PCIP;
	private String PCNAME;
	
	public IPItem(){}
	
	public IPItem(String pcid, String pcip, String pcname){
		this.PCID = pcid;
		this.PCIP = pcip;
		this.PCNAME = pcname;
	}

	public String getPCID() {
		return PCID;
	}

	public void setPCID(String pCID) {
		PCID = pCID;
	}

	public String getPCIP() {
		return PCIP;
	}

	public void setPCIP(String pCIP) {
		PCIP = pCIP;
	}

	public String getPCNAME() {
		return PCNAME;
	}

	public void setPCNAME(String pCNAME) {
		PCNAME = pCNAME;
	}
	
}
