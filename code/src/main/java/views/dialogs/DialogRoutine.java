package views.dialogs;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Provides features to select the path of the files (save/open).
 * Created by Mateusz Gasior on 26-Feb-17.
 */
public class DialogRoutine {

    /**
     * Extension of the files.
     */
    private final static String EXTENSION = "computation";

    /**
     * Description of the files.
     */
    private final static String COMPUTATIONAL_RESULT_FILE_DESCRIPTION = "Computation result file";

    /**
     * Creates open dialog that allows user to choose files with certain extensions - EXTENSION.
     *
     * @return Path to the file or null when user does not approve the option.
     */
    public static String openDialog() {
        JFileChooser fileChooser = getFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }

        return null;
    }

    /**
     * Creates save dialog that allows user to choose files with certain extensions - EXTENSION.
     *
     * @return Path to the file or null when user does not approve the option.
     */
    public static String saveDialog() {
        JFileChooser fileChooser = getFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath().endsWith("." + EXTENSION) ? selectedFile.getAbsolutePath() : selectedFile.getAbsolutePath() + "." + EXTENSION;
        }

        return null;
    }

    /**
     * Creates dialog in current directory that accepts files with certain extensions - EXTENSION.
     *
     * @return Dialog control.
     */
    private static JFileChooser getFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                String fileName = f.getName();
                int dot = fileName.lastIndexOf(".");
                String extension = fileName.substring(dot + 1);

                if (extension.toLowerCase().equals(EXTENSION)) {
                    return true;
                }

                return false;
            }

            @Override
            public String getDescription() {
                return COMPUTATIONAL_RESULT_FILE_DESCRIPTION;
            }
        });

        return fileChooser;
    }
}
