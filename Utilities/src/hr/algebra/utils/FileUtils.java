package hr.algebra.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author dnlbe
 */
public class FileUtils {

    private static final String UPLOAD = "Upload";
    private static final String SAVE = "Save";
    private static final String TXT = "txt";
    private static final String XML = "xml";
    private static final String TEXT_DOCUMENT = "Text documents(*.txt)";
    private static final String XML_DOCUMENT = "XML documents(*.xml)";

    public static Optional<File> uploadFile(String description, String... extensions) {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        chooser.setDialogTitle(UPLOAD);
        chooser.setApproveButtonText(UPLOAD);
        chooser.setApproveButtonToolTipText(UPLOAD);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            return Arrays.asList(extensions).contains(extension.toLowerCase()) ? Optional.of(selectedFile) : Optional.empty();
        }
        return Optional.empty();
    }

    public static void copyFromUrl(String source, String destination) throws MalformedURLException, IOException {
        String dir = destination.substring(0, destination.lastIndexOf(File.separator));
        if (!Files.exists(Paths.get(dir))) {
            Files.createDirectories(Paths.get(dir));
        }

        URL url = new URL(source);
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(destination));
        }
    }

    public static void copy(String source, String destination) throws MalformedURLException, IOException {
        String dir = destination.substring(0, destination.lastIndexOf(File.separator));
        if (!Files.exists(Paths.get(dir))) {
            Files.createDirectories(Paths.get(dir));
        }
        Files.copy(Paths.get(source), Paths.get(destination));
    }

    public static boolean filenameHasExtension(String filename, int length) {
        return filename.contains(".") && filename.substring(filename.lastIndexOf(".") + 1).length() == length;
    }

    public static Optional<File> saveTextInFile(String text, Optional<File> optFile) throws IOException {
        if (!optFile.isPresent()) {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setFileFilter(new FileNameExtensionFilter(TEXT_DOCUMENT, TXT));
            chooser.setDialogTitle(SAVE);
            chooser.setApproveButtonText(SAVE);
            chooser.setApproveButtonToolTipText(SAVE);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (!file.toString().endsWith(TXT)) {
                    file = new File(file.toString().concat(".").concat(TXT));
                }
                Files.write(file.toPath(), text.getBytes());
                optFile = Optional.of(file);
            }
        } else {
            Files.write(optFile.get().toPath(), text.getBytes());
        }
        return optFile;
    }

    public static Optional<File> getXMLFile(Optional<File> optionalFile) {
        if (!optionalFile.isPresent()) {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setFileFilter(new FileNameExtensionFilter(XML_DOCUMENT, XML));
            chooser.setDialogTitle(SAVE);
            chooser.setApproveButtonText(SAVE);
            chooser.setApproveButtonToolTipText(SAVE);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (!file.toString().endsWith(XML)) {
                    file = new File(file.toString().concat(".").concat(XML));
                }
                optionalFile = Optional.of(file);
            }
        }
        return optionalFile;
    }

    public static Optional<String> loadTextFromFile() throws IOException {

        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.toString().endsWith(TXT);
            }

            @Override
            public String getDescription() {
                return TEXT_DOCUMENT;
            }
        });
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return Optional.of(new String(Files.readAllBytes(chooser.getSelectedFile().toPath())));
        }
        return Optional.empty();
    }
}
