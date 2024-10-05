import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths; // Import Paths
import java.nio.file.StandardCopyOption;

public class StealToResources {

    public static void main(String[] args) {
        // Define the paths for the downloads and resources directories
        String userHome = System.getProperty("user.home");
        Path downloadsDir = Paths.get(userHome, "Downloads"); // Use Paths.get()
        Path resourcesDir = Paths.get("resources"); // Use Paths.get()

        // Ensure the resources directory exists
        File resourcesFolder = resourcesDir.toFile();
        if (!resourcesFolder.exists()) {
            resourcesFolder.mkdirs();
        }

        // Define the file name to search for
        String fileName = "MidDayQueen.jpg";

        // Search for the file in the downloads directory
        File fileToMove = new File(downloadsDir.toFile(), fileName);
        if (fileToMove.exists()) {
            try {
                // Move the file to the resources directory
                Files.move(fileToMove.toPath(), resourcesDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File moved successfully to resources folder.");
            } catch (IOException e) {
                System.err.println("Error moving file: " + e.getMessage());
            }
        } else {
            System.out.println("File not found in downloads folder.");
        }
    }
}
