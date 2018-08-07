/**
 * 用于验证用户的登录
 */
package com.qq.client.model;
import com.qq.common.*;

public class QqClientUser {

	//public boolean QqClientUser (User u) {
	public boolean checkUser (User u) throws ClassNotFoundException {

			return new QqClientConnect().SendLoginToServer(u);
	
	}
}
