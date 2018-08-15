package com.qq.client.tools;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Settings {

	private String serverIp = "192.168.3.9";
	private int serverPort = 10001;
	
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	
 	
	public Settings() {
		
	}
	
	private Dimension getScreen () {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();
		return screensize;
	}
	
	public int getScreenWidth() {
		Dimension screensize = this.getScreen();
		return screensize.width;
	}
	
	public int getScreenHeight() {
		Dimension screensize = this.getScreen();
		return screensize.height;
	}
}
