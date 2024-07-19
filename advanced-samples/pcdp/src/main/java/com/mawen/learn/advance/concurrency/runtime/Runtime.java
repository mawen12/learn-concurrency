package com.mawen.learn.advance.concurrency.runtime;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.mawen.learn.advance.concurrency.config.Configuration;
import com.mawen.learn.advance.concurrency.config.SystemProperty;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
public final class Runtime {

	private static final ThreadLocal<Deque<BaseTask>> threadLocalTaskStack = new ThreadLocal<Deque<BaseTask>>() {
		@Override
		protected Deque<BaseTask> initialValue() {
			return new ArrayDeque<>();
		}
	};

	private static ForkJoinPool taskPool = new ForkJoinPool(Configuration.readIntProperty(SystemProperty.numWorkers));

	public static void resizeWorkerThreads(final int numWorkers) throws InterruptedException {
		taskPool.shutdown();
		boolean terminated = taskPool.awaitTermination(10, TimeUnit.SECONDS);
		assert terminated;

		SystemProperty.numWorkers.set(numWorkers);
		taskPool = new ForkJoinPool(numWorkers);
	}

	public static BaseTask currentTask() {
		Deque<BaseTask> taskStack = Runtime.threadLocalTaskStack.get();
		if (taskStack.isEmpty()) {
			return null;
		}
		else {
			return taskStack.peek();
		}
	}

	public static void pushTask(final BaseTask task) {
		Runtime.threadLocalTaskStack.get().push(task);
	}

	public static void popTask() {
		Runtime.threadLocalTaskStack.get().pop();
	}

	public static void submitTask(final BaseTask task) {
		taskPool.execute(task);
	}

	public static void showRuntimeStats() {
		StringBuilder builder = new StringBuilder();
		builder.append("Runtime Stats(").append(Configuration.buildInfo).append("): ").append(System.lineSeparator());
		builder.append("   " + taskPool.toString())
				.append("   # finishes = " + BaseTask.FinishTask.TASK_COUNTER.get())
				.append("   # async = " + BaseTask.FutureTask.TASK_COUNTER.get());
		System.out.println(builder.toString());
	}

	private Runtime(){}
}
