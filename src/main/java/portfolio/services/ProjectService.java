package portfolio.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import portfolio.entities.Project;
import portfolio.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Folder path for images
    private final String UPLOAD_DIR = "src/main/resources/static/img/projects/";

    // 1. Get All Projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // 2. Save Project with Image
    public void saveProject(Project project, MultipartFile imageFile) throws IOException {
        
        // Check karte hain ki image file aayi hai ya nahi
        if (imageFile != null && !imageFile.isEmpty()) {
            
            // 🟢 Fix 1: Security - Clean the file path
            String originalFileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            
            // 🟢 Fix 2: Unique File Name - Taki same naam ki 2 images clash na karein
            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
            
            // 🟢 Fix 3: Absolute Path - Taaki folder hamesha sahi jagah bane
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 🟢 Fix 4: Try-with-resources - Taaki memory leak na ho
            try (InputStream inputStream = imageFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
            
            // Database mein unique naam save karenge
            project.setImageFileName(fileName);
        }
        
        // Database mein data save karna
        projectRepository.save(project);
    }

    // 3. Get Project by ID
    public Project getProjectById(Long id) {
        Optional<Project> optional = projectRepository.findById(id);
        return optional.orElse(null);
    }

    // 4. Delete Project
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}