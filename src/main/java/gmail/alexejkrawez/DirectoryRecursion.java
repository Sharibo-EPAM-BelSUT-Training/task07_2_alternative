package gmail.alexejkrawez;

import java.io.File;

public class DirectoryRecursion implements Runnable {

    private String path;
    private String pathDirOut;
    private String name;
    private File directory;

    DirectoryRecursion(File directory, String pathDirOut) {
        this.directory = directory;
        this.pathDirOut = pathDirOut;
     }

    @Override
    public void run() {

        if (directory.isFile()) {
            path = directory.getAbsolutePath();
            new Thread(new ChangeXML(path, pathDirOut)).start(); // отдельный поток будет обрабатывать найденный файл

        } else {

            name = directory.getName();
            pathDirOut = pathDirOut + File.separator + name.replace(name, "ADC_" + name);
            File directoryOut = new File(pathDirOut);
            directoryOut.mkdirs();
            System.out.println(directoryOut.getName()); // печатает в консоль названия изменённых папок

            File[] subDirectory = directory.listFiles();
            if (subDirectory != null) {
                for (File subWay : subDirectory) {
                    new Thread(new DirectoryRecursion(subWay, pathDirOut)).start(); // неявная рекурсия через
                                                                                    // отдельный поток
                }
            }

        }

    }


}
