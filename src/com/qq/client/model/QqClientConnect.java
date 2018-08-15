/**
 * 客户端连接服务器的后台
 */
package com.qq.client.model;
import java.util.*;
import java.net.*;
import java.io.*;

import com.qq.client.tools.ClientConServerThread;
import com.qq.common.*;
import com.qq.client.tools.*;
import com.qq.client.tools.*;

public class QqClientConnect {
	
	public  Socket s;
	public String serverIp= new Settings().getServerIp();
	public int serverPort = new Settings().getServerPort();
	public boolean SendLoginToServer(Object o) throws ClassNotFoundException {
		boolean bl = false;
		try {
			System.out.println(serverIp+" "+serverPort);
			s = new Socket (serverIp,serverPort);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			
			Message ms = (Message)ois.readObject();
			if(ms.getMesType().equals("1")) {
				
				
				//创建一个该qq号登陆的客户端 与服务端保持通信连接的线程
				ClientConServerThread ccst = new ClientConServerThread(s);
				ccst.start();
				ManageClientConServerThread.addClientConServerThread(((User)o).getUserId(), ccst);
				
				bl = true;
			}else {
				
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bl;
	}
	
	public void SendInfoToServer(Object o) {
		
		try {
		s = new Socket (serverIp,serverPort);
		
		
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
