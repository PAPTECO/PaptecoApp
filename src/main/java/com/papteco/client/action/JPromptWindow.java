package com.papteco.client.action;

import javax.swing.JOptionPane;

import com.papteco.client.ui.RunClientApp;

public class JPromptWindow {
	public static RunClientApp frame;
	
	public static void showWarnMsg(String msg){
		JOptionPane.showMessageDialog(frame, msg, "Warning",
				JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void showInfoMsg(String msg){
		JOptionPane.showMessageDialog(frame, msg, "Information",
				JOptionPane.PLAIN_MESSAGE);
	}
}
