package com.mawen.learn.basic.concurrency.notification;

/**
 * When thread not lock the monitor, it cannot call {@link Object#notify()}, otherwise throw {@link IllegalMonitorStateException}.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.2">Wait Sets and Notification</a>
 * @since 2024/5/22
 */
public class ObjectNotifyWhenNoLockThenThrowIllegalMonitorStateException {

	public void callNotify() {
		System.out.println("Execution callNotify....");
		this.notify();
		System.out.println("Execution callNotify end....");
	}

	public static void main(String[] args) {
		new ObjectNotifyWhenNoLockThenThrowIllegalMonitorStateException()
				.callNotify();
	}
}
