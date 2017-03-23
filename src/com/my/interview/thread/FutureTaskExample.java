package com.my.interview.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskExample {

	public static void main(String[] args) {

		MyTask task1 = new MyTask(1);
		MyTask task2 = new MyTask(2);

		FutureTask<String> futureTask1 = new FutureTask<String>(task1);
		FutureTask<String> futureTask2 = new FutureTask<String>(task2);

		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(futureTask1);
		executor.submit(futureTask2);
		
		while(true) {
			try {
				if (futureTask1.isDone() && futureTask2.isDone()) {
					System.out.println("Done");
					executor.shutdown();
					return;
				}
				if (!futureTask1.isDone()) {
					// wait indefinitely for future task to complete
					System.out.println("FutureTask1 output=" + futureTask1.get());
				}

				System.out.println("Waiting for FutureTask2 to complete");
				String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
				if (s != null) {
					System.out.println("FutureTask2 output=" + s);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}

class MyTask implements Callable<String> {

	private int waitTimeInSecond;
	
	public MyTask(int waitTimeInSecond) {
		this.waitTimeInSecond = waitTimeInSecond;
	}
	@Override
	public String call() throws Exception {
		Thread.sleep(waitTimeInSecond * 1000);
		return Thread.currentThread().getName();
	}
	
}