package portfolio.services;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import portfolio.dto.ServiceDto;
import portfolio.entities.ServiceEntity;

public interface ServicesServices {

    ServiceEntity saveService(String realPath, MultipartFile multipartFile, ServiceDto serviceDto);
    
    List<ServiceEntity> readServices();
    
    void deleteService(String realPath, int id, String filename);
    
    Optional<ServiceEntity> readService(int id);
    
    ServiceEntity updateService(String realPath, MultipartFile multipartFile, ServiceDto serviceDto,
            int id, String oldFilename) throws Exception;
}