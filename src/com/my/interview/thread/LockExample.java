package com.my.interview.thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
	
	public static void main(String[] args) throws InterruptedException {
		Resource resource = new Resource();
		
		//
		SynchronizedLockTask syncTask1 = new SynchronizedLockTask(resource);
		
		Thread thread1 = new Thread(syncTask1, "thread1");
		Thread thread2 = new Thread(syncTask1, "thread2");
		
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		
		// 
		ConcurrentLockTask concurrentTask1 = new ConcurrentLockTask(resource);
		
		Thread thread3 = new Thread(concurrentTask1, "thread3");
		Thread thread4 = new Thread(concurrentTask1, "thread4");
		
		thread3.start();
		thread4.start();
		thread3.join();
		thread4.join();
		
	}
}

class Resource {
	
	public void doSomething() {
		System.out.println(Thread.currentThread().getName() + " do something starts at " + new Date());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " do something ends at " + new Date());
	}
	
	public void doLogging() {
		System.out.println(Thread.currentThread().getName() + " do logging starts at " + new Date());
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " do logging ends at " + new Date());
	}
}

class SynchronizedLockTask implements Runnable {

	private Resource resource;
	
	public SynchronizedLockTask(Resource resource) {
		this.resource = resource;
	}
	
	@Override
	public void run() {
		synchronized (resource) {
			resource.doSomething();
		}
		resource.doLogging();
	}
	
}

class ConcurrentLockTask implements Runnable {

	private Resource resource;
	private Lock lock;
	
	public ConcurrentLockTask(Resource resource) {
		this.resource = resource;
		this.lock = new ReentrantLock();
	}
	
	@Override
	public void run() {
		try {
			if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
				resource.doSomething();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		resource.doLogging();
	}
	
}