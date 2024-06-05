package com.mawen.learn.basic.concurrency.wait;

/**
 * When call {@link Class#wait()}, but it has not locking the monitor, then will throw {@link IllegalMonitorStateException}.
 * Because {@link Class#wait()} must first lock the object monitor.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.2">Wait Sets and Notification</a>
 * @since 2024/5/22
 */
public class ObjectStaticWaitThenThrowIllegalMonitorStateException {

	public static void callWait() throws InterruptedException {
		ObjectStaticWaitThenThrowIllegalMonitorStateException.class.wait();
	}

	public static void main(String[] args) throws InterruptedException {
		ObjectStaticWaitThenThrowIllegalMonitorStateException.callWait();
	}
}
