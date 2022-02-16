package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.PropertyStatus;
import com.mycompany.myapp.domain.enumeration.PropertyType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PropertyType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PropertyStatus status;

    @Column(name = "is_urgent")
    private Boolean isUrgent;

    @JsonIgnoreProperties(value = { "customers" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Address address;

    @JsonIgnoreProperties(value = { "rooms" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Accommodation accommodation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Property id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Property title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PropertyType getType() {
        return this.type;
    }

    public Property type(PropertyType type) {
        this.setType(type);
        return this;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public PropertyStatus getStatus() {
        return this.status;
    }

    public Property status(PropertyStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public Boolean getIsUrgent() {
        return this.isUrgent;
    }

    public Property isUrgent(Boolean isUrgent) {
        this.setIsUrgent(isUrgent);
        return this;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Property address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Accommodation getAccommodation() {
        return this.accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Property accommodation(Accommodation accommodation) {
        this.setAccommodation(accommodation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Property)) {
            return false;
        }
        return id != null && id.equals(((Property) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Property{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", isUrgent='" + getIsUrgent() + "'" +
            "}";
    }
}
