package sg.edu.nus.iss.day13ws.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day13ws.model.Contact;

@Repository
public class ContactRepo {

    private List<Contact> contacts = new ArrayList<>();

    // Public repo constructor for initial values
    public ContactRepo() throws ParseException {

        // Add initial contacts
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        contacts.add(new Contact("Sheryl", "sheryl@moe.sg", "98889999", sdf.parse("1999-01-02")));
        contacts.add(new Contact("Shamsu", "shamsu@g.com", "88888888", sdf.parse("1996-07-09")));
        contacts.add(new Contact("random", "rand@g.com", "97862543", sdf.parse("2023-07-09")));
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }
    
}
