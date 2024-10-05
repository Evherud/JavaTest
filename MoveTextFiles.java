import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class MoveTextFiles {
    public static void main(String[] args) {
        // Get the path to the desktop
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        
        // Define the destination folder
        String destFolderPath = desktopPath + File.separator + "txt files";

        // Create the destination folder if it doesn't exist
        File destFolder = new File(destFolderPath);
        if (!destFolder.exists()) {
            destFolder.mkdir();
            System.out.println("Created 'txt files' folder on the desktop.");
        }

        // Get all files from the desktop
        File desktop = new File(desktopPath);
        File[] files = desktop.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    try {
                        Path source = file.toPath();
                        Path destination = Paths.get(destFolderPath, file.getName());
                        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Moved: " + file.getName());
                    } catch (IOException e) {
                        System.out.println("Error moving file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("Operation completed.");
    }
}