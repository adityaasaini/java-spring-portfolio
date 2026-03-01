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
    public String home() {
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

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @PostMapping("/savecontact")
    public String savecontact(@Valid @ModelAttribute ContactDto contactDto,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("result", "invalid input");
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "contact";
        }

        if (contactService.isContactEmailExist(contactDto.getEmail())) {
            redirectAttributes.addFlashAttribute("result", "You have already sent");
            return "redirect:/client/contact";
        }

        contactService.savecontact(contactDto);
        redirectAttributes.addFlashAttribute("result", "Contact saved successfully");
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
