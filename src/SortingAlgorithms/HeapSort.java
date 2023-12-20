package SortingAlgorithms;

import Tools.Element;
import Visualizer.SortingVisualizer;

public class HeapSort implements Runnable{
    Element[] array;
    SortingVisualizer svr;
    private int swapTime;

    public HeapSort(Element[] array, SortingVisualizer svr) {
        this.array = array;
        this.svr = svr;
    }

    @Override
    public void run() {
        heapSort(array);
        svr.resetIsSorting();
    }

    public void heapSort(Element[] arr) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // One by one extract an element from the heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            swap(arr, 0, i);

            // Set final sorted color for the element now at the end
            arr[i].setFinalSortedColor();

            // Call max heapify on the reduced heap
            heapify(arr, i, 0);


            try {
                Thread.sleep(50); // Adjust the sleep duration as needed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // 更新交换次数并通知UI更新

            svr.updateSwapTime(swapTime);
            svr.updateBoard(arr);
        }

        // Optionally, mark the last element as sorted if not already done
        arr[0].setFinalSortedColor();
        svr.updateBoard(arr);
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private void heapify(Element[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (left < n && arr[left].value> arr[largest].value) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && arr[right].value > arr[largest].value) {
            largest = right;
        }

        if (left < n) {
            arr[left].make_open(); // Highlight for comparison
        }
        if (right < n) {
            arr[right].make_open(); // Highlight for comparison
        }

        // If largest is not root
        if (largest != i) {
            swap(arr, i, largest);

            if (left < n) {
                arr[left].reset_color(); // Reset color if not final position
            }
            if (right < n) {
                arr[right].reset_color(); // Reset color if not final position
            }

            heapify(arr, n, largest);
        } else {
            if (left < n) {
                arr[left].reset_color(); // Reset color
            }
            if (right < n) {
                arr[right].reset_color(); // Reset color
            }
        }
    }

    private void swap(Element[] arr, int i, int j) {
        Element temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        swapTime++;
    }
}
