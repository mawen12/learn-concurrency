package com.mawen.learn.basic.concurrency;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/5/21
 */
public class Deadlock {

	static class Friend {

		private final String name;

		public Friend(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public synchronized void bow(Friend bower) {
			System.out.format("%s: %s has bowed to me!%n", this.name, bower.getName());
			bower.bowBack(this);
		}

		public synchronized void bowBack(Friend bower) {
			System.out.format("%s: %s has bowed back to me!%n", this.name, bower.getName());
		}
	}

	public static void main(String[] args) {
		final Friend alphonse = new Friend("Alphonse");
		final Friend gaston = new Friend("Gaston");
		new Thread(new Runnable() {
			@Override
			public void run() {
				alphonse.bow(gaston);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				gaston.bow(alphonse);
			}
		}).start();
	}
}
