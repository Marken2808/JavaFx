package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Land;
import com.mycompany.myapp.repository.LandRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LandResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String ENTITY_API_URL = "/api/lands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LandRepository landRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLandMockMvc;

    private Land land;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Land createEntity(EntityManager em) {
        Land land = new Land().title(DEFAULT_TITLE).price(DEFAULT_PRICE);
        return land;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Land createUpdatedEntity(EntityManager em) {
        Land land = new Land().title(UPDATED_TITLE).price(UPDATED_PRICE);
        return land;
    }

    @BeforeEach
    public void initTest() {
        land = createEntity(em);
    }

    @Test
    @Transactional
    void createLand() throws Exception {
        int databaseSizeBeforeCreate = landRepository.findAll().size();
        // Create the Land
        restLandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(land)))
            .andExpect(status().isCreated());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeCreate + 1);
        Land testLand = landList.get(landList.size() - 1);
        assertThat(testLand.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLand.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createLandWithExistingId() throws Exception {
        // Create the Land with an existing ID
        land.setId(1L);

        int databaseSizeBeforeCreate = landRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(land)))
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = landRepository.findAll().size();
        // set the field null
        land.setTitle(null);

        // Create the Land, which fails.

        restLandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(land)))
            .andExpect(status().isBadRequest());

        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = landRepository.findAll().size();
        // set the field null
        land.setPrice(null);

        // Create the Land, which fails.

        restLandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(land)))
            .andExpect(status().isBadRequest());

        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLands() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        // Get all the landList
        restLandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(land.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getLand() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        // Get the land
        restLandMockMvc
            .perform(get(ENTITY_API_URL_ID, land.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(land.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingLand() throws Exception {
        // Get the land
        restLandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLand() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        int databaseSizeBeforeUpdate = landRepository.findAll().size();

        // Update the land
        Land updatedLand = landRepository.findById(land.getId()).get();
        // Disconnect from session so that the updates on updatedLand are not directly saved in db
        em.detach(updatedLand);
        updatedLand.title(UPDATED_TITLE).price(UPDATED_PRICE);

        restLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLand))
            )
            .andExpect(status().isOk());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
        Land testLand = landList.get(landList.size() - 1);
        assertThat(testLand.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLand.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, land.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(land))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(land))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(land)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLandWithPatch() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        int databaseSizeBeforeUpdate = landRepository.findAll().size();

        // Update the land using partial update
        Land partialUpdatedLand = new Land();
        partialUpdatedLand.setId(land.getId());

        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLand))
            )
            .andExpect(status().isOk());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
        Land testLand = landList.get(landList.size() - 1);
        assertThat(testLand.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLand.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateLandWithPatch() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        int databaseSizeBeforeUpdate = landRepository.findAll().size();

        // Update the land using partial update
        Land partialUpdatedLand = new Land();
        partialUpdatedLand.setId(land.getId());

        partialUpdatedLand.title(UPDATED_TITLE).price(UPDATED_PRICE);

        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLand))
            )
            .andExpect(status().isOk());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
        Land testLand = landList.get(landList.size() - 1);
        assertThat(testLand.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLand.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, land.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(land))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(land))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLand() throws Exception {
        int databaseSizeBeforeUpdate = landRepository.findAll().size();
        land.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(land)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Land in the database
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLand() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        int databaseSizeBeforeDelete = landRepository.findAll().size();

        // Delete the land
        restLandMockMvc
            .perform(delete(ENTITY_API_URL_ID, land.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Land> landList = landRepository.findAll();
        assertThat(landList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
