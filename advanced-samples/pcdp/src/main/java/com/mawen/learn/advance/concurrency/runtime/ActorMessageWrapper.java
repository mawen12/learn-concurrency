package com.mawen.learn.advance.concurrency.runtime;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
public final class ActorMessageWrapper {

	private final Object msg;

	private ActorMessageWrapper next;

	public ActorMessageWrapper(final Object msg) {
		this.msg = msg;
		this.next = null;
	}

	public void setNext(final ActorMessageWrapper next) {
		this.next = next;
	}

	public ActorMessageWrapper getNext() {
		return next;
	}

	public Object getMsg() {
		return msg;
	}
}
