package portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import portfolio.entities.AppUser;
import portfolio.repository.UserRepository;

@SpringBootApplication
public class MyPortfolio1Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MyPortfolio1Application.class, args);

        UserRepository userRepo = ctx.getBean(UserRepository.class);
        PasswordEncoder encoder = ctx.getBean(PasswordEncoder.class);

        // admin
        if (userRepo.findByUsername("admin") == null) {
            AppUser appUser = new AppUser();
            appUser.setUsername("admin");
            appUser.setPassword(encoder.encode("admin123"));
            appUser.setRole("ROLE_ADMIN");
            userRepo.save(appUser);
            System.out.println("ADMIN INSERT");
        } else {
            System.out.println("ADMIN ALREADY EXIST");
        }

        // member
        if (userRepo.findByUsername("member") == null) {
            AppUser appUser = new AppUser();
            appUser.setUsername("member");
            appUser.setPassword(encoder.encode("member123"));
            appUser.setRole("ROLE_MEMBER");
            userRepo.save(appUser);
            System.out.println("MEMBER INSERT");
        } else {
            System.out.println("MEMBER ALREADY EXIST");
        }
    }
}
