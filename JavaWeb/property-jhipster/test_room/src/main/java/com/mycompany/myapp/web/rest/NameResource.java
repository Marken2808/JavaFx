package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Name;
import com.mycompany.myapp.repository.NameRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Name}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NameResource {

    private final Logger log = LoggerFactory.getLogger(NameResource.class);

    private static final String ENTITY_NAME = "name";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NameRepository nameRepository;

    public NameResource(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    /**
     * {@code POST  /names} : Create a new name.
     *
     * @param name the name to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new name, or with status {@code 400 (Bad Request)} if the name has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/names")
    public ResponseEntity<Name> createName(@Valid @RequestBody Name name) throws URISyntaxException {
        log.debug("REST request to save Name : {}", name);
        if (name.getId() != null) {
            throw new BadRequestAlertException("A new name cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Name result = nameRepository.save(name);
        return ResponseEntity
            .created(new URI("/api/names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /names/:id} : Updates an existing name.
     *
     * @param id the id of the name to save.
     * @param name the name to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated name,
     * or with status {@code 400 (Bad Request)} if the name is not valid,
     * or with status {@code 500 (Internal Server Error)} if the name couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/names/{id}")
    public ResponseEntity<Name> updateName(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Name name)
        throws URISyntaxException {
        log.debug("REST request to update Name : {}, {}", id, name);
        if (name.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, name.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Name result = nameRepository.save(name);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, name.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /names/:id} : Partial updates given fields of an existing name, field will ignore if it is null
     *
     * @param id the id of the name to save.
     * @param name the name to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated name,
     * or with status {@code 400 (Bad Request)} if the name is not valid,
     * or with status {@code 404 (Not Found)} if the name is not found,
     * or with status {@code 500 (Internal Server Error)} if the name couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/names/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Name> partialUpdateName(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Name name
    ) throws URISyntaxException {
        log.debug("REST request to partial update Name partially : {}, {}", id, name);
        if (name.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, name.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Name> result = nameRepository
            .findById(name.getId())
            .map(existingName -> {
                if (name.getTitle() != null) {
                    existingName.setTitle(name.getTitle());
                }
                if (name.getFirstName() != null) {
                    existingName.setFirstName(name.getFirstName());
                }
                if (name.getMiddleName() != null) {
                    existingName.setMiddleName(name.getMiddleName());
                }
                if (name.getLastName() != null) {
                    existingName.setLastName(name.getLastName());
                }
                if (name.getDisplayName() != null) {
                    existingName.setDisplayName(name.getDisplayName());
                }

                return existingName;
            })
            .map(nameRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, name.getId().toString())
        );
    }

    /**
     * {@code GET  /names} : get all the names.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of names in body.
     */
    @GetMapping("/names")
    public List<Name> getAllNames() {
        log.debug("REST request to get all Names");
        return nameRepository.findAll();
    }

    /**
     * {@code GET  /names/:id} : get the "id" name.
     *
     * @param id the id of the name to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the name, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/names/{id}")
    public ResponseEntity<Name> getName(@PathVariable Long id) {
        log.debug("REST request to get Name : {}", id);
        Optional<Name> name = nameRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(name);
    }

    /**
     * {@code DELETE  /names/:id} : delete the "id" name.
     *
     * @param id the id of the name to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/names/{id}")
    public ResponseEntity<Void> deleteName(@PathVariable Long id) {
        log.debug("REST request to delete Name : {}", id);
        nameRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
