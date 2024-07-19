package com.mawen.learn.advance.concurrency.runtime;

import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
public final class IsolatedManager {

	private final int nLocks = 64;

	private final Lock[] locks = new Lock[nLocks];

	public IsolatedManager() {
		for (int i = 0; i < locks.length; i++) {
			locks[i] = new ReentrantLock();
		}
	}

	private int lockIndexFor(final Object obj) {
		return Math.abs(obj.hashCode()) % nLocks;
	}

	public void acquireAllLocks() {
		for (Lock lock : locks) {
			lock.lock();
		}
	}

	public void releaseAllLocks() {
		for (Lock lock : locks) {
			lock.unlock();
		}
	}

	public void acquireLocksFor(final Object[] objects) {
		final TreeSet<Object> sorted = createSortedObjects(objects);

		for (Object obj : sorted) {
			final int lockIndex = lockIndexFor(obj);
			locks[lockIndex].lock();
		}
	}

	public void releaseLocksFor(final Object[] objects) {
		final TreeSet<Object> sorted = createSortedObjects(objects);

		for (Object obj : sorted) {
			final int lockIndex = lockIndexFor(obj);
			locks[lockIndex].unlock();
		}
	}

	private TreeSet<Object> createSortedObjects(final Object[] objects) {
		TreeSet<Object> sorted = new TreeSet<>(new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				return lockIndexFor(o1) - lockIndexFor(o2);
			}
		});

		Collections.addAll(sorted, objects);

		return sorted;
	}

}
