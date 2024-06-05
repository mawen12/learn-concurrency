package com.mawen.learn.basic.concurrency.wait;

/**
 * When call {@link Object#wait(long)}, but first parameter is less than 0, then will throw {@link IllegalArgumentException}.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.2">Wait Sets and Notification</a>
 * @since 2024/5/22
 */
public class ObjectWaitWhenTimeIsNegativeThenThrowIllegalArgumentException {

	public void callWait(long time) throws InterruptedException {
		this.wait(time);
	}

	public static void main(String[] args) {
		try {
			new ObjectWaitWhenTimeIsNegativeThenThrowIllegalArgumentException().callWait(-1L);
		}
		catch (Exception e) {
			System.err.println("Call wait with -1L " + e);
		}
	}
}
