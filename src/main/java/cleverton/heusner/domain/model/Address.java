package cleverton.heusner.domain.model;

public class Address {

    private boolean complete;
    private String zipCode;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String complement;
    private String number;

    public boolean isComplementBlank() {
        return complement == null || complement.isEmpty();
    }

    public boolean isComplete() {
        return complete;
    }

    public Address asComplete() {
        this.complete = true;
        return this;
    }

    public Address asIncomplete() {
        this.complete = false;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(final String complement) {
        this.complement = complement;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}