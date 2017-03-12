package com.my.interview.thread;

public class ThreadLocalExample {
	
	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++) {
			new Thread(new TestTask(i), "t" + i).start();
		}
	}
}

class TestTask implements Runnable {

	private static final ThreadLocal<Integer> testId = ThreadLocal.<Integer>withInitial(() -> {return 0;});
	private static final ThreadLocal<Integer> taskId = new ThreadLocal<Integer>(){
		
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};

	private Integer id;
	
	public TestTask(Integer id) {
		this.id = id;
	}
	
	protected TestTask initValue() {
		return null;
		
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " TestTask default taskID: " + taskId.get() + ", default testId: " + testId.get());
		
		try {
			Thread.sleep(1000);
			taskId.set(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " TestTask new taskID: " + taskId.get() + ", new testId: " + testId.get());
	}
	
	
}