package cleverton.heusner.domain.model;

import cleverton.heusner.shared.model.UserAuditingData;

import java.time.LocalDate;

public class User {

    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private Address address;
    private boolean active;
    private UserAuditingData userAuditingData;

    public boolean isBirthDateInFuture() {
        return birthDate.isAfter(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserAuditingData getUserAuditingData() {
        return userAuditingData;
    }

    public void setUserAuditingData(UserAuditingData userAuditingData) {
        this.userAuditingData = userAuditingData;
    }
}