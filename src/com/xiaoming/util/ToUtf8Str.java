package com.xiaoming.util;

import java.io.UnsupportedEncodingException;

/**
 * ��iso-8859-1������ַ���ת��Ϊutf-8������ַ���
 * @author Xman
 *
 */
public class ToUtf8Str {
	public static String func(String str){
		String ret = null;
		if(str == null)
			return ret;
		try {
			ret = new String(str.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
