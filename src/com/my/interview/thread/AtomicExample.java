package com.my.interview.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {

	public static void main(String[] args) throws InterruptedException {
		
		PrimaryCountTask primaryTask = new PrimaryCountTask();
		
		Thread primaryThread1 = new Thread(primaryTask, "primaryThread1");
		primaryThread1.start();
		Thread primaryThread2 = new Thread(primaryTask, "primaryThread2");
		primaryThread2.start();
		
		primaryThread1.join();
		primaryThread2.join();
		
		System.out.println("primary count result: " + primaryTask.getCount());
		
		AtomicCountTask atomicTask = new AtomicCountTask();
		
		Thread atomicThread1 = new Thread(atomicTask, "atomicThread1");
		atomicThread1.start();
		Thread atomicThread2 = new Thread(atomicTask, "atomicThread2");
		atomicThread2.start();

		atomicThread1.join();
		atomicThread2.join();
		
		System.out.println("atomic count result: " + atomicTask.getCount().get());
	}
}

class PrimaryCountTask implements Runnable {
	
	private int count;
	
	@Override
	public void run() {
		for (int i=1; i<5; i++) {
			try {
				Thread.sleep( i * 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count ++;
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}

class AtomicCountTask implements Runnable {

	private AtomicInteger count = new AtomicInteger();
	
	@Override
	public void run() {
		for (int i=1; i<5; i++) {
			try {
				Thread.sleep( i * 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count.incrementAndGet();
		}
	}

	public AtomicInteger getCount() {
		return count;
	}

	public void setCount(AtomicInteger count) {
		this.count = count;
	}
	
}