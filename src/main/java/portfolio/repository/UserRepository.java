package portfolio.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import portfolio.entities.AppUser;




@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    // 🛑 Optional hata diya hai taaki controller ke logic se match kare
    AppUser findByUsername(String username);
}
