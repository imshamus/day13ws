package sg.edu.nus.iss.day13ws;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day13wsApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Day13wsApplication.class);

		// SpringApplication.run(Day13wsApplication.class, args);

		// Parse arguments using DefaultApplicationArguments
		ApplicationArguments appArgs = new DefaultApplicationArguments(args);

		String dataDir = ""; // initiliased the dir path variable
		
		// Check if dataDir is provided
		if (appArgs.containsOption("dataDir"))
		{
			// Checking if dataDir is provided from the command line
			// getOptionValues("dataDir") returns a list of values associated with the --dataDir argument.
			// java -jar myapp.jar --dataDir /path/to/dir1 --dataDir /path/to/dir2
			// ["/path/to/dir1", "/path/to/dir2"]
			// .get(0) retrieves first value of that list
			dataDir = appArgs.getOptionValues("dataDir").get(0);
			System.out.printf("Application will use data directory: %s\n", dataDir);

			try 
			{
				// Creating the directory if it doesn't exist
				Path dataDirPath = Paths.get(dataDir);
				
				if(Files.exists(dataDirPath)) // if file DNE
				{
					System.out.printf("Directory already exists at: %s \n", dataDirPath.toAbsolutePath().toString());
				}
				else
				{
					Files.createDirectories(dataDirPath); // Create parent directories if DNE
					System.out.printf("Directory created at: %s \n", dataDirPath.toAbsolutePath().toString());
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			// Start the Spring Boot application
			app.run(args);
		}

		else

		{
			System.out.println("Can't start program, --dataDir argument is required.. ending..");
			System.exit(1);
		}
		

		// Check that --dataDir argument is provided

		// If dataDir not provided, stop the application with an error message

		// Create the directory if it doesn't exist

	}

}
