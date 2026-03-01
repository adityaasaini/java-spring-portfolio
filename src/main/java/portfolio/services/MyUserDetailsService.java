package portfolio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import portfolio.entities.AppUser;
import portfolio.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepositry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 🛑 FIXED: UserRepository ab seedha AppUser return karta hai, Optional nahi
        AppUser appUser = userRepositry.findByUsername(username);

        // ✅ FIXED: Optional.isEmpty() ki jagah null check karein (Java 8 standard)
        if (appUser == null) {
            throw new UsernameNotFoundException("User name not found: " + username);
        }

        // Spring Security UserDetails Builder logic
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                // roles() method automatically "ROLE_" prefix add kar deta hai
                .roles(appUser.getRole().replace("ROLE_", ""))
                .build();
    }
}
