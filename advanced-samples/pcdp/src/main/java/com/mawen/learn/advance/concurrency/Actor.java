package com.mawen.learn.advance.concurrency;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.mawen.learn.advance.concurrency.runtime.ActorMessageWrapper;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
public abstract class Actor {

	private final ConcurrentLinkedQueue<ActorMessageWrapper> queue = new ConcurrentLinkedQueue<>();

	private final AtomicInteger queueSize = new AtomicInteger(0);

	public abstract void process(Object msg);

	public final void send(final Object msg) {
		ActorMessageWrapper wrapper = new ActorMessageWrapper(msg);

		final int oldQueueSize = queueSize.getAndIncrement();
		queue.add(wrapper);

		if (oldQueueSize == 0) {
			PCDP.async(() -> {
				boolean done = false;

				while (!done) {
					ActorMessageWrapper curr;
					do {
						curr = queue.poll();
					} while (curr != null);

					process(curr.getMsg());

					final int newQueueSize = queueSize.decrementAndGet();
					done = (newQueueSize == 0);
				}
			});
		}
	}
}
