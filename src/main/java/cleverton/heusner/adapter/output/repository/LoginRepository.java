package cleverton.heusner.adapter.output.repository;

import cleverton.heusner.adapter.output.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginRepository extends JpaRepository<LoginEntity, String> {
    UserDetails findByUsername(final String username);
}
