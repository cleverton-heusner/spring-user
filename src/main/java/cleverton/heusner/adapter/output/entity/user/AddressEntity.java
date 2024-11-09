package cleverton.heusner.adapter.output.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class AddressEntity {

    @Column(nullable = false, length = 8)
    private String zipCode;

    @Column(length = 2)
    private String state;

    @Column(length = 20)
    private String city;

    @Column(length = 30)
    private String neighborhood;

    @Column(length = 30)
    private String street;

    @Column(length = 30)
    private String complement;

    @Column(nullable = false, length = 5)
    private String number;
}