package SortingAlgorithms;

import Tools.Element;
import Visualizer.SortingVisualizer;

public class MergeSort implements Runnable{
    Element[] array;
    SortingVisualizer svr;
    private int swapTime;

    public MergeSort(Element[] array, SortingVisualizer svr){
        this.array = array;
        this.svr = svr;
    }


    @Override
    public void run() {
        mergeSort(array, 0, array.length - 1);
        svr.resetIsSorting();
    }

    private void mergeSort(Element[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            try {
                Thread.sleep(20); // Adjust the sleep duration as needed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            mergeSort(arr, mid + 1, right);
            try {
                Thread.sleep(20); // Adjust the sleep duration as needed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            merge(arr, left, mid, right);
            try {
                Thread.sleep(20); // Adjust the sleep duration as needed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // After the entire array is sorted, mark all elements as sorted
        if (left == 0 && right == arr.length - 1) {
            for (Element el : arr) {
                el.setFinalSortedColor();
            }
            svr.updateBoard(arr); // Update the visualization at the end
        }
    }

    private void merge(Element[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        Element[] leftArray = new Element[n1];
        Element[] rightArray = new Element[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; ++i) {
            leftArray[i] = arr[left + i];
            leftArray[i].make_open(); // Highlight elements for merging
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = arr[mid + 1 + j];
            rightArray[j].make_open(); // Highlight elements for merging
        }

        // Merge the temporary arrays
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i].value <= rightArray[j].value) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
                swapTime++;
            }
            arr[k].setFinalSortedColor(); // Mark element as sorted
            k++;
        }

        // Copy remaining elements of leftArray[] if any
        while (i < n1) {
            arr[k] = leftArray[i];
            arr[k].reset_color(); // Reset color
            i++;
            k++;
        }

        // Copy remaining elements of rightArray[] if any
        while (j < n2) {
            arr[k] = rightArray[j];
            arr[k].reset_color(); // Reset color
            j++;
            k++;
        }

        // Update the visualization after merging
        svr.updateBoard(arr);
        svr.updateSwapTime(swapTime);
    }
}
