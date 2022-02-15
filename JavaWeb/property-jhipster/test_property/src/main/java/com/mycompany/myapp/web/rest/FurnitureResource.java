package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Furniture;
import com.mycompany.myapp.repository.FurnitureRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Furniture}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FurnitureResource {

    private final Logger log = LoggerFactory.getLogger(FurnitureResource.class);

    private static final String ENTITY_NAME = "furniture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FurnitureRepository furnitureRepository;

    public FurnitureResource(FurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }

    /**
     * {@code POST  /furnitures} : Create a new furniture.
     *
     * @param furniture the furniture to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new furniture, or with status {@code 400 (Bad Request)} if the furniture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/furnitures")
    public ResponseEntity<Furniture> createFurniture(@Valid @RequestBody Furniture furniture) throws URISyntaxException {
        log.debug("REST request to save Furniture : {}", furniture);
        if (furniture.getId() != null) {
            throw new BadRequestAlertException("A new furniture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Furniture result = furnitureRepository.save(furniture);
        return ResponseEntity
            .created(new URI("/api/furnitures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /furnitures/:id} : Updates an existing furniture.
     *
     * @param id the id of the furniture to save.
     * @param furniture the furniture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated furniture,
     * or with status {@code 400 (Bad Request)} if the furniture is not valid,
     * or with status {@code 500 (Internal Server Error)} if the furniture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/furnitures/{id}")
    public ResponseEntity<Furniture> updateFurniture(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Furniture furniture
    ) throws URISyntaxException {
        log.debug("REST request to update Furniture : {}, {}", id, furniture);
        if (furniture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, furniture.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!furnitureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Furniture result = furnitureRepository.save(furniture);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, furniture.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /furnitures/:id} : Partial updates given fields of an existing furniture, field will ignore if it is null
     *
     * @param id the id of the furniture to save.
     * @param furniture the furniture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated furniture,
     * or with status {@code 400 (Bad Request)} if the furniture is not valid,
     * or with status {@code 404 (Not Found)} if the furniture is not found,
     * or with status {@code 500 (Internal Server Error)} if the furniture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/furnitures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Furniture> partialUpdateFurniture(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Furniture furniture
    ) throws URISyntaxException {
        log.debug("REST request to partial update Furniture partially : {}, {}", id, furniture);
        if (furniture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, furniture.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!furnitureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Furniture> result = furnitureRepository
            .findById(furniture.getId())
            .map(existingFurniture -> {
                if (furniture.getTitle() != null) {
                    existingFurniture.setTitle(furniture.getTitle());
                }

                return existingFurniture;
            })
            .map(furnitureRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, furniture.getId().toString())
        );
    }

    /**
     * {@code GET  /furnitures} : get all the furnitures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of furnitures in body.
     */
    @GetMapping("/furnitures")
    public List<Furniture> getAllFurnitures() {
        log.debug("REST request to get all Furnitures");
        return furnitureRepository.findAll();
    }

    /**
     * {@code GET  /furnitures/:id} : get the "id" furniture.
     *
     * @param id the id of the furniture to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the furniture, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/furnitures/{id}")
    public ResponseEntity<Furniture> getFurniture(@PathVariable Long id) {
        log.debug("REST request to get Furniture : {}", id);
        Optional<Furniture> furniture = furnitureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(furniture);
    }

    /**
     * {@code DELETE  /furnitures/:id} : delete the "id" furniture.
     *
     * @param id the id of the furniture to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/furnitures/{id}")
    public ResponseEntity<Void> deleteFurniture(@PathVariable Long id) {
        log.debug("REST request to delete Furniture : {}", id);
        furnitureRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
