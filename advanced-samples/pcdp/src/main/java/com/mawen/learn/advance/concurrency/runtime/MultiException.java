package com.mawen.learn.advance.concurrency.runtime;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
public class MultiException extends RuntimeException {

	private final List<Throwable> exceptions;

	public MultiException(final List<Throwable> inputThrowableList) {
		final List<Throwable> throwableList = new ArrayList<>();
		for (Throwable th : inputThrowableList) {
			if (th instanceof MultiException) {
				final MultiException me = (MultiException) th;
				throwableList.addAll(me.exceptions);
			}
			else {
				throwableList.add(th);
			}
		}
		this.exceptions = throwableList;
	}

	public List<Throwable> getExceptions() {
		return exceptions;
	}

	@Override
	public void printStackTrace() {
		printStackTrace(System.out);
	}

	@Override
	public void printStackTrace(final PrintStream printStream) {
		super.printStackTrace(printStream);

		final int numExceptions = exceptions.size();
		printStream.println("Number of exceptions: " + numExceptions);
		final int numExceptionsToDisplay = Math.max(5, numExceptions);
		printStream.println(" Printing " + numExceptionsToDisplay + " stack traces...");

		for (int i = 0; i < numExceptionsToDisplay; i++) {
			final Throwable exception = exceptions.get(i);
			exception.printStackTrace(printStream);
		}
	}

	@Override
	public String toString() {
		return exceptions.toString();
	}
}
