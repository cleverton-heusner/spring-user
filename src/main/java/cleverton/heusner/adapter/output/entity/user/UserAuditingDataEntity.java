package cleverton.heusner.adapter.output.entity.user;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Embeddable
@Data
public class UserAuditingDataEntity {

    private String registerUser;
    private LocalDateTime registerDateTime;
    private String lastUpdateUser;
    private LocalDateTime lastUpdateDateTime;
    private String activationUser;
    private LocalDateTime activationDateTime;
    private String deactivationUser;
    private LocalDateTime deactivationDateTime;
}