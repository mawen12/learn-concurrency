package com.mawen.learn.basic.concurrency.wait;

/**
 * When call {@link Object#wait(long)}, and no other thread lock the object, after waiting it can continue executed.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.2">Wait Sets and Notification</a>
 * @since 2024/5/22
 */
public class ObjectWaitTimeWhenNoOtherThreadLockThenExecuted {

	public synchronized void callWait() throws InterruptedException {
		this.wait(1000L);
		System.out.println("Continue execute after wait 1s");
	}

	public static void main(String[] args) throws InterruptedException {
		new ObjectWaitTimeWhenNoOtherThreadLockThenExecuted().callWait();
	}
}
