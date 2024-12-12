package sg.edu.nus.iss.day13ws.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContactFileService {
    
    // Inject the dataDir value from application.properties or runtime argument
    @Value("${dataDir}")
    private String dataDir;

     // Generate an 8-character hex ID
    public String generateHexId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }


    // Create file in data dir
    public File createFile(String hexId) throws IOException
    {
        Path dirPath = Paths.get(dataDir);

         // Ensure the directory exists
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // Create the file
        File file = new File(dirPath.toFile(), hexId + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    public File getFileById(String hexId) 
    {
        File f = new File(Paths.get(dataDir, hexId + ".txt").toString());

        return f;
    }
    // dataDir = "/Users/shamus/tmp/data"
    // hexId = "abcd1234"
    // Paths.get(dataDir, hexId + ".txt") = /Users/shamus/tmp/data/abcd1234.txt
    // Paths.get(...).toString(): Converts Path obj to String rep of file path
    // new File creates a new File obj rep-ing the file at the specified path
    // getFileById doesn't create a new file on disk, simply an in memory obj to refer to file path
    // allows working with file, read/write, exist() delete(), without creating a physical file unless explicitly required
    
    // e.g.
    // File file = getFileById("abcd1234");
    // if (file.exists()) {
    //     System.out.println("File exists at: " + file.getAbsolutePath());
    // } else {
    //     System.out.println("File does not exist");
    // }

    // e.g. 
    // if (!file.exists()) {
    //     file.createNewFile(); // Create the file on disk
    // }




    // // Read file without BR
    // public List<String> readFile(String filePath) throws IOException 
    // {
    //     Path path = Paths.get(filePath);
    //     return Files.readAllLines(path); // Read all lines from the file
    // }

    // Read File
    public List<String> readFile(String filePath) 
    {
        Path pi = Paths.get(filePath); // Input file

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pi.toFile()))) 
        {
            String line;
            
            while ((line = br.readLine()) != null) 
            {
                lines.add(line);
                // System.out.println(line); // Process the line
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        return lines;
    }

    // Write File
    public void writeFile(String outputPath, String data) {
        Path po = Paths.get(outputPath); // Output file

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(po.toFile(), true)))
        {
            bw.write(data);
            bw.newLine(); // add new line after each write
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
