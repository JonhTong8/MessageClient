/**
 * 聊天界面
 * 客户端处于读取信息的状态，所以用线程实现
 */
package com.qq.client.view;

import javax.swing.*;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.client.*;
import com.qq.client.model.QqClientConnect;
import com.qq.client.tools.ManageClientConServerThread;
import com.qq.client.tools.Settings;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class QqChat extends JFrame implements ActionListener,KeyListener{

	JTextArea jta = null;
	JTextField jtf = null;
	JButton jb = null;
	JPanel jp = null;
	//发送者
	private String myName;
	private String friendName;
	public static void main(String[] args) {
		//QqChat qqchat = new QqChat("张希如");
	}
	
	public QqChat(String myName,String friendName) {
		this.myName=myName;
		this.friendName=friendName;
		
		jta = new JTextArea();
		jtf = new JTextField(15);
		jtf.addKeyListener(this);
		jb = new JButton("发送");
		jb.addActionListener(this);
		jp = new JPanel();
		jp.add(jtf);
		jp.add(jb);
		
		this.add(jta,"Center");
		this.add(jp, "South");
		this.setTitle("你正在和"+friendName+"聊天");
		this.setIconImage((new ImageIcon("image/").getImage()));
		int panelWidth = 300;
		int panelHeight = 200;
		this.setSize(panelWidth, panelHeight);
		Settings settings = new Settings();
		this.setLocation( (settings.getScreenWidth()-panelWidth)/2, (settings.getScreenHeight()-panelHeight)/2);
		this.setVisible(true);
	}

	public void sendMessage() {
		//用户发送
		Message ms = new Message();
		ms.setSender(this.myName);
		ms.setGetter(this.friendName);
		ms.setMesType(MessageType.message_comm_mes);
		ms.setCon(jtf.getText());
		jtf.setText(null);
		ms.setTime(new java.util.Date().toString());
		//发送给服务器
		try {
			ObjectOutputStream  oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(myName).getSocket().getOutputStream());
			oos.writeObject(ms);
			String info = ms.getSender()+" 给 "+ms.getGetter()+" 说 "+ms.getCon()+"\r\n";
			this.jta.append(info);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void showMessage(Message ms) {
		String info = ms.getSender()+" 给 "+ms.getGetter()+" 说 "+ms.getCon()+"\r\n";
		this.jta.append(info);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jb) {
			//用户点击发送
			this.sendMessage();	
		}
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == e.VK_ENTER) {
			//用户按下回车
			this.sendMessage();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}





