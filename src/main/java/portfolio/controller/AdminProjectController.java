package portfolio.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import portfolio.entities.Project;
import portfolio.services.ProjectService;

@Controller
@RequestMapping("/admin")
public class AdminProjectController {

    @Autowired
    private ProjectService projectService;

    // 🟢 1. View Projects List
    @GetMapping("/projects")
    public String viewProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "admin/projects-list"; 
    }

    // 🟢 2. Show Add Form
    @GetMapping("/add-project")
    public String showAddProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "admin/admin-add-project"; 
    }

    // 🟢 3. Save Project
    @PostMapping("/save-project")
    public String saveProject(@ModelAttribute("project") Project project,
                              @RequestParam("image") MultipartFile imageFile,
                              HttpSession session) {
        try {
            projectService.saveProject(project, imageFile);
            session.setAttribute("msg", "Project Added Successfully!");
            return "redirect:/admin/projects"; // List par bhej rahe hain success ke baad
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "Error: " + e.getMessage());
            return "redirect:/admin/add-project";
        }
    }

    // 🟢 4. Delete Project (FIXED URL)
    @GetMapping("/delete-project/{id}")
    public String deleteProject(@PathVariable("id") Long id, HttpSession session) {
        try {
            projectService.deleteProject(id);
            session.setAttribute("msg", "Project Deleted Successfully!");
        } catch (Exception e) {
            session.setAttribute("msg", "Error deleting project!");
        }
        // Always redirect back to the projects list
        return "redirect:/admin/projects"; 
    }
}