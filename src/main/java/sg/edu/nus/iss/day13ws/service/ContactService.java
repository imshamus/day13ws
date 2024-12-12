package sg.edu.nus.iss.day13ws.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

    public void checkDate(Contact contact)
    {
        Date contactDob = contact.getDob();
        // System.out.println("time is >>>>> " + contactDob.getTime());

        // long emsDate = System.currentTimeMillis();
        // System.out.println(emsDate);

        LocalDate currentDate = LocalDate.now();
        Date d1 = Date.from(currentDate.minusYears(10).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date diff = new Date (d1.getTime() - contactDob.getTime()); // getTime returns long
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String diffString = sdf.format(diff);

        System.out.println("Contact: " + contact.getName());
        System.out.println("contactDob is: " + sdf.format(contactDob));
        System.out.println("current time is " + sdf.format(d1));
        System.out.println("Difference is " + sdf.format(diff)); // difference not working

        if (contactDob.after(d1))
        {            
            System.out.printf("%s is %s younger than 10 \n", contact.getName(), diffString);
        }
        else
        {
            System.out.printf("%s is %s older than 10.\n", contact.getName(), diffString);
        }
    }

    public void checkDate2(Contact contact)
    {
        Instant contactInstant = contact.getDob().toInstant();
        LocalDate contactDob = LocalDate.ofInstant(contactInstant, ZoneId.systemDefault()); 

        LocalDate currentDate = LocalDate.now();
        LocalDate tenYearsAgo = currentDate.minusYears(10);

        if (contactDob.isAfter(tenYearsAgo)) {
            System.out.printf("C2 %s is younger than 10 years old.\n", contact.getName());
        } else {
            System.out.printf("C2 %s is older than 10 years old.\n", contact.getName());
        }
    }

    public void checkDate3(Contact contact)
    {

    }
}

