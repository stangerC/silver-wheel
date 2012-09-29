package com.silver.wheel.text;

import java.io.UnsupportedEncodingException;

public abstract class UnicodeParse {
	public static String intToString(int code, String charsetName) throws UnsupportedEncodingException {
		byte[] bytes = intToBytes(code);
		return new String(bytes, charsetName);
	}
	
	public static byte[] intToBytes(int anInt) {
		byte[] bytes = new byte[4];
		
		bytes[0] = (byte)((anInt >> 24) & 0xFF);
		bytes[1] = (byte)((anInt >> 16) & 0xFF);
		bytes[2] = (byte)((anInt >> 8) & 0xFF);
		bytes[3] = (byte)(anInt & 0xFF);
		
		return bytes;
	}
	
	public static void printBytes(byte[] bytes) {
		if(bytes != null) {
			for (byte b : bytes) {
				System.out.println(">>>>>b:" + b);
			}
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(">>>>> string:" + intToString(26087, "UTF-8"));
		printBytes("旧".getBytes());
		printBytes(intToBytes(26087));
	}
}
