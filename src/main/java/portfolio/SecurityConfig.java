package portfolio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // 1. Static Resources: Yahan "/resume/**" add kiya hai taaki PDF file direct access ho sake
                .antMatchers("/css/**", "/js/**", "/img/**", "/vendors/**", "/scss/**", "/resume/**").permitAll()
                
                // 👉 FIX YAHAN HAI: Admin block se pehle download URL ko bypass karna zaroori hai
                .antMatchers("/admin/download**", "/download**").permitAll() 
                
                // 2. 🛑 MEMBER BLOCK: Ab /admin/** sirf ADMIN hi access kar sakta hai
                .antMatchers("/admin/**").hasRole("ADMIN") 
                
                // 3. Baaki saare client pages public rahein
                .anyRequest().permitAll()
            .and()
            .formLogin()
                .loginPage("/client/mylogin")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("user")
                .passwordParameter("pass")
                .defaultSuccessUrl("/admin/home", false)
            .and()
            .logout()
                .logoutUrl("/logout") // Logout URL
                .logoutSuccessUrl("/client/mylogin?logout") // Logout ke baad kahan jaye
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            .and()
                .exceptionHandling()
                .accessDeniedPage("/client/unauthorized");
                
        // 🔒 CSRF Protection: Thymeleaf use kar rahe ho toh ye enabled rehna chahiye
        // http.csrf().disable(); // Ise kabhi disable mat karna Production mein
        
        return http.build();
    }
}