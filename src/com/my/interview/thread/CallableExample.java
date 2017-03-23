package com.my.interview.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		List<Future<String>> list = new ArrayList<Future<String>>();
		Callable<String> task = new CallableTask();
		
		for(int i=0; i<40; i++) {
			Future<String> future = executor.submit(task);
			list.add(future);
		}
		
		for (Future<String> fut : list) {
			try {
				System.out.println(new Date() + "::" + fut.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		executor.shutdown();
	}
}

class CallableTask implements Callable<String> {

	@Override
	public String call() throws Exception {
		Thread.sleep(1000);
		return Thread.currentThread().getName();
	}
	
}