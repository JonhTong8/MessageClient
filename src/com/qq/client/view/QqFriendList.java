/**
 * 好友列表界面（包括陌生人和黑名单）
 */
package com.qq.client.view;

import javax.swing.*;

import com.qq.client.tools.ManageClientConServerThread;
import com.qq.client.tools.ManageQqChat;
import com.qq.client.tools.ManageQqFriendList;
import com.qq.client.tools.Settings;
import com.qq.common.Message;
import com.qq.common.MessageType;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class QqFriendList extends JFrame implements ActionListener,MouseListener,WindowListener{
	
	//使用卡片布局
	//处理第一个卡片（好友列表）
	JPanel jphy1,jphy2,jphy3;
	JButton jphy_jb1,jphy_jb2,jphy_jb3;
	JScrollPane jsp1;
	//处理第二个卡片（陌生人名单）
	JPanel jpmsr1,jpmsr2,jpmsr3;
	JButton jpmsr_jb1,jpmsr_jb2,jpmsr_jb3;
	JScrollPane jsp2;
	//处理第三个卡片（黑名单名单）
	JPanel jphmd1,jphmd2,jphmd3;
	JButton jphmd_jb1,jphmd_jb2,jphmd_jb3;
	JScrollPane jsp3;
	
	JLabel [] jbls = null;
	//用户名
	private String myNo = null;
	CardLayout cl = null;
	public static void main (String [] args) {
		//QqFriendList qfl = new QqFriendList();
	}
	//更新好友列表在线情况
	public void updateFriendList(Message m) {
		String onLineFriend[] = m.getCon().split(" ");
		
		for (int i =0;i<onLineFriend.length; i++) {
			//jbls[Integer.parseInt(onLineFriend[i])-1].setEnabled(true);
			jbls[Integer.parseInt(onLineFriend[i])-1].setIcon(new ImageIcon("image/online_user.gif"));
		}
	}
	
	public QqFriendList(String myNo) {
		this.myNo=myNo;
		//处理第一张卡片（显示好友列表）
		jphy1 = new JPanel(new BorderLayout());
		//假定有50个好友
		jphy2 = new JPanel(new GridLayout(50,1,4,4));
		//给jphy2初始化50好友
		jbls = new JLabel[50];
		for (int i = 0;i<jbls.length;i++) {
			jbls[i] = new JLabel(i+1+"",new ImageIcon("image/default_user.gif"),JLabel.LEFT); //头像
			//jbls[i].setEnabled(false);
			jbls[i].addMouseListener(this);//监控鼠标
			jphy2.add(jbls[i]);
		}
		
		jphy3 = new JPanel(new GridLayout(2,1));
	
		jphy_jb1 = new JButton("我的好友");
		jphy_jb2 = new JButton("陌生人");
		jphy_jb2.addActionListener(this);
		jphy_jb3 = new JButton("黑名单");
		jphy_jb3.addActionListener(this);
		
		jphy3.add(jphy_jb2);
		jphy3.add(jphy_jb3);
		jsp1 = new JScrollPane (jphy2);
		
		jphy1.add(jphy_jb1,"North");
		jphy1.add(jsp1,"Center");
		jphy1.add(jphy3,"South");
		
		//处理第二张卡片（陌生人列表）
		jpmsr1 = new JPanel(new GridLayout(2,1));
		
		jpmsr2 = new JPanel(new GridLayout(20,1,4,4));
		JLabel [] jbls2 = new JLabel[20];
		for(int i = 0; i <jbls2.length;i++) {
			jbls2[i] = new JLabel(i+1+"",new ImageIcon("image/default_user.gif"),JLabel.LEFT);
			jbls2[i].addMouseListener(this);
			jpmsr2.add(jbls2[i]);
		}
		
		jpmsr3 = new JPanel(new BorderLayout());
		
		jpmsr_jb1 = new JButton("我的好友");
		jpmsr_jb1.addActionListener(this);
		jpmsr_jb2 = new JButton("陌生人");
		jpmsr_jb3 = new JButton("黑名单");
		jpmsr_jb3.addActionListener(this);
		jsp2 = new JScrollPane (jpmsr2);
		
		jpmsr1.add(jpmsr_jb1);
		jpmsr1.add(jpmsr_jb2);
		
		jpmsr3.add(jpmsr1,"North");
		jpmsr3.add(jpmsr2,"Center");
		jpmsr3.add(jpmsr_jb3,"South");
		
		//处理第三章卡片
		jphmd_jb1 = new JButton("我的好友");
		jphmd_jb1.addActionListener(this);
		jphmd_jb2 = new JButton("陌生人");
		jphmd_jb2.addActionListener(this);
		jphmd_jb3 = new JButton("黑名单");
		
		jphmd2 = new JPanel(new GridLayout(10,1,4,4));
		JLabel [] jbls3 = new JLabel[10];
		for(int i = 0; i <jbls3.length;i++) {
			jbls3[i] = new JLabel(i+1+"",new ImageIcon("image/default_user.gif"),JLabel.LEFT);
			jbls3[i].addMouseListener(this);
			jphmd2.add(jbls3[i]);
		}
		jsp3 = new JScrollPane(jphmd2);
		
		jphmd1 = new JPanel(new GridLayout(3,1));
		jphmd1.add(jphmd_jb1);
		jphmd1.add(jphmd_jb2);
		jphmd1.add(jphmd_jb3);
		
		jphmd3 = new JPanel(new BorderLayout());
		jphmd3.add(jphmd1,"North");
		jphmd3.add(jphmd2,"Center");

		
		
		cl = new CardLayout();
		this.setLayout(cl);
		this.add(jphy1, "1"); //好友列表
		this.add(jpmsr3, "2");//陌生人列表
		this.add(jphmd3, "3");//黑名单列表
		
		Settings settings = new Settings();
		this.setTitle(myNo);
		int panelWidth = 390;
		int panelHeight = 800;
		this.setSize(panelWidth,panelHeight);
		this.setLocation((int)((settings.getScreenWidth()-panelWidth)*0.9), (int)(settings.getScreenHeight()*0.1) );
		this.setVisible(true);
		this.addWindowListener(this);
	}

	public void sendLogoutMessage() {
		Message ms = new Message();
		ms.setSender(this.myNo);
		ms.setMesType(MessageType.message_logout);
		ms.setTime(new java.util.Date().toString());
		//发送给服务器
		try {
			ObjectOutputStream  oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(myNo).getSocket().getOutputStream());
			oos.writeObject(ms);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jphy_jb2 || e.getSource() == jphmd_jb2 ) {
			cl.show(getContentPane(), "2");
		}else if (e.getSource() == jpmsr_jb1 || e.getSource() == jphmd_jb1) {
			cl.show(getContentPane(), "1");
		}else if (e.getSource() == jphy_jb3 || e.getSource() == jpmsr_jb3) {
			cl.show(getContentPane(), "3");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//响应用户双击的事件，并得到好友的编号
		if(e.getClickCount() == 2) {
			String friendNo = ((JLabel)e.getSource()).getText();
			//System.out.println("你希望和 "+friendNo+" 聊天");
			QqChat qqChat = new QqChat(myNo,friendNo);
			
			//把聊天界面加入到对应管理类中
			ManageQqChat.addQqChat(myNo+" "+friendNo, qqChat);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel j1 = (JLabel)e.getSource();
		j1.setForeground(Color.red);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel j1 = (JLabel)e.getSource();
		j1.setForeground(Color.black);
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("我要被关啦");
		ManageQqFriendList.removeQqFrientList(myNo);
		this.sendLogoutMessage();
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
