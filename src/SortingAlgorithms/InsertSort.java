package SortingAlgorithms;

import Tools.Element;
import Visualizer.SortingVisualizer;

import java.util.Arrays;

public class InsertSort implements Runnable{
    Element[] array;
    SortingVisualizer svr;
    private int swapTime;

    public InsertSort(Element[] array, SortingVisualizer svr){
        this.array = array;
        this.svr = svr;
    }

    @Override
    public void run() {
        insertSort(array);
        svr.resetIsSorting();
    }

    private void insertSort(Element[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            Element key = arr[i];
            key.make_open(); // Highlight the key element
            int j = i - 1;

            // Move elements of arr[0..i-1] that are greater than key one position ahead of their current position
            while (j >= 0 && arr[j].value > key.value) {
                swapTime++; // Increment swap count

                // Update the visualization before each swap
                svr.updateBoard(arr);
                svr.updateSwapTime(swapTime);

                arr[j + 1] = arr[j];
                j--;

                // Add a sleep after each swap
                try {
                    Thread.sleep(50); // Adjust the sleep duration as needed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                arr[j + 1].reset_color(); // Reset color after moving
            }
            arr[j + 1] = key;

            // Mark the element as sorted
            key.setFinalSortedColor();

            // Update the visualization after placing the key in its sorted position
            svr.updateBoard(arr);
            svr.updateSwapTime(swapTime);
        }
        // Optionally, mark all elements as sorted if not already done
        for (Element el : arr) {
            el.setFinalSortedColor();
        }
        svr.updateBoard(arr);
    }
}
