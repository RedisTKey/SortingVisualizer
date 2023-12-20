package SortingAlgorithms;

import Tools.*;
import Visualizer.SortingVisualizer;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class BubbleSort implements Runnable{
    private Element[] array;
    private SortingVisualizer svr;
    private int swapTime;

    public BubbleSort(Element[] array, SortingVisualizer svr)
    {
        this.array = array;
        this.svr = svr;
    }

    @Override
    public void run() {
        bubbleSort(array);
        svr.resetIsSorting();
    }

    public void bubbleSort(Element[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {

            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                arr[j].make_open(); // Highlight for comparison
                arr[j + 1].make_open(); // Highlight for comparison
                if (arr[j].value > arr[j + 1].value) {
                    // 交换元素
                    swap(arr, j, j + 1);
                    swapped = true;

                    // 更新交换次数并通知UI更新
                    svr.updateSwapTime(swapTime);
                    svr.updateBoard(Arrays.copyOf(arr, arr.length));

                    try {
                        Thread.sleep(20); // Adjust the sleep duration as needed
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                // Reset color if not in final position
                if (j < n - i - 2) {
                    arr[j].reset_color();
                }
                arr[j + 1].reset_color(); // Always reset the second element
            }

            // 如果某一轮没有发生交换，说明数组已经有序，可以提前退出
            if (!swapped) {
                for (int k = 0; k <= n - i - 1; k++) {
                    arr[k].setFinalSortedColor();
                }
                break;
            }
            // Mark the last element of this pass as sorted
            arr[n - i - 1].setFinalSortedColor();
            svr.updateBoard(Arrays.copyOf(arr, arr.length));
        }

        // Optionally, mark all elements as sorted if not already done
        for (Element el : arr) {
            el.setFinalSortedColor();
        }
        svr.updateBoard(Arrays.copyOf(arr, arr.length));
    }

    private void swap(Element[] arr, int i, int j) {
        Element temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        swapTime++;
    }
}
