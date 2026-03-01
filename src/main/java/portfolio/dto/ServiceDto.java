package portfolio.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/*
 * NOTE:
 * Lombok IDE me kaam nahi kar raha,
 * isliye Lombok annotations ke saath
 * manual constructors, getters & setters bhi add kiye gaye hain.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {

    @Size(min = 2, max = 30, message = "Invalid Title Length")
    @NotBlank(message = "title cannot be empty")
    private String title;

    @Size(min = 2, max = 80, message = "Invalid description Length")
    @NotBlank(message = "description cannot be empty")
    private String description;

    // Field name SAME rakha gaya hai
    private MultipartFile servicefile;

    // -------- MANUAL NO-ARG CONSTRUCTOR --------
    public ServiceDto() {
    }

    // -------- MANUAL ALL-ARG CONSTRUCTOR --------
    public ServiceDto(String title, String description, MultipartFile servicefile) {
        this.title = title;
        this.description = description;
        this.servicefile = servicefile;
    }

    // -------- MANUAL GETTERS & SETTERS --------
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter/Setter name bhi servicefile ke according fix
    public MultipartFile getServicefile() {
        return servicefile;
    }

    public void setServicefile(MultipartFile servicefile) {
        this.servicefile = servicefile;
    }
}
