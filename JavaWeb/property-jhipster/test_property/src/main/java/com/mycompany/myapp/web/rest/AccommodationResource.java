package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Accommodation;
import com.mycompany.myapp.repository.AccommodationRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Accommodation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AccommodationResource {

    private final Logger log = LoggerFactory.getLogger(AccommodationResource.class);

    private static final String ENTITY_NAME = "accommodation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccommodationRepository accommodationRepository;

    public AccommodationResource(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    /**
     * {@code POST  /accommodations} : Create a new accommodation.
     *
     * @param accommodation the accommodation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accommodation, or with status {@code 400 (Bad Request)} if the accommodation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accommodations")
    public ResponseEntity<Accommodation> createAccommodation(@Valid @RequestBody Accommodation accommodation) throws URISyntaxException {
        log.debug("REST request to save Accommodation : {}", accommodation);
        if (accommodation.getId() != null) {
            throw new BadRequestAlertException("A new accommodation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Accommodation result = accommodationRepository.save(accommodation);
        return ResponseEntity
            .created(new URI("/api/accommodations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accommodations/:id} : Updates an existing accommodation.
     *
     * @param id the id of the accommodation to save.
     * @param accommodation the accommodation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accommodation,
     * or with status {@code 400 (Bad Request)} if the accommodation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accommodation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accommodations/{id}")
    public ResponseEntity<Accommodation> updateAccommodation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Accommodation accommodation
    ) throws URISyntaxException {
        log.debug("REST request to update Accommodation : {}, {}", id, accommodation);
        if (accommodation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accommodation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accommodationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Accommodation result = accommodationRepository.save(accommodation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accommodation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /accommodations/:id} : Partial updates given fields of an existing accommodation, field will ignore if it is null
     *
     * @param id the id of the accommodation to save.
     * @param accommodation the accommodation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accommodation,
     * or with status {@code 400 (Bad Request)} if the accommodation is not valid,
     * or with status {@code 404 (Not Found)} if the accommodation is not found,
     * or with status {@code 500 (Internal Server Error)} if the accommodation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/accommodations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Accommodation> partialUpdateAccommodation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Accommodation accommodation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Accommodation partially : {}, {}", id, accommodation);
        if (accommodation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accommodation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accommodationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Accommodation> result = accommodationRepository
            .findById(accommodation.getId())
            .map(existingAccommodation -> {
                if (accommodation.getTitle() != null) {
                    existingAccommodation.setTitle(accommodation.getTitle());
                }
                if (accommodation.getType() != null) {
                    existingAccommodation.setType(accommodation.getType());
                }
                if (accommodation.getStatus() != null) {
                    existingAccommodation.setStatus(accommodation.getStatus());
                }

                return existingAccommodation;
            })
            .map(accommodationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accommodation.getId().toString())
        );
    }

    /**
     * {@code GET  /accommodations} : get all the accommodations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accommodations in body.
     */
    @GetMapping("/accommodations")
    public List<Accommodation> getAllAccommodations() {
        log.debug("REST request to get all Accommodations");
        return accommodationRepository.findAll();
    }

    /**
     * {@code GET  /accommodations/:id} : get the "id" accommodation.
     *
     * @param id the id of the accommodation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accommodation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accommodations/{id}")
    public ResponseEntity<Accommodation> getAccommodation(@PathVariable Long id) {
        log.debug("REST request to get Accommodation : {}", id);
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(accommodation);
    }

    /**
     * {@code DELETE  /accommodations/:id} : delete the "id" accommodation.
     *
     * @param id the id of the accommodation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accommodations/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        log.debug("REST request to delete Accommodation : {}", id);
        accommodationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
