package com.mawen.learn.advance.concurrency.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
public abstract class BaseTask extends CountedCompleter<Void> {

	public BaseTask() {
		super();
	}

	public abstract FinishTask ief();

	public static final class FinishTask extends BaseTask {

		static final AtomicLong TASK_COUNTER = new AtomicLong();

		private final Runnable runnable;

		private List<Throwable> exceptionList;

		public FinishTask(final Runnable runnable) {
			super();
			this.runnable = runnable;
			TASK_COUNTER.incrementAndGet();
		}

		@Override
		public FinishTask ief() {
			return this;
		}

		@Override
		public void compute() {
			Runtime.pushTask(this);
			try {
				runnable.run();
			}
			catch (final Throwable th) {
				pushException(th);
			}
			finally {
				tryComplete();
				Runtime.popTask();
				awaitCompletion();
			}
		}

		public void awaitCompletion() {
			try {
				join();
			}
			catch (final Exception ex) {
				pushException(ex);
			}
		}

		public void pushException(final Throwable throwable) {
			synchronized (this) {
				this.exceptions().add(throwable);
			}
		}

		private List<Throwable> exceptions() {
			if (exceptionList == null) {
				exceptionList = new ArrayList<>();
			}
			return exceptionList;
		}
	}

	public static final class FutureTask<R> extends BaseTask {

		static final AtomicLong TASK_COUNTER = new AtomicLong();

		private final Runnable runnable;

		private final FinishTask immediatelyEnclosingFinish;

		private final AtomicBoolean cancellationFlag = new AtomicBoolean(false);

		private final CompletableFuture<R> completableFuture = new CompletableFuture<R>() {
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return cancellationFlag.compareAndSet(false, true)
						&& super.cancel(mayInterruptIfRunning);
			}
		};

		public FutureTask(final Callable<R> setRunnable, final FinishTask setImmediatelyEnclosingFinish, final boolean rethrowException) {
			super();
			if (setImmediatelyEnclosingFinish == null) {
				throw new IllegalStateException("Async is not executing inside a finish!");
			}
			this.runnable = () -> {
				try {
					final R result = setRunnable.call();
					completableFuture.complete(result);
				}
				catch (Exception e) {
					completableFuture.completeExceptionally(e);
					if (rethrowException) {
						if (e instanceof RuntimeException) {
							throw (RuntimeException) e;
						}
						else {
							throw new RuntimeException("Error in executing callable", e);
						}
					}
				}
			};

			this.immediatelyEnclosingFinish = setImmediatelyEnclosingFinish;
			this.immediatelyEnclosingFinish.addToPendingCount(1);
			TASK_COUNTER.incrementAndGet();
		}

		@Override
		public void compute() {
			Runtime.pushTask(this);

			try {
				if (!cancellationFlag.get()) {
					runnable.run();
				}
			}
			catch (Throwable th) {
				immediatelyEnclosingFinish.pushException(th);
			}
			finally {
				immediatelyEnclosingFinish.tryComplete();
				Runtime.popTask();
			}
		}

		@Override
		public FinishTask ief() {
			return immediatelyEnclosingFinish;
		}

		public CompletableFuture<R> future() {
			return completableFuture;
		}
	}
}
