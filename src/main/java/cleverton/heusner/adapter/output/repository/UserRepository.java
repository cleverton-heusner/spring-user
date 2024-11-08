package cleverton.heusner.adapter.output.repository;

import cleverton.heusner.adapter.output.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByCpf(final String cpf);

    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', TRIM(:nameOrZipCode), '%')) OR" +
            " LOWER(u.addressEntity.zipCode) LIKE LOWER(CONCAT('%', TRIM(:nameOrZipCode), '%'))")
    List<UserEntity> filterByNameOrZipCode(@Param("nameOrZipCode") final String nameOrZipCode);
}