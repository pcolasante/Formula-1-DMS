package Core;

import java.io.File;
import java.util.Scanner;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class FileManager: This class will process the storage of the Driver and Race Results information.

 */
public class FileManager {

    public boolean loadFromFile(String fileName, InformationManager manager) {
        try {
            File file = new File(fileName);

            System.out.println("Looking for file at: " + file.getAbsolutePath());

            if (!file.exists()) {
                System.out.println("File doesn't exist");
                return false;
            }

            Scanner scanner = new Scanner(file);
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    String[] parts = line.split("-");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Incorrect format.");
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("Incorrect format.");

                }

            }
            return true;
        } catch (Exception e) {
            System.out.println("Error loading file");
            return false;
        }
    }
}
