package com.my.interview.string;

public class StringIsPalindrome {

	private static boolean isPalindrome1(String param) {
		if (param == null) return false;
		
		StringBuilder sb = new StringBuilder(param);
		StringBuilder reverse = sb.reverse();
		return param.equals(reverse.toString());
	}
	
	private static boolean isPalindrome2(String param) {
		if (param == null) return false;
		
		int len = param.length() / 2;
		for (int index = 0; index < len; index++) {
			if (param.charAt(index) != param.charAt(param.length() - index - 1))
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		String testStr1 = "abcdeedcba";
		String testStr2 = "abcde";
		String testStr3 = "ssabcde";
		
		System.out.println(isPalindrome1(testStr1));
		System.out.println(isPalindrome2(testStr1));
		System.out.println(isPalindrome1(testStr2));
		System.out.println(isPalindrome2(testStr2));
		System.out.println(isPalindrome1(testStr3));
		System.out.println(isPalindrome2(testStr3));
		
	}
}
