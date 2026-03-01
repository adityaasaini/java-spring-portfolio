package portfolio.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import portfolio.dto.ServiceDto;
import portfolio.entities.ServiceEntity;
import portfolio.repository.ServiceRepository;


@Service
public class ServicesServicesImpl implements ServicesServices {

    private final ServiceRepository serviceRepositry; // FIXED: class name 'ServiceRepositry'

    @Autowired
    public ServicesServicesImpl(ServiceRepository serviceRepositry) {
        this.serviceRepositry = serviceRepositry;
    }

    // REMOVED: saveService1 method (As requested)

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceEntity saveService(String realPath, MultipartFile multipartFile, ServiceDto serviceDto) {
        String fileName = UUID.randomUUID().toString()
                + LocalDateTime.now().toString().replace(":", "a")
                + multipartFile.getOriginalFilename();

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setTitle(serviceDto.getTitle());
        serviceEntity.setDescription(serviceDto.getDescription());
        serviceEntity.setFilename(fileName);
        serviceEntity.setDatetime(LocalDateTime.now().toString());

        ServiceEntity entity = serviceRepositry.save(serviceEntity);

        // ✨ FIXED: Ensure directory exists before saving
        try {
            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            Path path = Paths.get(realPath, fileName);
            multipartFile.transferTo(path.toFile());
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public List<ServiceEntity> readServices() {
        return serviceRepositry.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteService(String realPath, int id, String filename) {
        serviceRepositry.deleteById(id);
        File file = new File(realPath + File.separator + filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public Optional<ServiceEntity> readService(int id) {
        return serviceRepositry.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceEntity updateService(String realPath, MultipartFile multipartFile, 
                                     ServiceDto serviceDto, int id, String oldFilename) throws Exception {
        
        Optional<ServiceEntity> optionalService = serviceRepositry.findById(id);
        if(optionalService.isEmpty()) {
            throw new Exception("Service not found with id: " + id);
        }
        
        ServiceEntity serviceEntity = optionalService.get();
        
        serviceEntity.setTitle(serviceDto.getTitle());
        serviceEntity.setDescription(serviceDto.getDescription());
        
        if(multipartFile != null && !multipartFile.isEmpty()) {
            File oldFile = new File(realPath + File.separator + oldFilename);
            if(oldFile.exists()) {
                oldFile.delete();
            }
            
            String fileName = UUID.randomUUID().toString()
                    + LocalDateTime.now().toString().replace(":", "a")
                    + multipartFile.getOriginalFilename();
            serviceEntity.setFilename(fileName);
            
            Path path = Paths.get(realPath, fileName);
            File file = path.toFile();
            multipartFile.transferTo(file);
            
        } else {
            serviceEntity.setFilename(oldFilename);
        }
        
        serviceEntity.setDatetime(LocalDateTime.now().toString());
        
        return serviceRepositry.save(serviceEntity);
    }
}