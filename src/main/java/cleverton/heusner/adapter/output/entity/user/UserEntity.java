package cleverton.heusner.adapter.output.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import static cleverton.heusner.adapter.input.constant.validation.UserValidationErrorMessage.USER_CPF_SIZE;
import static cleverton.heusner.adapter.input.constant.validation.UserValidationErrorMessage.USER_NAME_MAX_SIZE;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = USER_NAME_MAX_SIZE)
    private String name;

    @Column(nullable = false, length = USER_CPF_SIZE, unique = true)
    private String cpf;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Embedded
    private AddressEntity addressEntity;

    @Embedded
    private UserAuditingDataEntity userAuditingDataEntity;

    @Column(nullable = false)
    private boolean active;
}