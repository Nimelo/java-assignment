package views;

import controllers.MainController;
import controllers.exceptions.*;
import models.exceptions.*;
import models.internals.results.InverseResult;
import models.internals.results.LUPivotResult;
import models.internals.ApplicationModel;
import models.internals.results.Result;
import views.dialogs.DialogRoutine;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Main Panel of application.
 * Created by Mateusz Gasior on 23-Feb-17.
 *
 * @author Mateusz Gasior
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
     * Controller of the view.
     */
    private MainController controller;

    /**
     * Constructor.
     * Wires up all necessary action listeners.
     */
    public MainPanel() {
        controller = new MainController(new ApplicationModel());

        inverseButton.addActionListener(e -> {
            try {
                this.statusLabel.setText("Everything is alright!");
                controller.extractAndSetMatrix(matrixArea.getText());
                InverseResult inverse = controller.inverse();
                this.outputArea.setText(String.format("%s%s\n", this.outputArea.getText(), inverse.toString()));
            } catch (InversionConstraintsException e1) {
                this.statusLabel.setText("Error: Input matrix is not correct!");
            } catch (NotEqualAmountOfColumnsInMatrixException e1) {
                this.statusLabel.setText("Error: Input matrix is not correct. Check number of columns.");
            } catch (MatrixExtractionException e1) {
                this.statusLabel.setText("Error: Matrix values are incorrect.");
            } catch (Throwable t) {
                this.statusLabel.setText("Error: unhandled throwable!");
            }
        });

        LUPivotButton.addActionListener(e -> {
            try {
                this.statusLabel.setText("Everything is alright!");
                controller.extractAndSetMatrix(matrixArea.getText());
                controller.extractAndSetVector(vectorArea.getText());
                LUPivotResult luPivotResult = controller.LUPivot();
                this.outputArea.setText(String.format("%s%s\n", this.outputArea.getText(), luPivotResult.toString()));
            } catch (NonSquareMatrixException e1) {
                this.statusLabel.setText("Error: Matrix is not square.");
            } catch (NotEqualAmountOfColumnsInMatrixException e1) {
                this.statusLabel.setText("Error: Input matrix is not correct. Check number of columns.");
            } catch (LUPivotConstraintsException e1) {
                this.statusLabel.setText("Error: " + e1.getMessage());
            } catch (SingularMatrixException e1) {
                this.statusLabel.setText("Error: Input matrix is singular.");
            } catch (InvalidMatrixSizesException e1) {
                this.statusLabel.setText("Error: Error during multiplication of matrices.");
            } catch (VectorExtractionException e1) {
                this.statusLabel.setText("Error: Vector values are incorrect.");
            } catch (MatrixExtractionException e1) {
                this.statusLabel.setText("Error: Matrix values are incorrect.");
            } catch (Throwable t) {
                this.statusLabel.setText("Error: unhandled throwable!");
            }
        });

        loadButton.addActionListener(e -> {
            String s = DialogRoutine.openDialog();
            if (s != null) {
                try {
                    Result deserialize = controller.deserialize(s);
                    this.outputArea.setText(deserialize.toString());
                    this.matrixArea.setText(deserialize.getOriginalMatrix().toString());
                    if (deserialize instanceof LUPivotResult) {
                        this.vectorArea.setText(((LUPivotResult) deserialize).getOriginalVector().toString());
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(mainPanel, "File not found", "Error with file", JOptionPane.OK_OPTION);
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(mainPanel, "Invalid version of file.", "Error with file", JOptionPane.OK_OPTION);
                }
            }
        });

        saveButton.addActionListener(e -> {
            String s = DialogRoutine.saveDialog();
            if (s != null) {
                try {
                    controller.serialize(s);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(mainPanel, "Could not save file.", "Error with file", JOptionPane.OK_OPTION);
                }
            }
        });

        clearButton.addActionListener(e -> outputArea.setText(""));
    }

    /**
     * Creates GUI and displays it to screen.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Assignment Advanced Java, Mateusz Gasior, s258770");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel.mainPanel);

        frame.pack();
        frame.setVisible(true);
        int width = (int) (0.7 * screenSize.getWidth());
        int height = (int) (0.7 * screenSize.getHeight());
        frame.setSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(400, 400));
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
