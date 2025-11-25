import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import javax.swing.JFileChooser;

public class FileInspector {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src"));

        try {
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                Path filePath = selectedFile.toPath();

                String fileName = selectedFile.getName();
                long lineCount = 0;
                long wordCount = 0;
                long charCount = 0;

                try (Stream<String> lineStream = Files.lines(filePath)) {
                    for (String line : (Iterable<String>) lineStream::iterator) {
                        lineCount++;
                        charCount += line.length();

                        String[] words = line.split("\\s+");

                        if (!(words.length == 1 && words[0].isEmpty())) {
                            wordCount += words.length;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading the file: " + e.getMessage());
                    e.printStackTrace();
                    return;
                }

                System.out.println("File Summary Report:");
                System.out.println("File Name: " + fileName);
                System.out.println("Line Count: " + lineCount);
                System.out.println("Word Count: " + wordCount);
                System.out.println("Character Count: " + charCount);
            }
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during file selection.");
            e.printStackTrace();
        }
    }
}