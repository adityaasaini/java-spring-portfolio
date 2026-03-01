package portfolio.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ContactDto {
    
    @Size(min = 2, max = 30, message = "Invalid name Length")
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Size(min = 2, max = 30, message = "Enter valid email")
    @NotBlank(message = "email cannot be empty")
    private String email;

    @Size(min = 2, max = 30, message = "Invalid subject length")
    @NotBlank(message = "Subject cannot be empty")
    private String subject;

    @Size(min = 2, max = 30, message = "Invalid message Length")
    @NotBlank(message = "message cannot be empty") // FIXED: 'meassge' corrected to 'message'
    private String message;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}