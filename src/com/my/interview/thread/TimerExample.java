package com.my.interview.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerExample {

	public static void main(String[] args) {
		TimerTask task = new TestTimerTask();
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(task, 0, 5 * 1000);
		System.out.println("Timer started.");
		
		try {
			Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		timer.cancel();
		System.out.println("Timer canceled.");
		
		try {
			Thread.sleep(30 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Main thread ends.");
	}
}

class TestTimerTask extends TimerTask {

	@Override
	public void run() {
		System.out.println("Timer task started at: " + new Date());
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Timer task ended at: " + new Date());
	}
	
}