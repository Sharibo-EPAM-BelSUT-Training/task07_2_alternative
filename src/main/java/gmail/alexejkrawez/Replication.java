package gmail.alexejkrawez;

import java.io.File;

public class Replication {

    private String path;
    private String pathDirOut;
    private String name;

    Replication(File path) {
        printFilesTree(path);
    }

    public void printFilesTree(File directory) {
        if (directory.exists()) {
            path = directory.getAbsolutePath();
            name = directory.getName();

            if (name.equals("ADC")) { // первая папка
                File directoryOut = new File(pathDirOut = path.replace("\\in", "\\out")); // создаёт папку ADC
                directoryOut.mkdirs();

                File[] subDirectory = directory.listFiles();
                if (subDirectory != null) {
                    for (File subWay : subDirectory) {
                        new Thread(new DirectoryRecursion(subWay, pathDirOut)).start();
                    }
                }

            }

        } else {
            System.out.println("Directory is not found.");
        }

    }
}
