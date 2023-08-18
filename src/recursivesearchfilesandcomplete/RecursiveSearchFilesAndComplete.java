package recursivesearchfilesandcomplete;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 * @author GC014121
 */
public class RecursiveSearchFilesAndComplete {

    private static final String INITIAL_PATH = "PathFile";

    private static void searchFilesInFolder(final File folder, String partialNameToSearch) throws IOException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                searchFilesInFolder(fileEntry, partialNameToSearch);
            } else {
                if (partialNameToSearch != null && fileEntry.getName().toLowerCase().contains(partialNameToSearch)) {
                    modifyContentFile(fileEntry.getAbsolutePath(), "name=popFrame", " id=\"popFrame\" name=\"popFrame\" ");
                }else{
                    modifyContentFile(fileEntry.getAbsolutePath(), "name=popFrame", " id=\"popFrame\" name=\"popFrame\" ");
                }
            }
        }
    }

    private static void modifyContentFile(String path, String pattern, String toReplace) throws IOException {
        //Get File Content.
        StringBuilder contentBuilder = new StringBuilder();
        try ( Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.ISO_8859_1)) {
            stream.forEach(s -> {
                if (s.contains(pattern) && !s.contains("id=")) {
                    System.err.println("Modificando archivo: " + path);
                    String newLine = s.replace(pattern, toReplace);
                    contentBuilder.append(newLine).append("\n");
                } else {
                    contentBuilder.append(s).append("\n");
                }
            });
        }

        //Modify Content and Write Again in File.
        FileWriter fw = null;
        try {
            fw = new FileWriter(path);
            fw.write(contentBuilder.toString());
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.err.println("Empezando Proceso...");
        File projectDirectory = new File(INITIAL_PATH);
        if (projectDirectory.isDirectory()) {
            searchFilesInFolder(projectDirectory, null);
        }
        System.err.println("Ha concluido el Proceso...");
    }
}
