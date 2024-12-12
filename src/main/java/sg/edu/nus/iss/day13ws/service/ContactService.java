package sg.edu.nus.iss.day13ws.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        // Convert contact's Date of Birth (Date) to LocalDate
        Date contactDob = contact.getDob();
        // toInsant converts Date (represent a specific pt in time in ms since unix epoch) into an Instant
        // Instant represents an moment on UTC timeline, typically represented in nanoseconds from the epoch (1970-01-01T00:00:00Z)
        // old Date class only works with time in ms, x handles time zones directly. Converting to Instant allows the use of modern java.time classes, more flexible and easier to use.
        LocalDate contactDobLocal = contactDob.toInstant()
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate();
                                            // extracts just the date (YMD)

        // Get the current date and calculate the date 10 years ago
        LocalDate currentDate = LocalDate.now();
        LocalDate date10YearsAgo = currentDate.minusYears(10);

        // Calculate the difference in years relative to "10 years ago" benchmark to see the how much older/younger
        int yearsDifference = Period.between(contactDobLocal, date10YearsAgo).getYears(); 

        // Determine if the contact is younger or older than 10 years
        System.out.println("Contact: " + contact.getName());
        System.out.println("Date of Birth: " + contactDobLocal);
        System.out.println("Current Date: " + currentDate);
        System.out.println("Date 10 years ago: " + date10YearsAgo);

        if (contactDobLocal.isAfter(date10YearsAgo)) {
            // System.out.printf("%s is %d years younger than 10.\n", contact.getName(), yearsDifference);
            System.out.printf("%s is %d years younger than 10.\n", contact.getName(), Math.abs(yearsDifference));
        } else {
            // System.out.printf("%s is %d years older than 10.\n", contact.getName(), yearsDifference);
            System.out.printf("%s is %d years older than 10.\n", contact.getName(), Math.abs(yearsDifference));
        }
    }







    public boolean isYounger(Contact contact)
    {
        Date contactDob = contact.getDob();
        LocalDate contactDobLocal = contactDob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate currentDate = LocalDate.now();
        LocalDate date10YearsAgo = currentDate.minusYears(10);

        System.out.println("Contact DOB LocalDate: " + contactDobLocal);
        System.out.println("Date 10 Years Ago: " + date10YearsAgo);

        boolean isYounger = contactDobLocal.isAfter(date10YearsAgo);
        System.out.println("Is Younger Than 10: " + isYounger);
        
        return isYounger; // true if younger
    }

    public boolean isOlder(Contact contact)
    {
        Date contactDob = contact.getDob();
        LocalDate contactDobLocal = contactDob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate currentDate = LocalDate.now();
        
        int yearsDifference = Period.between(contactDobLocal, currentDate).getYears();

        if (yearsDifference > 100)
        {
            return true;
        }

        return false;
    }







    public void printDate()
    {
        // toInstant() - Converts a Date object to an Instant.
        Date date = new Date();
        System.out.println(date); // Prints current date and time
        Instant instant = date.toInstant();
        System.out.println(instant); // e.g., 2024-12-12T14:00:00Z

        // atZone() - Takes the Instant (which is in UTC) and applies the system's default time zone to it, creating a ZonedDateTime.
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime); // e.g., 2024-12-12T22:00+08:00[Asia/Singapore]

        // toLocalDate() - Extracts just the date (year, month, day) from the ZonedDateTime, ignoring the time and time zone. returns a LocalDate object
        LocalDate localDate = zonedDateTime.toLocalDate();
        System.out.println(localDate); // e.g., 2024-12-12

        // minusYears(10) - Subtracts 10 years from the given LocalDate. Returns a new LocalDate instance (since LocalDate is immutable).
        LocalDate date10YearsAgo = LocalDate.now().minusYears(10);
        System.out.println(date10YearsAgo); // e.g., 2014-12-12

        // Period.between() - Calculates the difference between two LocalDate objects in terms of years, months, and days.
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        Period period = Period.between(startDate, endDate);
        System.out.println(period.getYears()); // 20 years

        // getYears() - Extracts the year component of a Period.
        int yearsDifference = Period.between(startDate, endDate).getYears();
        System.out.println(yearsDifference); // 20

        // isAfter() - Checks if one LocalDate is after another LocalDate.
        // Returns true if the first date is later than the second date.

        LocalDate today = LocalDate.now();
        LocalDate pastDate = LocalDate.of(2000, 1, 1);
        System.out.println(today.isAfter(pastDate)); // true

    }
}

