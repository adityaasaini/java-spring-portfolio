package portfolio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import portfolio.dto.ContactDto;
import portfolio.entities.Project;
import portfolio.services.ContactService;
import portfolio.services.EmailService;
import portfolio.services.ProjectService;
import portfolio.services.ServicesServices;

@Controller
@RequestMapping("/client")
public class MyController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ServicesServices servicesServices;

    @GetMapping("/mylogin")
    public String login() {
        return "login";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @GetMapping("/home")
    public String home(Model model) {
        // Homepage ke liye data fetch karna
        model.addAttribute("listOfServices", servicesServices.readServices());
        
        List<Project> projectList = projectService.getAllProjects();
        model.addAttribute("projectList", projectList);
        
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("listOfServices", servicesServices.readServices());
        return "services";
    }

 // Services Inject karein
    @Autowired
    private EmailService emailService;

    // Aapka purana ContactService bhi yahan hona chahiye
    @Autowired
    private ContactService contactService1; 


    // =====================================================
    // 1. PAGE OPEN KARNE KE LIYE (GET MAPPING)
    // Ye method delete ho gaya tha, isliye 404 aa raha tha
    // =====================================================
    @GetMapping("/contact")
    public String contact() {
        return "contact"; // Ye aapki contact.html file ko load karta hai
    }


    // =====================================================
    // 2. FORM SUBMIT KARNE KE LIYE (POST MAPPING)
    // =====================================================
    @PostMapping("/savecontact")
    public String savecontact(@Valid @ModelAttribute ContactDto contactDto,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        // Step 1: Validation check
        if (bindingResult.hasErrors()) {
            model.addAttribute("result", "invalid input");
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "contact";
        }

        // Step 2: Duplicate email check
        if (contactService.isContactEmailExist(contactDto.getEmail())) {
            redirectAttributes.addFlashAttribute("result", "You have already sent");
            return "redirect:/client/contact";
        }

        // Step 3: Database mein save karna
        contactService.savecontact(contactDto);

        // Step 4: EMAIL SEND KARNE KA LOGIC
        try {
            // Email ka format aur design
            String subject = "New Portfolio Inquiry: " + contactDto.getSubject();
            String body = "You have received a new message from your portfolio!\n\n" +
                          "--------------------------------------\n" +
                          "Sender Name: " + contactDto.getName() + "\n" +
                          "Sender Email: " + contactDto.getEmail() + "\n" +
                          "Message:\n" + contactDto.getMessage() + "\n" +
                          "--------------------------------------";

            // Kis email par aap notification chahte hain
            String toEmail = "aadityaa0006@gmail.com"; 

            // Email bhejna
            emailService.sendEmail(toEmail, subject, body);
            
        } catch (Exception e) {
            // Agar email bhejne mein koi error aati hai (internet issue etc.)
            System.out.println("Email notification fail ho gayi: " + e.getMessage());
        }

        // Step 5: Success message UI par bhejna
        redirectAttributes.addFlashAttribute("result", "Message sent successfully!");
        return "redirect:/client/contact";
    }

    @GetMapping("/downloadResume")
    public void downloadResume(HttpServletResponse response) throws IOException {
        // FIXED: Seedha disk se file uthayenge taaki upload ke turant baad download ho sake
        String resumePath = "src/main/resources/static/resume/Myresume.pdf";
        java.io.File file = new java.io.File(resumePath);
        
        if (file.exists()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Myresume.pdf");
            
            // File data copy karna
            java.nio.file.Files.copy(file.toPath(), response.getOutputStream());
            response.getOutputStream().flush();
        } else {
            // Agar file nahi mili toh message
            response.setContentType("text/html");
            response.getWriter().write("<h2 style='color:red;'>Resume file not found!</h2>" +
                                     "<p>Pehle Admin Panel se resume upload karein.</p>");
        }
    }
    
    
    


 @Autowired
 private ProjectService projectService;

 
 @GetMapping("/projects")
 public String showProjectsPage(Model model) {
     // Database se saare projects fetch karna
     List<Project> projectList = projectService.getAllProjects();
     
     
     model.addAttribute("projectList", projectList);
     
     return "projects"; 
 }
    
    
    
    
    }
