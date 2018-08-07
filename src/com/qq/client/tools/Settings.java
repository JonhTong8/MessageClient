package com.qq.client.tools;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Settings {

	
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
