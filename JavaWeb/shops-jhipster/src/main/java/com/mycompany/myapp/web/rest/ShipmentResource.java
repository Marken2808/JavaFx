package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Shipment;
import com.mycompany.myapp.repository.ShipmentRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Shipment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ShipmentResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentResource.class);

    private static final String ENTITY_NAME = "shipment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipmentRepository shipmentRepository;

    public ShipmentResource(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    /**
     * {@code POST  /shipments} : Create a new shipment.
     *
     * @param shipment the shipment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipment, or with status {@code 400 (Bad Request)} if the shipment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipments")
    public ResponseEntity<Shipment> createShipment(@Valid @RequestBody Shipment shipment) throws URISyntaxException {
        log.debug("REST request to save Shipment : {}", shipment);
        if (shipment.getId() != null) {
            throw new BadRequestAlertException("A new shipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Shipment result = shipmentRepository.save(shipment);
        return ResponseEntity
            .created(new URI("/api/shipments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipments/:id} : Updates an existing shipment.
     *
     * @param id the id of the shipment to save.
     * @param shipment the shipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipment,
     * or with status {@code 400 (Bad Request)} if the shipment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shipments/{id}")
    public ResponseEntity<Shipment> updateShipment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Shipment shipment
    ) throws URISyntaxException {
        log.debug("REST request to update Shipment : {}, {}", id, shipment);
        if (shipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Shipment result = shipmentRepository.save(shipment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shipments/:id} : Partial updates given fields of an existing shipment, field will ignore if it is null
     *
     * @param id the id of the shipment to save.
     * @param shipment the shipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipment,
     * or with status {@code 400 (Bad Request)} if the shipment is not valid,
     * or with status {@code 404 (Not Found)} if the shipment is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shipments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Shipment> partialUpdateShipment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Shipment shipment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Shipment partially : {}, {}", id, shipment);
        if (shipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Shipment> result = shipmentRepository
            .findById(shipment.getId())
            .map(existingShipment -> {
                if (shipment.getTrackingCode() != null) {
                    existingShipment.setTrackingCode(shipment.getTrackingCode());
                }
                if (shipment.getDate() != null) {
                    existingShipment.setDate(shipment.getDate());
                }
                if (shipment.getDetails() != null) {
                    existingShipment.setDetails(shipment.getDetails());
                }

                return existingShipment;
            })
            .map(shipmentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipment.getId().toString())
        );
    }

    /**
     * {@code GET  /shipments} : get all the shipments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipments in body.
     */
    @GetMapping("/shipments")
    public ResponseEntity<List<Shipment>> getAllShipments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Shipments");
        Page<Shipment> page = shipmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shipments/:id} : get the "id" shipment.
     *
     * @param id the id of the shipment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipments/{id}")
    public ResponseEntity<Shipment> getShipment(@PathVariable Long id) {
        log.debug("REST request to get Shipment : {}", id);
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shipment);
    }

    /**
     * {@code DELETE  /shipments/:id} : delete the "id" shipment.
     *
     * @param id the id of the shipment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipments/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        log.debug("REST request to delete Shipment : {}", id);
        shipmentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
