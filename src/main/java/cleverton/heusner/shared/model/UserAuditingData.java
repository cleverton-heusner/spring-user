package cleverton.heusner.shared.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuditingData {

    private String registerUser;
    private LocalDateTime registerDateTime;
    private String lastUpdateUser;
    private LocalDateTime lastUpdateDateTime;
    private String activationUser;
    private LocalDateTime activationDateTime;
    private String deactivationUser;
    private LocalDateTime deactivationDateTime;

    public String getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(String registerUser) {
        this.registerUser = registerUser;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }

    public void setRegisterDateTime(LocalDateTime registerDateTime) {
        this.registerDateTime = registerDateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public LocalDateTime getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public String getActivationUser() {
        return activationUser;
    }

    public void setActivationUser(String activationUser) {
        this.activationUser = activationUser;
    }

    public LocalDateTime getActivationDateTime() {
        return activationDateTime;
    }

    public void setActivationDateTime(LocalDateTime activationDateTime) {
        this.activationDateTime = activationDateTime;
    }

    public String getDeactivationUser() {
        return deactivationUser;
    }

    public void setDeactivationUser(String deactivationUser) {
        this.deactivationUser = deactivationUser;
    }

    public LocalDateTime getDeactivationDateTime() {
        return deactivationDateTime;
    }

    public void setDeactivationDateTime(LocalDateTime deactivationDateTime) {
        this.deactivationDateTime = deactivationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuditingData that = (UserAuditingData) o;
        return Objects.equals(registerUser, that.registerUser) && Objects.equals(registerDateTime, that.registerDateTime) && Objects.equals(lastUpdateUser, that.lastUpdateUser) && Objects.equals(lastUpdateDateTime, that.lastUpdateDateTime) && Objects.equals(activationUser, that.activationUser) && Objects.equals(activationDateTime, that.activationDateTime) && Objects.equals(deactivationUser, that.deactivationUser) && Objects.equals(deactivationDateTime, that.deactivationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registerUser, registerDateTime, lastUpdateUser, lastUpdateDateTime, activationUser, activationDateTime, deactivationUser, deactivationDateTime);
    }
}