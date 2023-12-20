package SortingAlgorithms;

import Tools.Element;
import Visualizer.SortingVisualizer;

public class QuickSort implements Runnable {
    Element[] array;
    SortingVisualizer svr;
    private int swapTime;

    public QuickSort(Element[] array, SortingVisualizer svr){
        this.array = array;
        this.svr = svr;
    }

    @Override
    public void run() {
        quickSort(array, 0, array.length - 1);
        svr.resetIsSorting();
    }

    private void quickSort(Element[] arr, int low, int high) {
        if (low < high) {
            // Find pivot element such that elements smaller than pivot are on the left, and larger on the right
            int pivotIndex = partition(arr, low, high);

            // Recursively sort sub-arrays
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
        if (low == 0 && high == arr.length - 1) {

            for (Element el : arr) {
                el.setFinalSortedColor();
            }
            svr.updateBoard(arr);
        }
    }

    private int partition(Element[] arr, int low, int high) {
        Element pivot = arr[high];
        pivot.make_open(); // Highlight pivot
        int i = low - 1; // Index of smaller element

        for (int j = low; j < high; j++) {
            arr[j].make_open(); // Highlight element for comparison
            // If current element is smaller than or equal to pivot
            if (arr[j].value <= pivot.value) {
                i++;

                // Swap arr[i] and arr[j]
                Element temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                swapTime++; // Increment swap count

                arr[i].reset_color(); // Reset color after swap

                if (i != j) {
                    arr[j].reset_color(); // Reset color if it's not the same element
                }
                // Add a sleep after each swap
                try {
                    Thread.sleep(50); // Adjust the sleep duration as needed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // Update the visualization after each swap
                svr.updateBoard(arr);
            }
            else{
                arr[j].reset_color(); // Reset color if no swap occurs
            }
        }

        // Swap arr[i+1] and arr[high] (pivot)
        Element temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        swapTime++; // Increment swap count
        pivot.reset_color(); // Reset pivot color
        arr[i + 1].setFinalSortedColor(); // Mark the pivot's final position as sorted

        // Add a sleep after the final swap
        try {
            Thread.sleep(50); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Update the visualization after the final swap
        svr.updateBoard(arr);
        svr.updateSwapTime(swapTime);

        return i + 1;
    }
}
