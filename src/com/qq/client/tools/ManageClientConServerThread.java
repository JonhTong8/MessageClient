/**这是一个管理客户端和服务端保持通讯的线程类
 */

package com.qq.client.tools;
import  java.util.*;

public class ManageClientConServerThread {

	private static HashMap hm = new HashMap<String ,ClientConServerThread>();
	
	public static void addClientConServerThread(String qqId,ClientConServerThread  ccst) {
		hm.put(qqId, ccst);
	}
	
	public static ClientConServerThread getClientConServerThread(String qqId) {
		
		return (ClientConServerThread) hm.get(qqId);
	}
}
