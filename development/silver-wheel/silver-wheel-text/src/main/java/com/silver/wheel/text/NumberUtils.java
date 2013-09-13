package com.silver.wheel.text;

public abstract class NumberUtils {
	public static final int BYTE_BIT = 8;
	/**
	 * 输出一个byte对应的无符号数的2进制字符串。默认输出4位2进制数。
	 * @param aByte
	 * @return
	 */
	public static String toUnsignBinaryString(Byte aByte) {
		return toUnsignBinaryString(aByte, BYTE_BIT);
	}
	/**
	 * 输出一个byte对应的无符号数的2进制字符串，需要指定输出的位数。
	 * @param aByte
	 * @param digit
	 * @return
	 */
	public static String toUnsignBinaryString(Byte aByte, int digit) {
		StringBuilder builder = new StringBuilder(Integer.toBinaryString(aByte));		
		if(digit <= BYTE_BIT) {
			return builder.substring(24);
		}else {
			builder.delete(1, 24);
			for(int i = digit - BYTE_BIT; i > 0; i --) {
				builder.insert(1, "0");
			}
			return builder.toString();
		}
	}
	
	public static void main(String[] args) {
		String tempStr = null;
		tempStr = NumberUtils.toUnsignBinaryString(Byte.parseByte("-128"), 10);
		System.out.println(tempStr);
	}
}
