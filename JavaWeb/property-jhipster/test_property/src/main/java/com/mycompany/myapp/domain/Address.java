package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numberic")
    private String numberic;

    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "county")
    private String county;

    @Column(name = "city")
    private String city;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "address")
    @JsonIgnoreProperties(value = { "user", "name", "address" }, allowSetters = true)
    private Set<Customer> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberic() {
        return this.numberic;
    }

    public Address numberic(String numberic) {
        this.setNumberic(numberic);
        return this;
    }

    public void setNumberic(String numberic) {
        this.numberic = numberic;
    }

    public String getStreet() {
        return this.street;
    }

    public Address street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCounty() {
        return this.county;
    }

    public Address county(String county) {
        this.setCounty(county);
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return this.city;
    }

    public Address city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public Address postcode(String postcode) {
        this.setPostcode(postcode);
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return this.country;
    }

    public Address country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customer> customers) {
        if (this.customers != null) {
            this.customers.forEach(i -> i.setAddress(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setAddress(this));
        }
        this.customers = customers;
    }

    public Address customers(Set<Customer> customers) {
        this.setCustomers(customers);
        return this;
    }

    public Address addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setAddress(this);
        return this;
    }

    public Address removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setAddress(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", numberic='" + getNumberic() + "'" +
            ", street='" + getStreet() + "'" +
            ", county='" + getCounty() + "'" +
            ", city='" + getCity() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
