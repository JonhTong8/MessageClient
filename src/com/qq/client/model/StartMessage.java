package com.qq.client.model;

import com.qq.client.view.QqClientLogin;

public class StartMessage implements Runnable{

	public static void main (String[] args) {
		StartMessage startMessage = new StartMessage();
	}
	
	public StartMessage() {
		
		QqClientLogin qqClientLogin = new QqClientLogin();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
