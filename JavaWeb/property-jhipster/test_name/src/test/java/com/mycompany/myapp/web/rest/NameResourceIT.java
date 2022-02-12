package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Name;
import com.mycompany.myapp.domain.enumeration.Title;
import com.mycompany.myapp.repository.NameRepository;
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
 * Integration tests for the {@link NameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NameResourceIT {

    private static final Title DEFAULT_TITLE = Title.Mr;
    private static final Title UPDATED_TITLE = Title.Mrs;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/names";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNameMockMvc;

    private Name name;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Name createEntity(EntityManager em) {
        Name name = new Name()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .displayName(DEFAULT_DISPLAY_NAME);
        return name;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Name createUpdatedEntity(EntityManager em) {
        Name name = new Name()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME);
        return name;
    }

    @BeforeEach
    public void initTest() {
        name = createEntity(em);
    }

    @Test
    @Transactional
    void createName() throws Exception {
        int databaseSizeBeforeCreate = nameRepository.findAll().size();
        // Create the Name
        restNameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isCreated());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeCreate + 1);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testName.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testName.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testName.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testName.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void createNameWithExistingId() throws Exception {
        // Create the Name with an existing ID
        name.setId(1L);

        int databaseSizeBeforeCreate = nameRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = nameRepository.findAll().size();
        // set the field null
        name.setTitle(null);

        // Create the Name, which fails.

        restNameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nameRepository.findAll().size();
        // set the field null
        name.setFirstName(null);

        // Create the Name, which fails.

        restNameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nameRepository.findAll().size();
        // set the field null
        name.setLastName(null);

        // Create the Name, which fails.

        restNameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nameRepository.findAll().size();
        // set the field null
        name.setDisplayName(null);

        // Create the Name, which fails.

        restNameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNames() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get all the nameList
        restNameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(name.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)));
    }

    @Test
    @Transactional
    void getName() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get the name
        restNameMockMvc
            .perform(get(ENTITY_API_URL_ID, name.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(name.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME));
    }

    @Test
    @Transactional
    void getNonExistingName() throws Exception {
        // Get the name
        restNameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewName() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        int databaseSizeBeforeUpdate = nameRepository.findAll().size();

        // Update the name
        Name updatedName = nameRepository.findById(name.getId()).get();
        // Disconnect from session so that the updates on updatedName are not directly saved in db
        em.detach(updatedName);
        updatedName
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME);

        restNameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedName.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedName))
            )
            .andExpect(status().isOk());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testName.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testName.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testName.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testName.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void putNonExistingName() throws Exception {
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();
        name.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, name.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(name))
            )
            .andExpect(status().isBadRequest());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchName() throws Exception {
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();
        name.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(name))
            )
            .andExpect(status().isBadRequest());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamName() throws Exception {
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();
        name.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNameMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNameWithPatch() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        int databaseSizeBeforeUpdate = nameRepository.findAll().size();

        // Update the name using partial update
        Name partialUpdatedName = new Name();
        partialUpdatedName.setId(name.getId());

        partialUpdatedName.middleName(UPDATED_MIDDLE_NAME).displayName(UPDATED_DISPLAY_NAME);

        restNameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedName.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedName))
            )
            .andExpect(status().isOk());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testName.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testName.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testName.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testName.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void fullUpdateNameWithPatch() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        int databaseSizeBeforeUpdate = nameRepository.findAll().size();

        // Update the name using partial update
        Name partialUpdatedName = new Name();
        partialUpdatedName.setId(name.getId());

        partialUpdatedName
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME);

        restNameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedName.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedName))
            )
            .andExpect(status().isOk());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testName.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testName.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testName.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testName.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingName() throws Exception {
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();
        name.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, name.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(name))
            )
            .andExpect(status().isBadRequest());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchName() throws Exception {
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();
        name.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(name))
            )
            .andExpect(status().isBadRequest());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamName() throws Exception {
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();
        name.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNameMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteName() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        int databaseSizeBeforeDelete = nameRepository.findAll().size();

        // Delete the name
        restNameMockMvc
            .perform(delete(ENTITY_API_URL_ID, name.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
