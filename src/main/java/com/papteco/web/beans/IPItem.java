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
	
	private String additional1;
	private String additional2;
	private String additional3;
	private String additional4;
	private String additional5;
	
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
