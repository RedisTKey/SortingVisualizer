package Visualizer;

import SortingAlgorithms.*;
import Tools.Element;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class SortingVisualizer {

    private int width;
    private int height;
    private Element[] elements;
    private boolean is_sorting;
    private int length;

    /**================组件==================*/
    private JFrame frame;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> algorithmComboBox;
    private JPanel sortingPanel;
    private JPanel controlPanel;
    private JPanel dataPanel;
    private JSlider sizeSlider;         //控制数组大小的滑动条
    private JLabel label_length;
    private JLabel label_swapTime;
    /**================组件==================*/
    private Thread sortingThread;

    public SortingVisualizer() {
        //Initialize the parameters
        is_sorting = false;
        width = 800;
        height = 600;
        is_sorting = false;
        length = 50;
        elements = new Element[length];

        // Initialize the frame
        frame = new JFrame("Sorting Algorithm Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());

        // Create and add components
        createComponents();
        addComponents();

        // Make the frame visible
        frame.setVisible(true);
    }

    private void createComponents() {
        // Create Panels
        sortingPanel = new JPanel();
        sortingPanel.setBackground(Color.WHITE);
        controlPanel = new JPanel();
        dataPanel = new JPanel();

        // Create the start button
        startButton = new JButton("Start Sorting");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSorting();
            }
        });

        // Create the algorithm selection dropdown
        String[] algorithms = {"Bubble", "Merge", "Quick", "Heap", "Insert"};
        algorithmComboBox = new JComboBox<>(algorithms);

        //Create the reset button
        resetButton = new JButton("Reset Array");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!is_sorting) {
                    generateRandomArray();
                    label_swapTime.setText("Swap Time: ");
                }else {
                    System.out.println("Can not generate when sorting");
                }
            }
        });

        //Create the roller of controlling the number of element
        //from[50->200]
        sizeSlider = new JSlider(JSlider.HORIZONTAL, 50, 200, 50); // Min: 1, Max: 100, Default: 50
        sizeSlider.setMajorTickSpacing(10);
        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.setPaintTicks(false);
        sizeSlider.setPaintLabels(false);
        sizeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = sizeSlider.getValue();
                length = value;
                label_length.setText("Array Size: "+length);
            }
        });

        //Create the Labels
        label_length = new JLabel();
        label_length.setText("Array Size: "+length);
        label_swapTime = new JLabel();
        label_swapTime.setText("Swap Time: ");
    }

    private void addComponents() {
        // Add components to the frame
        frame.add(sortingPanel, BorderLayout.CENTER);

        /**====================Control Panel====================*/
        controlPanel.add(algorithmComboBox);
        controlPanel.add(startButton);
        controlPanel.add(resetButton);
        controlPanel.add(sizeSlider);
        frame.add(controlPanel, BorderLayout.SOUTH);
        /**====================Control Panel====================*/

        /**====================Data Panel====================*/
        dataPanel.add(label_length);
        dataPanel.add(label_swapTime);
        frame.add(dataPanel,BorderLayout.NORTH);
        /**====================Data Panel====================*/
    }

    private void startSorting() {
        if (sortingThread == null || !this.is_sorting) {
            this.is_sorting = true;
            startSortingThread();
        }
    }

    //更新一次画板
    public void updateBoard(Element[] list) {

        sortingPanel.removeAll(); // Clear the sortingPanel before drawing

        sortingPanel.setLayout(null); // Set layout to null for manual positioning

        int numBars = list.length;
        //画板边界（百分比）
        int marginPercentage = 5;
        int gap = 1; // Gap between bars

        int maxBarWidth = (width - 2 * width * marginPercentage / 100) / numBars;
        int barWidth = (width- gap*numBars)/numBars;


        int marginX = (width - (barWidth + gap) * numBars + gap) / 2; // Center the bars horizontally
        int barHeightMultipler = 50/(list.length/10);

        for (int i = 0; i < numBars; i++) {
            Element element = list[i];
            int barHeight = element.value * barHeightMultipler; // Adjust the multiplier for better visualization

            //创建一个新的JPanel来表示对应长度的bar
            JPanel barPanel = new JPanel();
            barPanel.setBounds(
                    marginX + i * (barWidth + gap),   // X position with left margin
                    (height-100-barHeight),
                    barWidth,
                    barHeight
            );
            barPanel.setBackground(element.get_color());

            sortingPanel.add(barPanel);
        }

        frame.revalidate();
        frame.repaint();
    }

    public void updateSwapTime(int count){
        label_swapTime.setText("Swap Time: "+count);
    }

    //生成一个随机的数组，并根据生成的数进行一次偏移
    public void generateRandomArray() {
        Random rand = new Random();
        if (elements.length != length)
        {
            elements = new Element[length];
        }
        for (int i = 0; i < length; i++) {
            this.elements[i] = new Element(i);
        }
        // shuffle
        for (int i = 0; i < this.elements.length; i++) {
            int randomIndexToSwap = rand.nextInt(this.elements.length);
            Element temp = this.elements[randomIndexToSwap];
            this.elements[randomIndexToSwap] = this.elements[i];
            this.elements[i] = temp;
        }

        //更新画板上的数据
        updateBoard(elements);
    }

    public void startSortingThread() {
        //获取选择框里的文本
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();

        //匹配排序类型
        switch (selectedAlgorithm) {
            case "Bubble":
                BubbleSort bubbleSort = new BubbleSort(elements, this);
                sortingThread = new Thread(bubbleSort);
                sortingThread.start();
                break;
            case "Merge":
                MergeSort mergeSort = new MergeSort(elements,this);
                sortingThread = new Thread(mergeSort);
                sortingThread.start();
                break;
            case "Quick":
                QuickSort quickSort = new QuickSort(elements,this);
                sortingThread = new Thread(quickSort);
                sortingThread.start();
                break;
            case "Insert":
                InsertSort insertSort = new InsertSort(elements,this);
                sortingThread = new Thread(insertSort);
                sortingThread.start();
                break;
            case "Heap":
                HeapSort heapSort = new HeapSort(elements,this);
                sortingThread = new Thread(heapSort);
                sortingThread.start();
                break;
        }
    }

    public void resetIsSorting()
    {
        if (is_sorting = true)
            is_sorting = false;
    }

    public Element[] getElements() {
        return elements;
    }

    public static void main(String[] args) {
        // Create an instance of the SortingVisualizerUI
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Visualizer.SortingVisualizer();
//            }
//        });
        SortingVisualizer svr = new SortingVisualizer();
        svr.generateRandomArray();
        svr.updateBoard(svr.elements);
    }
}
