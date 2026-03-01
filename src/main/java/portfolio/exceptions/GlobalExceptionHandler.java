package portfolio.exceptions;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Note: ServicesServicesImpl injection hata di hai kyunki yahan uska use nahi ho raha tha.

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, RedirectAttributes redirectAttributes,
            HttpServletRequest httpServletRequest) {
        
        String header = httpServletRequest.getHeader("referer");
        
        // Console par error print karne ke liye
        System.out.println("Error: " + e.getMessage());
        
        redirectAttributes.addFlashAttribute("result", "Something went wrong. Please try again.");
        
        // FIXED: Agar header null hai (direct URL access), toh home page par redirect hoga
        String redirectUrl = (header != null) ? header : "/client/home";
        return "redirect:" + redirectUrl;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(Exception e, RedirectAttributes redirectAttributes,
            HttpServletRequest httpServletRequest) {
        
        String header = httpServletRequest.getHeader("referer");
        
        redirectAttributes.addFlashAttribute("result", "File Size must not exceed 5MB");
        
        // FIXED: Redirection safety check
        String redirectUrl = (header != null) ? header : "/admin/addService";
        return "redirect:" + redirectUrl;
    }
}