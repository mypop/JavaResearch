package com.my.interview.thread;

import java.util.Calendar;

public class ThreadJoinExample {

	public static void main(String[] args) {
		Thread t1 = new Thread(new MyRunnable(), "t1");
		Thread t2 = new Thread(new MyRunnable(), "t2");
		Thread t3 = new Thread(new MyRunnable(), "t3");
		
		t1.start();
		
		try {
			long start = Calendar.getInstance().getTimeInMillis();
			t1.join(1000);
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("t1 end in " + (end - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		t2.start();
		try {
			long start = Calendar.getInstance().getTimeInMillis();
			t2.join();
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("t2 end in " + (end - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		t3.start();
		try {
			long start = Calendar.getInstance().getTimeInMillis();
			t1.join();
			t2.join();
			t3.join();
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("t3 end in " + (end - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("All thread are dead.");
	}
}

class MyRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("Thread started: " + Thread.currentThread().getName());
		try {
			Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Thread ended: " + Thread.currentThread().getName());
	}
	
}
