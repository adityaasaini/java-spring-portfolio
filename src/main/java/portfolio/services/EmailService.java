package portfolio.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    // application.properties se 64-digit API key automatically utha lega
    @Value("${brevo.api.key}")
    private String apiKey;

    @Async // Background process ke liye taaki website load na le
    public void sendEmail(String toEmail, String subject, String body) {
      
    	
    	
    	
    	
    	try {
    		
    		
            
    		
    		
    		
    		
            // Brevo API Endpoint
            String url = "https://api.brevo.com/v3/smtp/email";
            RestTemplate restTemplate = new RestTemplate();

            // 1. Headers set karna
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey.trim());
            headers.set("accept", "application/json");

            // 2. Email Body (JSON) banana
            Map<String, Object> requestBody = new HashMap<>();
            
            // SENDER: Jis email se Brevo account banaya hai
            Map<String, String> sender = new HashMap<>();
            sender.put("name", "Portfolio System");
            sender.put("email", "neuralsoft.ai@gmail.com"); 

            // RECEIVER: Jisko mail jayega (Controller se aayega)
            Map<String, String> to = new HashMap<>();
            to.put("email", toEmail);
            to.put("name", "Aditya Admin");

            requestBody.put("sender", sender);
            requestBody.put("to", List.of(to));
            requestBody.put("subject", subject);
            
            // Email design (HTML)
            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #00b894; border-radius: 10px;'>"
                               + "<h2 style='color: #00b894;'>New Portfolio Message</h2>"
                               + "<p style='white-space: pre-wrap; color: #333;'>" + body + "</p>"
                               + "</div>";
            requestBody.put("htmlContent", htmlContent);

            // 3. API Request Bhejna
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            
            System.out.println("✅ Brevo API Email Sent! Status: " + response.getStatusCode());

        } catch (Exception e) {
            System.out.println("❌ Brevo API Email Failed: " + e.getMessage());
       
        
        }
    	
    }
}