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

	//JTextArea jta = null;
	JTextPane jta = null;
	JTextField jtf = null;
	JButton jb = null;
	JPanel jp = null;
	JPanel jp2 = null;
	JButton jb2 = null; //图片选择按钮
	JFileChooser jfc = null;//文件选择器
	//发送者 接收者
	private String myName;
	private String friendName;
	
	public static void main(String[] args) {
	//	QqChat qqchat = new QqChat("张希如","曹泽通");
	}
	
	public QqChat(String myName,String friendName) {
		this.myName=myName;
		this.friendName=friendName;
		
		int panelWidth = 700;
		int panelHeight = 400;
		jta = new JTextPane();
		jta.setBounds(0, panelHeight*2/3+30, panelWidth, panelHeight*2/3);
		jtf = new JTextField(15);
		jtf.addKeyListener(this);
		jb = new JButton("发送");
		jb.setBounds(0, panelHeight*2/3+30, 30, 30);
		jb.addActionListener(this);
		jp = new JPanel();
		jp.add(jtf);
		jp.add(jb);
		jp.setBounds(0, panelHeight*2/3+30, panelWidth, panelHeight/3-30);
		jp2 = new JPanel(); //功能区 预留8个格子
		jp2.setBounds(0,panelHeight*2/3 , panelWidth, 30);
		jb2 = new JButton(new ImageIcon("image/picture.gif"));
		jb2.addActionListener(this);
		jb2.setBounds(0, 0, 30, 30);
		jp2.add(jb2);

		
		this.add(jta,"Center");
		
		this.add(jp2,"North");
		this.add(jp,"South");
		this.setTitle("你正在和"+friendName+"聊天");
		this.setIconImage((new ImageIcon("image/").getImage()));
		
		this.setSize(panelWidth, panelHeight);
		Settings settings = new Settings();
		this.setLocation( (settings.getScreenWidth()-panelWidth)/2, (settings.getScreenHeight()-panelHeight)/2);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
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
			this.jta.add(new JTextArea(),"info");// append(info);
			oos.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			
		}
	}
	
	public void showMessage(Message ms) {
		String info = ms.getSender()+" 给 "+ms.getGetter()+" 说 "+ms.getCon()+"\r\n";
	//	this.jta.append(info);
		
	}
	
	public void showImage(String image) {
		Icon icon = new ImageIcon(image);
		this.jta.insertIcon(icon);
	}
	
	public void sendImage(String image) {
		//用户发送图片
		Message ms = new Message();
		ms.setGetter(this.friendName);
		ms.setSender(this.myName);
		ms.setMesType(MessageType.message_image);
		ms.setTime(new java.util.Date().toString());
		
		File file = new File(image);
		//发送给服务器
		try {
			//先发送message 再发送图片
			ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(myName).getSocket().getOutputStream());
			oos.writeObject(ms);
			//发送图片
			FileInputStream fis = new FileInputStream(file);
			DataOutputStream dos = new DataOutputStream(ManageClientConServerThread.getClientConServerThread(myName).getSocket().getOutputStream());
			//发送文件名和长度
			dos.writeUTF(file.getName());
			dos.flush();
			dos.writeLong(file.length());
			dos.flush();
			//发送图片
			byte[] bytes = new byte[1024];
			int length = 0;
			long progress = 0;
			while ((length = fis.read(bytes,0,bytes.length)) != -1) {
				dos.write(bytes,0,length);
				dos.flush();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendFile(String filedir) {
		
	}
	
	public void getFile(String filedir) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jb) {
			//用户点击发送
			this.sendMessage();	
		}else if (e.getSource() == jb2) {
				jfc=new JFileChooser();  //图片选择按钮
		        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
		        jfc.showDialog(new JLabel(), "选择图片");  
		        File file=jfc.getSelectedFile();  
		        if(file.isDirectory()){
		        	JOptionPane jop = new JOptionPane();
		        	JOptionPane.showMessageDialog(new JTextArea(), "这是文件夹，请选择图片文件", "警告！", JOptionPane.WARNING_MESSAGE );

		           // System.out.println("文件夹:"+file.getAbsolutePath());  
		        }else if(file.isFile()){  
		        	//如果是文件 就显示（暂时不考虑该文件不是图片）
		        	this.showImage(file.getAbsolutePath().toString());
		        	this.sendImage(file.getAbsolutePath().toString());
		           // System.out.println("文件:"+file.getAbsolutePath());  
		        }  
		        //System.out.println(jfc.getSelectedFile().getName()); 
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





