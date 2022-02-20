package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Accommodation;
import com.mycompany.myapp.domain.Room;
import com.mycompany.myapp.domain.enumeration.AccommodationStatus;
import com.mycompany.myapp.domain.enumeration.AccommodationType;
import com.mycompany.myapp.repository.AccommodationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AccommodationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AccommodationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final AccommodationType DEFAULT_TYPE = AccommodationType.Flat;
    private static final AccommodationType UPDATED_TYPE = AccommodationType.House;

    private static final AccommodationStatus DEFAULT_STATUS = AccommodationStatus.Furnished;
    private static final AccommodationStatus UPDATED_STATUS = AccommodationStatus.Unfurnished;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final String ENTITY_API_URL = "/api/accommodations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Mock
    private AccommodationRepository accommodationRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccommodationMockMvc;

    private Accommodation accommodation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accommodation createEntity(EntityManager em) {
        Accommodation accommodation = new Accommodation()
            .title(DEFAULT_TITLE)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .total(DEFAULT_TOTAL);
        // Add required entity
        Room room;
        if (TestUtil.findAll(em, Room.class).isEmpty()) {
            room = RoomResourceIT.createEntity(em);
            em.persist(room);
            em.flush();
        } else {
            room = TestUtil.findAll(em, Room.class).get(0);
        }
        accommodation.getRooms().add(room);
        return accommodation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accommodation createUpdatedEntity(EntityManager em) {
        Accommodation accommodation = new Accommodation()
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .total(UPDATED_TOTAL);
        // Add required entity
        Room room;
        if (TestUtil.findAll(em, Room.class).isEmpty()) {
            room = RoomResourceIT.createUpdatedEntity(em);
            em.persist(room);
            em.flush();
        } else {
            room = TestUtil.findAll(em, Room.class).get(0);
        }
        accommodation.getRooms().add(room);
        return accommodation;
    }

    @BeforeEach
    public void initTest() {
        accommodation = createEntity(em);
    }

    @Test
    @Transactional
    void createAccommodation() throws Exception {
        int databaseSizeBeforeCreate = accommodationRepository.findAll().size();
        // Create the Accommodation
        restAccommodationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accommodation)))
            .andExpect(status().isCreated());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeCreate + 1);
        Accommodation testAccommodation = accommodationList.get(accommodationList.size() - 1);
        assertThat(testAccommodation.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAccommodation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAccommodation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAccommodation.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    void createAccommodationWithExistingId() throws Exception {
        // Create the Accommodation with an existing ID
        accommodation.setId(1L);

        int databaseSizeBeforeCreate = accommodationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccommodationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accommodation)))
            .andExpect(status().isBadRequest());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = accommodationRepository.findAll().size();
        // set the field null
        accommodation.setTitle(null);

        // Create the Accommodation, which fails.

        restAccommodationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accommodation)))
            .andExpect(status().isBadRequest());

        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accommodationRepository.findAll().size();
        // set the field null
        accommodation.setType(null);

        // Create the Accommodation, which fails.

        restAccommodationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accommodation)))
            .andExpect(status().isBadRequest());

        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = accommodationRepository.findAll().size();
        // set the field null
        accommodation.setStatus(null);

        // Create the Accommodation, which fails.

        restAccommodationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accommodation)))
            .andExpect(status().isBadRequest());

        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccommodations() throws Exception {
        // Initialize the database
        accommodationRepository.saveAndFlush(accommodation);

        // Get all the accommodationList
        restAccommodationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accommodation.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAccommodationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(accommodationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAccommodationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(accommodationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAccommodationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(accommodationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAccommodationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(accommodationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getAccommodation() throws Exception {
        // Initialize the database
        accommodationRepository.saveAndFlush(accommodation);

        // Get the accommodation
        restAccommodationMockMvc
            .perform(get(ENTITY_API_URL_ID, accommodation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accommodation.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingAccommodation() throws Exception {
        // Get the accommodation
        restAccommodationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccommodation() throws Exception {
        // Initialize the database
        accommodationRepository.saveAndFlush(accommodation);

        int databaseSizeBeforeUpdate = accommodationRepository.findAll().size();

        // Update the accommodation
        Accommodation updatedAccommodation = accommodationRepository.findById(accommodation.getId()).get();
        // Disconnect from session so that the updates on updatedAccommodation are not directly saved in db
        em.detach(updatedAccommodation);
        updatedAccommodation.title(UPDATED_TITLE).type(UPDATED_TYPE).status(UPDATED_STATUS).total(UPDATED_TOTAL);

        restAccommodationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccommodation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccommodation))
            )
            .andExpect(status().isOk());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeUpdate);
        Accommodation testAccommodation = accommodationList.get(accommodationList.size() - 1);
        assertThat(testAccommodation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAccommodation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAccommodation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccommodation.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingAccommodation() throws Exception {
        int databaseSizeBeforeUpdate = accommodationRepository.findAll().size();
        accommodation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccommodationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accommodation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accommodation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccommodation() throws Exception {
        int databaseSizeBeforeUpdate = accommodationRepository.findAll().size();
        accommodation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccommodationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accommodation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccommodation() throws Exception {
        int databaseSizeBeforeUpdate = accommodationRepository.findAll().size();
        accommodation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccommodationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accommodation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccommodationWithPatch() throws Exception {
        // Initialize the database
        accommodationRepository.saveAndFlush(accommodation);

        int databaseSizeBeforeUpdate = accommodationRepository.findAll().size();

        // Update the accommodation using partial update
        Accommodation partialUpdatedAccommodation = new Accommodation();
        partialUpdatedAccommodation.setId(accommodation.getId());

        partialUpdatedAccommodation.title(UPDATED_TITLE).type(UPDATED_TYPE).status(UPDATED_STATUS).total(UPDATED_TOTAL);

        restAccommodationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccommodation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccommodation))
            )
            .andExpect(status().isOk());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeUpdate);
        Accommodation testAccommodation = accommodationList.get(accommodationList.size() - 1);
        assertThat(testAccommodation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAccommodation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAccommodation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccommodation.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateAccommodationWithPatch() throws Exception {
        // Initialize the database
        accommodationRepository.saveAndFlush(accommodation);

        int databaseSizeBeforeUpdate = accommodationRepository.findAll().size();

        // Update the accommodation using partial update
        Accommodation partialUpdatedAccommodation = new Accommodation();
        partialUpdatedAccommodation.setId(accommodation.getId());

        partialUpdatedAccommodation.title(UPDATED_TITLE).type(UPDATED_TYPE).status(UPDATED_STATUS).total(UPDATED_TOTAL);

        restAccommodationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccommodation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccommodation))
            )
            .andExpect(status().isOk());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeUpdate);
        Accommodation testAccommodation = accommodationList.get(accommodationList.size() - 1);
        assertThat(testAccommodation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAccommodation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAccommodation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccommodation.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingAccommodation() throws Exception {
        int databaseSizeBeforeUpdate = accommodationRepository.findAll().size();
        accommodation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccommodationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accommodation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accommodation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccommodation() throws Exception {
        int databaseSizeBeforeUpdate = accommodationRepository.findAll().size();
        accommodation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccommodationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accommodation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccommodation() throws Exception {
        int databaseSizeBeforeUpdate = accommodationRepository.findAll().size();
        accommodation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccommodationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accommodation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accommodation in the database
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccommodation() throws Exception {
        // Initialize the database
        accommodationRepository.saveAndFlush(accommodation);

        int databaseSizeBeforeDelete = accommodationRepository.findAll().size();

        // Delete the accommodation
        restAccommodationMockMvc
            .perform(delete(ENTITY_API_URL_ID, accommodation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accommodation> accommodationList = accommodationRepository.findAll();
        assertThat(accommodationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
