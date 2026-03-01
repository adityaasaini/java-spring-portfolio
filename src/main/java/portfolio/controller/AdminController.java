package portfolio.controller;



import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // Changed from BCrypt
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import portfolio.dto.ServiceDto;
import portfolio.entities.AppUser; // Only one import needed
import portfolio.entities.ServiceEntity;
import portfolio.repository.UserRepository; // image_e8fd3e.png ke hisab se
import portfolio.services.ContactService;
import portfolio.services.ServicesServices;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ServicesServices servicesServices;
    
    @Autowired
    private PasswordEncoder passwordEncoder;	 // Password encrypt karne ke liye

    @Autowired
    private UserRepository userRepository; // image_e8fd3e.png ke hisab se sahi naam

    @GetMapping("/home")
    public String home() {
        return "admin/adminHome";
    }
    
    @GetMapping("/readAllContacts")
    public String readAllContacts(Model model) {
        model.addAttribute("contactData", contactService.readAllContacts());
        return "admin/readAllContacts";
    }

    @GetMapping("/deleteContactById")
    public String deleteContactById(@RequestParam int id) {
        contactService.deleteContactById(id);
        return "redirect:/admin/readAllContacts";
    }
    
    @GetMapping("/addService")
    public String addServiceview() {
        return "admin/addService";
    }

    @PostMapping("/addService")
    public String addService(@Valid @ModelAttribute ServiceDto serviceDto, BindingResult result, 
            Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IllegalStateException, IOException {
        if(result.hasErrors()) {
            model.addAttribute("result", "Invalid input");
            return "admin/addService";
        }
        
        MultipartFile multipartFile = serviceDto.getServicefile();
        String realPath = request.getServletContext().getRealPath("img/services");
        servicesServices.saveService(realPath, multipartFile, serviceDto);
        
        redirectAttributes.addFlashAttribute("result", "Service added Successfully");
        return "redirect:/admin/addService";
    }
    
    @GetMapping("/readAllServices")
    public String readAllServices(Model model) {
        model.addAttribute("listOfServices", servicesServices.readServices());
        return "admin/readAllServices";
    }

    @GetMapping("/deleteService")
    public String deleteService(@RequestParam int id, @RequestParam String filename, HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("img/services");
        servicesServices.deleteService(realPath, id, filename);
        return "redirect:/admin/readAllServices";
    }

    @GetMapping("/updateService")
    public String updateServiceView(@RequestParam int id, Model model) {
        Optional<ServiceEntity> service = servicesServices.readService(id);
        if(service.isPresent()) {
            model.addAttribute("serviceData", service.get());
        }
        return "admin/updateService";
    }

    // ================= RESUME MANAGEMENT =================

    @GetMapping("/uploadResume")
    public String uploadResumeView() {
        return "admin/uploadResume";
    }

    @PostMapping("/uploadResume")
    public String uploadResume(@RequestParam MultipartFile resume, RedirectAttributes redirectAttributes) {
        if(resume == null || resume.isEmpty()) {
            redirectAttributes.addFlashAttribute("result", "Resume must be uploaded");
            return "redirect:/admin/uploadResume";
        }

        try {
            String projectPath = System.getProperty("user.dir");
            String uploadFolderPath = projectPath + File.separator + "src" + File.separator + 
                                       "main" + File.separator + "resources" + File.separator + 
                                       "static" + File.separator + "resume";

            File dir = new File(uploadFolderPath);
            if (!dir.exists()) dir.mkdirs(); 

            Path path = Paths.get(uploadFolderPath, "Myresume.pdf");
            resume.transferTo(path.toFile());

            redirectAttributes.addFlashAttribute("result", "Resume updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("result", "Upload failed: " + e.getMessage());
        }
        return "redirect:/admin/uploadResume";
    }

    // 🟢 NEW: Download CV Logic (Iske bina button kaam nahi karega)
    @GetMapping("/downloadResume")
    public ResponseEntity<Resource> downloadResume() throws MalformedURLException {
        String projectPath = System.getProperty("user.dir");
        String filePathString = projectPath + File.separator + "src" + File.separator + 
                                 "main" + File.separator + "resources" + File.separator + 
                                 "static" + File.separator + "resume" + File.separator + "Myresume.pdf";
        
        Path filePath = Paths.get(filePathString);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Aditya_Sain_CV.pdf\"")
                .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
 // 1. GET: Change Password ka page dikhane ke liye
    @GetMapping("/change-password")
    public String showChangePassword() {
        return "admin/change-password";
    }

    // 2. POST: Password update karne ka asli logic
    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("oldPass") String oldPass, 
                                 @RequestParam("newPass") String newPass,
                                 Principal principal, 
                                 RedirectAttributes redirectAttributes) { // 👈 RedirectAttributes use karein

        String username = principal.getName();
        portfolio.entities.AppUser user = userRepository.findByUsername(username); // Java 8 compatible

        if (user != null) {
            if (passwordEncoder.matches(oldPass, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPass));
                userRepository.save(user);
                redirectAttributes.addFlashAttribute("msg", "Success: Password change ho gaya! ✅");
                redirectAttributes.addFlashAttribute("msgClass", "alert-success");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Error: Purana password galat hai! ❌");
                redirectAttributes.addFlashAttribute("msgClass", "alert-danger");
            }
        }
        return "redirect:/admin/change-password"; 
    }
}