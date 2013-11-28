package com.papteco.web.beans;

import java.io.Serializable;
import java.util.List;

public class UsersFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 229980401777078388L;
	private String createUserName;
	private String createPassword;
	private String createEmail;
	private List<String> createRoles;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreatePassword() {
		return createPassword;
	}

	public void setCreatePassword(String createPassword) {
		this.createPassword = createPassword;
	}

	public String getCreateEmail() {
		return createEmail;
	}

	public void setCreateEmail(String createEmail) {
		this.createEmail = createEmail;
	}

	public List<String> getCreateRoles() {
		return createRoles;
	}

	public void setCreateRoles(List<String> createRoles) {
		this.createRoles = createRoles;
	}

	@Override
	public String toString() {
		return "UsersFormBean [createUserName=" + createUserName
				+ ", createPassword=" + createPassword + ", createEmail="
				+ createEmail + ", createRoles=" + createRoles + "]";
	}

}
