package com.mawen.learn.basic.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Use {@link ForkJoinPool} to process.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html">forkjoin</a>
 * @since 2024/5/22
 */
public class ForkBlur extends RecursiveAction {

	private int[] mSource;
	private int mStart;
	private int mLength;
	private int[] mDestination;

	private int mBlurWidth = 15;

	public ForkBlur(int[] source, int start, int length, int[] destination) {
		mSource = source;
		mStart = start;
		mLength = length;
		mDestination = destination;
	}

	protected void computeDirectly() {
		int sidePixels = (mBlurWidth - 1) / 2;
		for (int i = mStart; i < mStart + mLength; i++) {
			// Calculate average
			float rt = 0, gt = 0, bt = 0;
			for (int j = -sidePixels; j < sidePixels; j++) {
				int index = Math.min(Math.max(j + i, 0), mSource.length - 1);
				int pixel = mSource[index];
				rt += (float) ((pixel & 0x00ff0000) >> 16) / mBlurWidth;
				gt += (float) ((pixel & 0x0000ff00) >> 8) / mBlurWidth;
				bt += (float) ((pixel & 0x000000ff) >> 0) / mBlurWidth;
			}

			// Reassemble destination pixel.
			int dpixel = (0xff000000) |
					(((int) rt) << 16) |
					(((int) gt) << 8) |
					(((int) bt) << 0);
			mDestination[i] = dpixel;
		}
	}

	protected static int sThreshold = 100000;

	@Override
	protected void compute() {
		if (mLength < sThreshold) {
			computeDirectly();
			return;
		}

		int split = mLength / 2;

		invokeAll(new ForkBlur(mSource, mStart, split, mDestination),
				new ForkBlur(mSource, mStart + split, mLength - split, mDestination)
		);
	}

	public static void main(String[] args) {
		int[] source = {1, 2, 3, 4, 5, 6};
		int[] dest = new int[6];
		ForkBlur fb = new ForkBlur(source, 0, source.length, dest);

		ForkJoinPool pool = new ForkJoinPool();

		pool.invoke(fb);
	}
}
