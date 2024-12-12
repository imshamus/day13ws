package sg.edu.nus.iss.day13ws.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import sg.edu.nus.iss.day13ws.model.Contact;
import sg.edu.nus.iss.day13ws.service.ContactFileService;
import sg.edu.nus.iss.day13ws.service.ContactService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactFileService contactFileService;
    
    @GetMapping("/list")
    public String showContactList(Model model) throws ParseException 
    {
        // List<Contact> contacts = contactService.getAllContact();

        // Add existing contacts to the model
        model.addAttribute("contacts", contactService.getAllContact());
        return "list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "addForm";
    }

    @PostMapping("/add")
    public String postNewContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult bindingResult) {

        
        // Simulate a global error for testing
        if (contactService.isYounger(contact)) {
            //ObjectError error = new ObjectError("globalError", String.format("%s is too young.", contact.getName()));
            ObjectError error = new ObjectError("globalError", "%s is too young".formatted(contact.getName()));
            bindingResult.addError(error);
        }

        if (contactService.isOlder(contact))
        {
            ObjectError error = new ObjectError("globalError", "%s is too old".formatted(contact.getName()));
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors())
        {
            return "addform"; // Return form with errors
        }

    contactService.addContact(contact);
    return "redirect:/contacts/list";
}

    // ResponseEntity is class in Spring FrameWork that rep the entire HTTP response

    // Allows control of HTTP Status Code - Specifies whether the request was successful, failed, or resulted in an error (e.g., 200 OK, 201 CREATED, 500 INTERNAL_SERVER_ERROR)

    // Response Body: The data you want to send back to the client (e.g., success message, JSON object, etc.).

    // @RequestBody is an annotation in Spring that maps the HTTP request body (usually JSON) into a Java object.

    //How It Works:
        // When a client sends a POST request with JSON data in the body, Spring reads the body of the request.
        // Spring automatically deserializes the JSON into a Java object, based on the class specified in the method parameter.

    // Spring will convert this JSON into a Contact object with the corresponding fields.
    // {
    //   "name": "Jane Doe",
    //   "email": "jane.doe@example.com",
    //   "phoneNumber": "98765432",
    //   "dob": "1990-05-05"
    // }


    @PostMapping("/save")
    @ResponseBody // return JSON or plain text 
    // public ResponseEntity<String> saveContact(@RequestBody Contact contact)
    public ResponseEntity<String> saveContact()  
    {
        List<Contact> contacts = contactService.getAllContact();

        try {
            // 1. Generate the Hex ID
            String hexId = contactFileService.generateHexId();

            // 2. Create the file
            File file = contactFileService.createFile(hexId);

            // 3. Write contact data to the file

            for (Contact c : contacts)
            {
                String cString = c.toString();
                contactFileService.writeFile(file.getAbsolutePath(), cString);
            }

            // String contactData = String.format(
            //     "Name: %s\nEmail: %s\nPhone: %s\nDOB: %s",
            //     contact.getName(),
            //     contact.getEmail(),
            //     contact.getPhoneNumber(),
            //     contact.getDob()
            // );
            // contactFileService.writeFile(file.getAbsolutePath(), contactData);

            // 4. Return a success response
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Contact saved with ID: " + hexId);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving contact");
        }
    }

    @GetMapping("/{id}")
    public String getContactById(@PathVariable("id") String id, Model model) throws IOException 
    {
        // Build the file path based on the provided ID
        File file = contactFileService.getFileById(id);

        if (!file.exists()) 
        {
            model.addAttribute("error", "File with ID " + id + " not found.");
            return "error2"; // Use an error HTML page to display the error
        }

        // Read the file content
        List<String> fileContent = contactFileService.readFile(file.getAbsolutePath());
        model.addAttribute("fileContent", fileContent);
        model.addAttribute("fileId", id);

        return "contactdetails"; // Render the HTML page to display file contents
    }
    

    // @GetMapping("/error")
    // public String showErrorPage(Model model) {
    //     // Default msg
    //     model.addAttribute("error", "An unexpected error occured");
    //     return "error";
    // }
    

    @GetMapping("/test")
    // public ResponseEntity<String> testMethod() 
    public ResponseEntity<List<Contact>> testMethod() 
    {
        List<Contact> contacts = contactService.getAllContact();

        for (Contact c : contacts)
        {
            contactService.checkDate3(c);
        }

        // return ResponseEntity.ok("Test successful!"); // Sends plain text "Test successful!" as the response
        return ResponseEntity.ok(contacts);
    }
    
    
    
    
}
