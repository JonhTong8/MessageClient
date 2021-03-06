/**
 * 这是客户端和服务器保持通讯的线程
 */
package com.qq.client.tools;

import java.io.*;
import java.net.*;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.Random;

public class ClientConServerThread extends Thread{

	private Socket s;
	
	public Socket getSocket() {
		return s;
	}


	public ClientConServerThread(Socket s) {
		this.s = s;
	}
	
	
	
	public void run() {
		while (true) {
			//不停地读取从服务端发来的消息
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message)ois.readObject();
			//	System.out.println("读取到从服务器发来的消息"+" 对 "+m.getGetter()+" 说 :"+m.getCon()+"\r\n");
			//	System.out.println(""+m.getMesType());
				if(m.getMesType().equals(MessageType.message_comm_mes)) {
				//把从服务器得到的消息 显示到对应的界面上
					QqChat qqChat = ManageQqChat.getQqChat(m.getGetter()+" "+m.getSender());
					qqChat.showMessage(m);
				}else if(m.getMesType().equals(MessageType.message_ret_onLineFriend)) {
					String con = m.getCon();
					String friends[] = con.split(" ");
					String getter = m.getGetter();
					
					//修改相应的好友列表
					QqFriendList qqFriendList = ManageQqFriendList.getQqFriendList(getter);
					
					//更新在线好友
					if(qqFriendList != null) {
						qqFriendList.updateFriendList(m);
					}
				}else if (m.getMesType().equals(MessageType.message_send_file)) {
					QqChat qqChat = ManageQqChat.getQqChat(m.getGetter()+" "+m.getSender());
					
					String filename = new Random().getRandom(8);
					File directory = new File("f:\\Message_tmp");
					if (!directory.exists()) {
						directory.mkdir();
					}
					File file = new File(directory.getAbsolutePath()+File.separatorChar+filename);
					FileOutputStream fos = new FileOutputStream(file);
					DataInputStream dis =  new DataInputStream(s.getInputStream());
					
					String realFilename = dis.readUTF();
					long fileLength = dis.readLong();
					
					byte[] bytes = new byte[1024];
					int length = 0;
					while ((length = dis.read(bytes,0,bytes.length)) != -1) {
						fos.write(bytes, 0, length);
						fos.flush();
					}
					qqChat.showImage(file.getAbsolutePath().toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
