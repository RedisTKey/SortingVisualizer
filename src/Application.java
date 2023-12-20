import Visualizer.SortingVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application extends JFrame {
    private SortingVisualizer sortingVisualizer;

    public Application() {
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Sorting Algorithm Visualizer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Text area for additional information
        JTextArea infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setText("Welcome to the Sorting Algorithm Visualizer!\n\n" +
                "This our CPS 2232 final project application allows you to visualize various sorting algorithms in action.\n" +
                "Click 'Enter' to start the visualizer or 'Exit' to close the application.\n\n" +
                "This work is done by:\n"+
                "Chen Jinjian \n"+
                "Deng Zihui \n"+
                "Zhang Hanfei\n"+
                "Zhou Chufan \n"+
                "Zhu Lianjie");

        // Button panel
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSortingVisualizer();  // Start the SortingVisualizer
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(enterButton);
        buttonPanel.add(exitButton);

        // Adding components to the frame
        add(titleLabel, BorderLayout.NORTH);
        add(infoTextArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 400);
        setLocationRelativeTo(null);  // Center the splash screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startSortingVisualizer() {
        sortingVisualizer = new SortingVisualizer();
        sortingVisualizer.generateRandomArray();
        sortingVisualizer.updateBoard(sortingVisualizer.getElements());

        // Hide the Application
        setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Application();
            }
        });
    }
}
