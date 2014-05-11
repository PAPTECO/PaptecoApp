package com.papteco.client.netty;

import java.util.Properties;

import com.papteco.client.action.EnvConfiguration;

public class BasicBuilder {

	protected Properties envsetting = EnvConfiguration.getEnvSetting();

	protected Integer PortTranslater(String port) {
		return Integer.valueOf(port);
	}
}
