package com.xiaoming.util;

import java.io.UnsupportedEncodingException;

/**
 * °Ñiso-8859-1±àÂëµÄ×Ö·û´®×ª»»Îªutf-8±àÂëµÄ×Ö·û´®
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
