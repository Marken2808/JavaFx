package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.RoomSize;
import com.mycompany.myapp.domain.enumeration.RoomType;
import java.io.Serializable;
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
    @Column(name = "r_title", nullable = false)
    private String rTitle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "r_type", nullable = false)
    private RoomType rType;

    @NotNull
    @Column(name = "r_acreage", nullable = false)
    private Double rAcreage;

    @Enumerated(EnumType.STRING)
    @Column(name = "r_size")
    private RoomSize rSize;

    @Lob
    @Column(name = "r_image")
    private byte[] rImage;

    @Column(name = "r_image_content_type")
    private String rImageContentType;

    @Column(name = "r_price")
    private Double rPrice;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "property" }, allowSetters = true)
    private Accommodation accommodation;

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

    public String getrTitle() {
        return this.rTitle;
    }

    public Room rTitle(String rTitle) {
        this.setrTitle(rTitle);
        return this;
    }

    public void setrTitle(String rTitle) {
        this.rTitle = rTitle;
    }

    public RoomType getrType() {
        return this.rType;
    }

    public Room rType(RoomType rType) {
        this.setrType(rType);
        return this;
    }

    public void setrType(RoomType rType) {
        this.rType = rType;
    }

    public Double getrAcreage() {
        return this.rAcreage;
    }

    public Room rAcreage(Double rAcreage) {
        this.setrAcreage(rAcreage);
        return this;
    }

    public void setrAcreage(Double rAcreage) {
        this.rAcreage = rAcreage;
    }

    public RoomSize getrSize() {
        return this.rSize;
    }

    public Room rSize(RoomSize rSize) {
        this.setrSize(rSize);
        return this;
    }

    public void setrSize(RoomSize rSize) {
        this.rSize = rSize;
    }

    public byte[] getrImage() {
        return this.rImage;
    }

    public Room rImage(byte[] rImage) {
        this.setrImage(rImage);
        return this;
    }

    public void setrImage(byte[] rImage) {
        this.rImage = rImage;
    }

    public String getrImageContentType() {
        return this.rImageContentType;
    }

    public Room rImageContentType(String rImageContentType) {
        this.rImageContentType = rImageContentType;
        return this;
    }

    public void setrImageContentType(String rImageContentType) {
        this.rImageContentType = rImageContentType;
    }

    public Double getrPrice() {
        return this.rPrice;
    }

    public Room rPrice(Double rPrice) {
        this.setrPrice(rPrice);
        return this;
    }

    public void setrPrice(Double rPrice) {
        this.rPrice = rPrice;
    }

    public Accommodation getAccommodation() {
        return this.accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Room accommodation(Accommodation accommodation) {
        this.setAccommodation(accommodation);
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
            ", rTitle='" + getrTitle() + "'" +
            ", rType='" + getrType() + "'" +
            ", rAcreage=" + getrAcreage() +
            ", rSize='" + getrSize() + "'" +
            ", rImage='" + getrImage() + "'" +
            ", rImageContentType='" + getrImageContentType() + "'" +
            ", rPrice=" + getrPrice() +
            "}";
    }
}
