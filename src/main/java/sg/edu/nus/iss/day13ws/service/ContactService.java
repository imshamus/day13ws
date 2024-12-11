package sg.edu.nus.iss.day13ws.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day13ws.model.Contact;
import sg.edu.nus.iss.day13ws.repository.ContactRepo;

@Service
public class ContactService {

    @Autowired
    private ContactRepo contactRepo;
    
    public List<Contact> getAllContact()
    {
        return contactRepo.getContacts();
    }

    public void addContact(Contact contact)
    {
        contactRepo.addContact(contact);
    }
}

