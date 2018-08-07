/**
 * QQ界面
 */
package com.qq.client.view;

import javax.swing.*;

import com.qq.client.model.*;
import com.qq.client.tools.ManageClientConServerThread;
import com.qq.client.tools.ManageQqFriendList;
import com.qq.client.tools.Settings;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import com.qq.common.*;

public class QqClientLogin extends JFrame implements ActionListener,KeyListener{

	//定义上部组件
	JLabel jbl1;
	//定义中部组件
	JTabbedPane jtp;
	JPanel jp2,jp3,jp4;
	JLabel jbl2,jbl3,jbl4,jbl5;
	JButton jp2_jb1;
	JTextField jp2_jtf;
	JPasswordField jp2_jpf;
	JCheckBox jp2_jcb1,jp2_jcb2;
	
	//定义下部组件
	JPanel jp1;
	JButton jp1_jb1,jp1_jb2,jp1_jb3;
	
	//定义好友列表对象
	QqFriendList qqFriendList = null;
	
	public QqClientLogin() {
	
		//处理上部组件
		jbl1 = new JLabel(new ImageIcon("image/welcome.gif"));
		//处理中部组件
		jp2 = new JPanel(new GridLayout(3,3));
		jp3 = new JPanel();
		jp4 = new JPanel();
		jbl2 = new JLabel("帐   号",JLabel.CENTER);
		jbl3 = new JLabel("密   码",JLabel.CENTER);
		jbl4 = new JLabel("忘记密码",JLabel.CENTER);
		jbl5 = new JLabel("申请密码保护",JLabel.CENTER);
		jp2_jb1= new JButton(new ImageIcon("image/clear.gif"));
		jp2_jtf = new JTextField();
		jp2_jpf = new JPasswordField();
		jp2_jpf.addKeyListener(this);
		jp2_jcb1 = new JCheckBox("隐身登陆");
		jp2_jcb2 = new JCheckBox("记住密码");
		
		jp2.add(jbl2);
		jp2.add(jp2_jtf);
		jp2.add(jp2_jb1);
		jp2.add(jbl3);
		jp2.add(jp2_jpf);
		jp2.add(jbl4);
		jp2.add(jp2_jcb1);
		jp2.add(jp2_jcb2);
		jp2.add(jbl5);
	
		//选项卡窗口
		jtp = new JTabbedPane();
		jtp.add("帐号登陆", jp2);
		jtp.add("手机登陆", jp3);
		jtp.add("邮箱登陆", jp4);
		
		//处理下部组件
		jp1 = new JPanel();
		jp1_jb1 = new JButton(new ImageIcon("image/login.gif"));
		//响应用户点击登录
		jp1_jb1.addActionListener(this);
		jp1_jb2 = new JButton(new ImageIcon("image/cancel.gif"));
		jp1_jb3 = new JButton(new ImageIcon("image/register.gif"));
		
		jp1.add(jp1_jb1);
		jp1.add(jp1_jb2);
		jp1.add(jp1_jb3);
		//显示
		this.add(jbl1,"North");//显示靠上
		this.add(jtp);
		this.add(jp1, "South");//显示靠下
		int panelWidth = 650;
		int panelHeight = 350;
		this.setSize(panelWidth,panelHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Settings settings = new Settings();
		this.setLocation(settings.getScreenWidth()/2-panelWidth/2, settings.getScreenHeight()/2-panelHeight);
		this.setVisible(true);
		
		
	}

	public void login () {
		QqClientUser qqClient = new QqClientUser();
		User u = new User();
		u.setUserId(jp2_jtf.getText().trim());
		u.setPasswd(new String(jp2_jpf.getPassword()));
		
		try {
			if(qqClient.checkUser(u)) {
				qqFriendList = new QqFriendList(u.getUserId());
				//发送一个要求返回在线好友信息的信息包
				ObjectOutputStream oos = new ObjectOutputStream
						(ManageClientConServerThread.getClientConServerThread(u.getUserId()).getSocket().getOutputStream());
				//做一个message包 准备发送
				Message m = new Message();
				m.setMesType(MessageType.message_get_onLineFriend);
				m.setSender(u.getUserId());
				oos.writeObject(m);
				
				
				
				ManageQqFriendList.addQqFriendList(u.getUserId(), qqFriendList);
				this.dispose();//关闭登录界面
			}else {
				JOptionPane.showMessageDialog(this,"用户名密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jp1_jb1) {
			this.login();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_ENTER) {
			this.login();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
