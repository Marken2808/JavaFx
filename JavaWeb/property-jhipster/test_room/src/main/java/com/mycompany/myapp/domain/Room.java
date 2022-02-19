package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.RoomType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RoomType type;

    @ManyToMany(mappedBy = "rooms")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rooms" }, allowSetters = true)
    private Set<Accommodation> accommodations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Room id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Room title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RoomType getType() {
        return this.type;
    }

    public Room type(RoomType type) {
        this.setType(type);
        return this;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public Set<Accommodation> getAccommodations() {
        return this.accommodations;
    }

    public void setAccommodations(Set<Accommodation> accommodations) {
        if (this.accommodations != null) {
            this.accommodations.forEach(i -> i.removeRoom(this));
        }
        if (accommodations != null) {
            accommodations.forEach(i -> i.addRoom(this));
        }
        this.accommodations = accommodations;
    }

    public Room accommodations(Set<Accommodation> accommodations) {
        this.setAccommodations(accommodations);
        return this;
    }

    public Room addAccommodation(Accommodation accommodation) {
        this.accommodations.add(accommodation);
        accommodation.getRooms().add(this);
        return this;
    }

    public Room removeAccommodation(Accommodation accommodation) {
        this.accommodations.remove(accommodation);
        accommodation.getRooms().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        return id != null && id.equals(((Room) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
