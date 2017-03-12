package com.my.interview.thread;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {
	
	public static void main(String[] args) {
		// fixedThreadPool
		String poolName = "fixedThreadPool";
		showTimeWithStatus(poolName, 0);
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
		for (int i=0; i<10; i++) {
			fixedThreadPool.execute(new Task("fixedThreadPool " + i));
		}
		
		fixedThreadPool.shutdown();
		testTerminate(fixedThreadPool, poolName); 
		showTimeWithStatus(poolName, 1);
		
		// ScheduledThreadPool
		poolName = "scheduledThreadPoolAtFixedRate";
		showTimeWithStatus(poolName, 0);
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
		for (int i=0; i<3; i++) {
			waitForNSecs(1);
			scheduledThreadPool.scheduleAtFixedRate(new Task("scheduleAtFixedRate" + i), 0, 3, TimeUnit.SECONDS);
		}
		waitForNSecs(20);
		scheduledThreadPool.shutdown();
		testTerminate(scheduledThreadPool, poolName);
		showTimeWithStatus(poolName, 1);
		
		//
		poolName = "scheduledThreadPoolWithFixedDelay";
		showTimeWithStatus(poolName, 0);
		scheduledThreadPool = Executors.newScheduledThreadPool(3);
		for (int i=0; i<3; i++) {
			waitForNSecs(1);
			scheduledThreadPool.scheduleWithFixedDelay(new Task("scheduleWithFixedDelay" + i), 0, 3, TimeUnit.SECONDS);
		}
		waitForNSecs(20);
		scheduledThreadPool.shutdown();
		testTerminate(scheduledThreadPool, poolName);
		showTimeWithStatus(poolName, 1);
		
		//
		poolName = "scheduledThreadPool";
		showTimeWithStatus(poolName, 0);
		scheduledThreadPool = Executors.newScheduledThreadPool(5);
		for (int i=0; i<3; i++) {
			scheduledThreadPool.schedule(new Task("schedule" + i), 3, TimeUnit.SECONDS);
		}
		waitForNSecs(10);
		scheduledThreadPool.shutdown();
		testTerminate(scheduledThreadPool, poolName);
		showTimeWithStatus(poolName, 1);
		
		// ThreadPoolExecutor
		RejectedExecutionHandler rejectedHandler = new RejectionHandler();
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(3);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 3, TimeUnit.MINUTES, queue, rejectedHandler);
		MonitorTask monitorTask = new MonitorTask(executor, 1);
		Thread monitorThread = new Thread(monitorTask);
		monitorThread.start();
		for (int i=0; i<15; i++) {
			executor.execute(new Task("executor " + i));
		}
		waitForNSecs(20);
		executor.shutdown();
		waitForNSecs(5);
		monitorTask.shutdown();
		testTerminate(executor, "executor");
	}
	
	private static void showTimeWithStatus(String poolName, int type) {
		if (type == 0) {
			System.out.println(poolName + " start at " + new Date());
		} else if (type == 1) {
			System.out.println(poolName + " end at " + new Date());
			System.out.println();
		}
	}
	
	private static void testTerminate(ExecutorService pool, String poolName) {
		while(!pool.isTerminated()) {
			;
		}
		System.out.println(poolName + " done.");
	}

	private static void waitForNSecs(int num) {
		try {
			Thread.sleep(num * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Task implements Runnable {

	private String msg;

	public Task(String msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " start at " + new Date() + ". msg: " + msg);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " end at " + new Date() + ". msg: " + msg);
	}
	
	@Override
    public String toString(){
        return this.msg;
    }
}

class RejectionHandler implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println(r.toString() + " is rejected.");
	}
}

class MonitorTask implements Runnable {

	private ThreadPoolExecutor executor;
	private int delay;
	private boolean run=true;
	
	public MonitorTask(ThreadPoolExecutor executor, int delay) {
		this.executor = executor;
		this.delay = delay;
	}
	
	public void shutdown() {
		this.run = false;
	}
	
	@Override
	public void run() {
		while(run) {
			System.out.println(
					String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
	                        this.executor.getPoolSize(),
	                        this.executor.getCorePoolSize(),
	                        this.executor.getActiveCount(),
	                        this.executor.getCompletedTaskCount(),
	                        this.executor.getTaskCount(),
	                        this.executor.isShutdown(),
	                        this.executor.isTerminated()));
			
			try {
				Thread.sleep(delay * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}