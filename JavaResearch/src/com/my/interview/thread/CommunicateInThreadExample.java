package com.my.interview.thread;

public class CommunicateInThreadExample {
	
	public static void main(String[] args) {
		Message message = new Message("process it");
		Waiter waiter = new Waiter(message);
		new Thread(waiter, "waiter").start();
		
		Waiter waiter1 = new Waiter(message);
		new Thread(waiter1, "waiter1").start();
	
		// example for notify()
		SingleNotifier singleNotifier = new SingleNotifier(message);
		Thread singleNotiferThread = new Thread(singleNotifier, "singleNotifier");
		singleNotiferThread.start();
		System.out.println("All threads started for single notifier.");
		try {
			singleNotiferThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println();
		// example for notifyAll()
		AllNotifier allNotifier = new AllNotifier(message);
		new Thread(waiter, "waiter").start();
		Thread allNotifierThread = new Thread(allNotifier, "allNotifier");
		allNotifierThread.start();
		System.out.println("All threads started for all notifier.");
		
	}
}

class Message {
	private String message;
	
	public Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

class Waiter implements Runnable {

	private Message msg;
	
	public Waiter(Message msg) {
		this.msg = msg;
	}
	
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		synchronized (msg) {
			try {
				System.out.println(name + " waiter is waiting to get notified at time: " + System.currentTimeMillis());
				msg.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(name + " waiter got notified at time: " + System.currentTimeMillis());
			System.out.println(name + " processed: " + msg.getMessage());
		}
	}
}

class SingleNotifier implements Runnable {

	private Message msg;
	
	public SingleNotifier(Message msg) {
		this.msg = msg;
	}
	
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println(name + " started.");
		try {
			Thread.sleep(1000);
			synchronized (msg) {
				msg.setMessage(name + " SingleNotifier work done");
				msg.notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

class AllNotifier implements Runnable {

	private Message msg;
	
	public AllNotifier(Message msg) {
		this.msg = msg;
	}
	
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println(name + " started.");
		try {
			Thread.sleep(1000);
			synchronized (msg) {
				msg.setMessage(name + " AllNotifier work done");
				msg.notifyAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}