package sg.edu.nus.iss.day13ws.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Contact {

    @NotEmpty(message = "Name must not be empty.")
    @Size(min = 3, max = 10, message = "Name must between 3-10 characters long.")
    private String name;

    @NotEmpty(message = "Email field must not be empty.")
    @Email(message = "Please enter a valid email.")
    private String email;

    @NotEmpty(message = "Phone number must not be empty")
    @Pattern(regexp = "(8|9)[0-9]{7}", message = "Phone number must starts with 8 or 9 follow by 7 digits")
    private String phoneNumber;

    @Past(message = "Date must be in the past.")
    @NotNull(message = "Date must not be null.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    public Contact() {
    }

    public Contact(String name, String email, String phoneNumber, Date dob) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return name + "," + email + "," + phoneNumber + "," + dob;
    }
    
}
