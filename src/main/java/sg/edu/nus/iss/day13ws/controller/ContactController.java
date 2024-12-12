package sg.edu.nus.iss.day13ws.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import sg.edu.nus.iss.day13ws.model.Contact;
import sg.edu.nus.iss.day13ws.service.ContactService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    
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
    
    // @PostMapping("/add")
    // public String postNewContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult bindingResult) {
    // if (bindingResult.hasErrors()) {
    //     return "addform"; // Return form with errors
    // }

    // if (contactService.isYounger(contact)) {
    //     ObjectError error = new ObjectError("globalError", "%s is too young.".formatted(contact.getName()));
    //     bindingResult.addError(error);
    //     return "addform"; // Return form with global error
    // }

    // contactService.addContact(contact);
    // return "redirect:/contacts/list";
    // }

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

    return "addform";
}



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
