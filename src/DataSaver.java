import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean done = false;
        int idCounter = 1;

        while (!done) {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter First Name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter Last Name");

            String idNum = String.format("%06d", idCounter++);

            String email = SafeInput.getNonZeroLenString(in, "Enter Email");
            int yob = SafeInput.getRangedInt(in, "Enter Year of Birth", 1000, 9999);

            String csvRecord = firstName + "," + lastName + "," + idNum + "," + email + "," + yob;
            records.add(csvRecord);

            done = SafeInput.getYNConfirm(in, "Are you done entering records?");
        }

        String fileName = SafeInput.getNonZeroLenString(in, "Enter the file name to save (e.g., Data.csv)");
        if (!fileName.toLowerCase().endsWith(".csv")) {
            fileName += ".csv";
        }

        try {
            File currentDir = new File(System.getProperty("user.dir"));
            Path srcPath = new File(currentDir.getParentFile(), "src").toPath();
            Path file = srcPath.resolve(fileName);

            Files.write(file, records, CREATE);
            System.out.println("Data successfully written to " + file.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}