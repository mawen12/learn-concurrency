package com.mawen.learn.advance.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.mawen.learn.advance.concurrency.config.SystemProperty;
import com.mawen.learn.advance.concurrency.runtime.BaseTask;
import com.mawen.learn.advance.concurrency.runtime.IsolatedManager;
import com.mawen.learn.advance.concurrency.runtime.Runtime;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
public class PCDP {

	protected static final String missingFinishMsg = "A new task cannot be created at the top-level of your program." +
			"It must be created from within a finish or another async." +
			"Please ensure that all asyncs, actor sends, parallel loops, and other type of parallelism-creating constructs " +
			"are not called at the top-level of your program.";

	private static final IsolatedManager isolatedManager = new IsolatedManager();

	public static void finish(final Runnable runnable) {
		final BaseTask currentTask = Runtime.currentTask();
		final BaseTask.FinishTask newTask = new BaseTask.FinishTask(runnable);

		if (currentTask == null) {
			Runtime.submitTask(newTask);
		}
		else {
			newTask.compute();
		}
		newTask.awaitCompletion();
	}

	public static void async(final Runnable runnable) {
		final BaseTask.FutureTask<Void> newTask = createFutureTask(runnable);
		newTask.fork();
	}

	public static void forasync(final int startInc, final int endInc, final ProcedureInt1D body) {
		assert startInc <= endInc;

		for (int i = startInc; i <= endInc; i++) {
			final int loopIndex = i;
			final Runnable loopRunnable = () -> body.apply(loopIndex);
			async(loopRunnable);
		}
	}

	public static void forasync2d(final int startInc0, final int endInc0, final int startInc1, final int endInc1, final ProcedureInt2D body) {
		assert startInc0 <= endInc0;
		assert startInc1 <= endInc1;

		for (int i = startInc0; i < endInc0; i++) {
			final int iCopy = i;

			for (int j = startInc1; j < endInc1; j++) {
				final int jCopy = j;

				final Runnable runnable = () -> body.apply(iCopy, jCopy);
				async(runnable);
			}
		}
	}

	public static void forall(final int start, final int end, final ProcedureInt1D body) {
		finish(() -> forasync(start, end, body));
	}

	public static void forall2d(final int start0, final int end0, final int start1, final int end1, final ProcedureInt2D body) {
		finish(() -> forasync2d(start0, end0, start1, end1, body));
	}

	public static void forasyncChunked(final int start, final int endInclusive, final int chunkSize, final ProcedureInt1D body) {
		assert (start <= endInclusive);

		for (int i = start; i <= endInclusive; i += chunkSize) {
			final int iCopy = i;

			async(() -> {
				int end = iCopy + chunkSize - 1;
				if (end > endInclusive) {
					end = endInclusive;
				}
				for (int innerI = iCopy; innerI <= end; innerI++) {
					body.apply(innerI);
				}
			});
		}
	}

	public static void forasyncChunked(final int start, final int endInclusive, final ProcedureInt1D body) {
		forasyncChunked(start, endInclusive, getChunkSize(endInclusive - start + 1, numThreads() * 2), body);
	}

	public static void forasync2dChunked(final int start0, final int endInclusive0, final int start1, final int endInclusive1, final int chunkSize, final ProcedureInt2D body) {
		assert start0 <= endInclusive0;
		assert start1 <= endInclusive1;

		final int outerNIters = endInclusive0 - start0 + 1;
		final int innerNIters = endInclusive1 - start1 + 1;
		final int numIters = outerNIters * innerNIters;

		forasyncChunked(0, numIters - 1, chunkSize, i -> {
			int outer = i / innerNIters;
			int inner = i % innerNIters;

			body.apply(start0 + outer, start1 + inner);
		});
	}

	public static void forasync2dChunked(final int start0, final int endInclusive0, final int start1, final int endInclusive1, final ProcedureInt2D body) {
		final int numIters = endInclusive0 - start0 + 1;

		forasync2dChunked(start0, endInclusive0, start1, endInclusive1, getChunkSize(numIters, numThreads() * 2), body);
	}

	public static void forallChunked(final int start, final int endInclusive, final int chunkSize, final ProcedureInt1D body) {
		finish(() -> {
			forasyncChunked(start, endInclusive, chunkSize, body);
		});
	}

	public static void forallChunked(final int start, final int endInclusive, final ProcedureInt1D body) {
		forallChunked(start, endInclusive, getChunkSize(endInclusive - start + 1, numThreads() * 2), body);
	}

	public static void forall2dChunked(final int start0, final int endInclusive0, final int start1, final int endInclusive1, final int chunkSize, final ProcedureInt2D body) {
		final int numIters = endInclusive0 - start0 + 1;

		forall2dChunked(start0, endInclusive0, start1, endInclusive1, getChunkSize(numIters, numThreads() * 2), body);
	}

	public static <R> Future<R> future(final Callable<R> body) {
		final BaseTask.FutureTask<R> newTask = createFutureTask(body, false);
		newTask.fork();
		return newTask.future();
	}

	public static void asyncAwait(final Runnable runnable, final Future<? extends Object>... futures) {
		final BaseTask.FutureTask<Void> newTask = createFutureTask(runnable);
		CompletableFuture.allOf(wrapToCompletableFuture(futures)).whenComplete((v, t) -> newTask.fork());
	}

	public static <R> Future<R> futureAwait(final Callable<R> runnable, final Future<? extends Object>... futures) {
		final BaseTask.FutureTask<R> newTask = createFutureTask(runnable, false);
		CompletableFuture.allOf(wrapToCompletableFuture(futures)).whenComplete((v, t) -> newTask.fork());
		return newTask.future();
	}

	private static BaseTask.FutureTask<Void> createFutureTask(final Runnable runnable) {
		final BaseTask currentTask = Runtime.currentTask();
		if (currentTask == null) {
			throw new IllegalStateException(missingFinishMsg);
		}

		return createFutureTask(() -> {
			runnable.run();
			return null;
		}, true);
	}

	private static <R> BaseTask.FutureTask<R> createFutureTask(final Callable<R> body, final boolean rethrowException) {
		final BaseTask currentTask = Runtime.currentTask();
		if (currentTask == null) {
			throw new IllegalStateException(missingFinishMsg);
		}
		return new BaseTask.FutureTask<>(body, currentTask.ief(), rethrowException);
	}


	private static CompletableFuture<?>[] wrapToCompletableFuture(final Future<? extends Object>... futures) {
		final CompletableFuture<?>[] result = new CompletableFuture<?>[futures.length];

		for (int i = 0; i < futures.length; i++) {
			final Future<?> future = futures[i];
			if (future instanceof CompletableFuture) {
				result[i] = (CompletableFuture<?>) future;
			}
			else {
				throw new IllegalArgumentException("Future at index " + i + " is not an instance of CompletableFuture!");
			}
		}

		return result;
	}

	public static void forseg(final int start, final int endInclusive, final ProcedureInt1D body) {
		assert start <= endInclusive;

		for (int i = start; i < endInclusive; i++) {
			body.apply(i);
		}
	}

	public static void forseg2d(final int start0, final int endInclusive0, final int start1, final int endInclusive1, final ProcedureInt2D body) {
		assert start0 <= endInclusive0;
		assert start1 <= endInclusive1;

		for (int i = start0; i < endInclusive0; i++) {
			for (int j = start1; j < endInclusive1; j++) {
				body.apply(i, j);
			}
		}
	}

	public static int numThreads() {
		return Integer.parseInt(SystemProperty.numWorkers.getPropertyValue());
	}

	private static int getChunkSize(final int nElements, final int nChunks) {
		return (nElements + nChunks - 1) / nChunks;
	}

	private PCDP() {
	}
}
