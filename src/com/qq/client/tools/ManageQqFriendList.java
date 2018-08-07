/**
 * 管理好友列表
 */
package com.qq.client.tools;
import java.util.*;

import com.qq.client.view.QqFriendList;

import java.io.*;

public class ManageQqFriendList {

	private static HashMap hm = new HashMap<String,QqFriendList>();
	
	public static void addQqFriendList(String qqid,QqFriendList qqFriendList) {
		hm.put(qqid, qqFriendList);
	}
	
	public static QqFriendList getQqFriendList(String qqId) {
		return (QqFriendList)hm.get(qqId);
	}
	
	public static  void removeQqFrientList(String qqid) {
		hm.remove(qqid);
	}
}
