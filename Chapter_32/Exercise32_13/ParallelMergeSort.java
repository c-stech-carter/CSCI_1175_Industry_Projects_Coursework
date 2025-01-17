/*
Author: Charles Carter
Date: 1/16/2025

Description:  Exercise 32-13 From intro to Java
This is a revised version of Listing 32.10 to define a generic parallelMergeSort method as follows:
public static <E extends Comparable<E>> void parallelMergeSort(E[] list)

The program compares the performance of parallel and sequential merge sort methods.

Some of the changes also include modifying the SortTask class.
SortTask now uses generics (<E extends Comparable<E>>) to handle arrays of any type.
Although modified from the source, the logic for splitting the array,
recursively sorting halves, and merging them remains unchanged.

 */

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort {
	public static void main(String[] args) {
		final int SIZE = 7000000;
		Integer[] list1 = new Integer[SIZE];
		Integer[] list2 = new Integer[SIZE];

		for (int i = 0; i < list1.length; i++)
			list1[i] = list2[i] = (int)(Math.random() * 10000000);

		long startTime = System.currentTimeMillis();
		parallelMergeSort(list1); // Use the generic method
		long endTime = System.currentTimeMillis();
		System.out.println("\nParallel time with "
			+ Runtime.getRuntime().availableProcessors() + 
			" processors is " + (endTime - startTime) + " milliseconds");

		startTime = System.currentTimeMillis();
		MergeSort.mergeSort(list2); // MergeSort is in Listing 24.5
		endTime = System.currentTimeMillis();
		System.out.println("\nSequential time is " + 
			(endTime - startTime) + " milliseconds");
	}

	public static <E extends Comparable<E>> void parallelMergeSort(E[] list) {
		RecursiveAction mainTask = new SortTask<>(list);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
	}

	private static class SortTask <E extends Comparable<E>> extends RecursiveAction {
		private static final int THRESHOLD = 500;
		private E[] list;

		SortTask(E[] list) {
			this.list = list;
		}

		@Override
		protected void compute() {
			if (list.length < THRESHOLD)
				java.util.Arrays.sort(list);
			else {
				//Split the array into two halves, (adapted to support generics)
				int mid = list.length / 2;
				E[] firstHalf = java.util.Arrays.copyOfRange(list, 0, mid);
				E[] secondHalf = java.util.Arrays.copyOfRange(list, mid, list.length);

				//Sort each half in parallel
				invokeAll(new SortTask<>(firstHalf), new SortTask<>(secondHalf));

				// Merge the sorted halves
				MergeSort.merge(firstHalf, secondHalf, list);
			}
		}
	}
}
