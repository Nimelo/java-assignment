package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mateusz Gasior on 23-Feb-17.
 */
public class MainPanel {
    private JButton LUPivotButton;
    private JButton inverseButton;
    private JTextArea matrixArea;
    private JTextArea vectorArea;
    private JTextArea outputArea;
    private JButton clearButton;
    private JPanel statusLabel;
    public JPanel mainPanel;
    private JButton saveButton;
    private JButton loadButton;

    public MainPanel() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
            }
        });
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Assignment Advanced Java, Mateusz Gasior, s258770");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        MainPanel something = new MainPanel();
        frame.add(something.mainPanel);

        frame.pack();
        frame.setVisible(true);
        int width = (int)(0.75 * screenSize.getWidth());
        int height = (int)(0.75 * screenSize.getHeight());
        frame.setSize(new Dimension(width, height));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

}
