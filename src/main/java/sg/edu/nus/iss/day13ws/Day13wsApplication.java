package sg.edu.nus.iss.day13ws;

import java.nio.file.Paths;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day13wsApplication {

	public static void main(String[] args) {

		SpringApplication.run(Day13wsApplication.class, args);

		// Parse arguments using DefaultApplicationArguments
		ApplicationArguments appArgs = new DefaultApplicationArguments(args);

		String dataDirPath = "";
		String fileName = "";
		// Check if dataDir is provided
		if (appArgs.containsOption("dataDir"))
		{
			dataDirPath = 
		}
		

		// Check that --dataDir argument is provided

		// If dataDir not provided, stop the application with an error message

		// Create the directory if it doesn't exist

	}

}
