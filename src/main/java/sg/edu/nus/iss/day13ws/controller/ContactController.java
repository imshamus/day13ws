package sg.edu.nus.iss.day13ws.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

        model.addAttribute("contacts", contactService.getAllContact());
        model.addAttribute("contact", new Contact());
        return "list";
    }

    @PostMapping("/list")
    public String postNewContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult bindingResult) throws ParseException {

        if (bindingResult.hasErrors()) {
            return "/list";
        }
        
        List<Contact> contacts = contactService.getAllContact();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        contacts.add(contact);        
        
        return "redirect:/contacts/list";
    }
    
    
    
}
