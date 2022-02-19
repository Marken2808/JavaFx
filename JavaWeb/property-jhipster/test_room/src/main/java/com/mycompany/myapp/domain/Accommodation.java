package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.AccommodationStatus;
import com.mycompany.myapp.domain.enumeration.AccommodationType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Accommodation.
 */
@Entity
@Table(name = "accommodation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Accommodation implements Serializable {

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
    private AccommodationType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccommodationStatus status;

    @NotNull
    @Column(name = "acreage", nullable = false)
    private Double acreage;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "price")
    private Double price;

    @ManyToMany
    @JoinTable(
        name = "rel_accommodation__room",
        joinColumns = @JoinColumn(name = "accommodation_id"),
        inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accommodations" }, allowSetters = true)
    private Set<Room> rooms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Accommodation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Accommodation title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AccommodationType getType() {
        return this.type;
    }

    public Accommodation type(AccommodationType type) {
        this.setType(type);
        return this;
    }

    public void setType(AccommodationType type) {
        this.type = type;
    }

    public AccommodationStatus getStatus() {
        return this.status;
    }

    public Accommodation status(AccommodationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
    }

    public Double getAcreage() {
        return this.acreage;
    }

    public Accommodation acreage(Double acreage) {
        this.setAcreage(acreage);
        return this;
    }

    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Accommodation image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Accommodation imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Double getPrice() {
        return this.price;
    }

    public Accommodation price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Accommodation rooms(Set<Room> rooms) {
        this.setRooms(rooms);
        return this;
    }

    public Accommodation addRoom(Room room) {
        this.rooms.add(room);
        room.getAccommodations().add(this);
        return this;
    }

    public Accommodation removeRoom(Room room) {
        this.rooms.remove(room);
        room.getAccommodations().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accommodation)) {
            return false;
        }
        return id != null && id.equals(((Accommodation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accommodation{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", acreage=" + getAcreage() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
