package views;

import controllers.MainController;
import controllers.exceptions.InversionConstraintsException;
import controllers.exceptions.NotEqualAmountOfColumnsInMatrixException;
import controllers.results.InverseResult;
import controllers.results.LUPivotResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mateusz Gasior on 23-Feb-17.
 */
public class MainPanel {
    /**
     * LUPivot button.
     */
    private JButton LUPivotButton;

    /**
     * Inverse button.
     */
    private JButton inverseButton;

    /**
     * Matrix text area.
     */
    private JTextArea matrixArea;

    /**
     * Vector text area.
     */
    private JTextArea vectorArea;

    /**
     * Output text area.
     */
    private JTextArea outputArea;

    /**
     * Clear output text area button.
     */
    private JButton clearButton;

    /**
     * Status label.
     */
    private JLabel statusLabel;

    /**
     * Main panel.
     */
    public JPanel mainPanel;

    /**
     * Save button.
     */
    private JButton saveButton;

    /**
     * Load button.
     */
    private JButton loadButton;

    /**
     * Main controller.
     */
    private MainController controller;

    /**
     * Constructor.
     * Wires up all necessary action listeners.
     */
    public MainPanel() {
        controller = new MainController();

        inverseButton.addActionListener(e -> {
            try {
                controller.extractAndSetMatrix(matrixArea.getText());
                InverseResult inverse = controller.inverse();
                this.outputArea.setText(inverse.toString());
            } catch (NotEqualAmountOfColumnsInMatrixException e1) {
                this.statusLabel.setText("NotEqualAmountOfColumnsInMatrixException");
            } catch (InversionConstraintsException e1) {
                this.statusLabel.setText("InversionConstraintsException");
            }
        });

        LUPivotButton.addActionListener(e -> {
            try {
                controller.extractAndSetMatrix(matrixArea.getText());
                controller.extractAndSetVector(vectorArea.getText());
                LUPivotResult luPivotResult = controller.LUPivot();
                this.outputArea.setText(luPivotResult.toString());
            } catch (NotEqualAmountOfColumnsInMatrixException e1) {
                this.statusLabel.setText("NotEqualAmountOfColumnsInMatrixException");
            } catch (Throwable throwable) {
                this.statusLabel.setText(throwable.getClass().getName());
            }
        });

        clearButton.addActionListener(e -> outputArea.setText(""));
    }

    /**
     * Creates GUI and displays it to screen.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Assignment Advanced Java, Mateusz Gasior, s258770");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        MainPanel something = new MainPanel();
        frame.add(something.mainPanel);

        frame.pack();
        frame.setVisible(true);
        int width = (int) (0.7 * screenSize.getWidth());
        int height = (int) (0.7 * screenSize.getHeight());
        frame.setSize(new Dimension(width, height));
    }

    /**
     * Starts the application
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

}
