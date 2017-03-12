package com.my.interview.string;

public class RemoveGivenCharacter {

	private static String remove(String str, char c) {
		if (str == null) return null;
		return str.replaceAll(String.valueOf(c), "");
	}
	
	public static void main(String[] args) {
		String testStr = "abcabc";
		char c = 'a';
		System.out.println(remove(testStr, c));
	}
}
