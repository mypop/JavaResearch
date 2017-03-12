package com.my.interview.string;

import java.util.HashSet;
import java.util.Set;

public class PrintAllPermutations {

	private static Set<String> findAllPermutations(String str) {
		Set<String> result = new HashSet<String>();
		if (str.length() <= 1) {
			result.add(str);
			return result;
		}
		if (str.length() == 2) {
			StringBuilder sb1 = new StringBuilder(str);
			result.add(str);
			result.add(sb1.reverse().toString());
			return result;
		}
		
		for (int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			StringBuilder sb = new StringBuilder();
			sb.append(str.substring(0, i)).append(str.substring(i + 1));
			
			Set<String> permutations = findAllPermutations(sb.toString());
			for (String s : permutations) {
				StringBuilder rs = new StringBuilder();
				rs.append(c).append(s);
				result.add(rs.toString());
			}
		}
		return result;
	}
	
	private static void printAllPermutations(String str) {
		System.out.println("print all permutations:");
		Set<String> set = findAllPermutations(str);
		for (String s : set) {
			System.out.println(s);
		}
	}
	
	public static void main(String[] args) {
		String s1 = "ABC";
		String s2 = "ABCD";
		String s3 = "ABCA";
		
		printAllPermutations(s1);
		printAllPermutations(s2);
		printAllPermutations(s3);
	}
}
