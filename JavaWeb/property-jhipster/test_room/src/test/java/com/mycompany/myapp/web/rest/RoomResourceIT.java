package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Accommodation;
import com.mycompany.myapp.domain.Room;
import com.mycompany.myapp.domain.enumeration.RoomSize;
import com.mycompany.myapp.domain.enumeration.RoomType;
import com.mycompany.myapp.repository.RoomRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RoomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomResourceIT {

    private static final String DEFAULT_R_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_R_TITLE = "BBBBBBBBBB";

    private static final RoomType DEFAULT_R_TYPE = RoomType.Attic;
    private static final RoomType UPDATED_R_TYPE = RoomType.Lounge;

    private static final Double DEFAULT_R_ACREAGE = 1D;
    private static final Double UPDATED_R_ACREAGE = 2D;

    private static final RoomSize DEFAULT_R_SIZE = RoomSize.Single_room;
    private static final RoomSize UPDATED_R_SIZE = RoomSize.Double_room;

    private static final byte[] DEFAULT_R_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_R_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_R_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_R_IMAGE_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_R_PRICE = 1D;
    private static final Double UPDATED_R_PRICE = 2D;

    private static final String ENTITY_API_URL = "/api/rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomMockMvc;

    private Room room;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createEntity(EntityManager em) {
        Room room = new Room()
            .rTitle(DEFAULT_R_TITLE)
            .rType(DEFAULT_R_TYPE)
            .rAcreage(DEFAULT_R_ACREAGE)
            .rSize(DEFAULT_R_SIZE)
            .rImage(DEFAULT_R_IMAGE)
            .rImageContentType(DEFAULT_R_IMAGE_CONTENT_TYPE)
            .rPrice(DEFAULT_R_PRICE);
        // Add required entity
        Accommodation accommodation;
        if (TestUtil.findAll(em, Accommodation.class).isEmpty()) {
            accommodation = AccommodationResourceIT.createEntity(em);
            em.persist(accommodation);
            em.flush();
        } else {
            accommodation = TestUtil.findAll(em, Accommodation.class).get(0);
        }
        room.setAccommodation(accommodation);
        return room;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createUpdatedEntity(EntityManager em) {
        Room room = new Room()
            .rTitle(UPDATED_R_TITLE)
            .rType(UPDATED_R_TYPE)
            .rAcreage(UPDATED_R_ACREAGE)
            .rSize(UPDATED_R_SIZE)
            .rImage(UPDATED_R_IMAGE)
            .rImageContentType(UPDATED_R_IMAGE_CONTENT_TYPE)
            .rPrice(UPDATED_R_PRICE);
        // Add required entity
        Accommodation accommodation;
        if (TestUtil.findAll(em, Accommodation.class).isEmpty()) {
            accommodation = AccommodationResourceIT.createUpdatedEntity(em);
            em.persist(accommodation);
            em.flush();
        } else {
            accommodation = TestUtil.findAll(em, Accommodation.class).get(0);
        }
        room.setAccommodation(accommodation);
        return room;
    }

    @BeforeEach
    public void initTest() {
        room = createEntity(em);
    }

    @Test
    @Transactional
    void createRoom() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();
        // Create the Room
        restRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate + 1);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getrTitle()).isEqualTo(DEFAULT_R_TITLE);
        assertThat(testRoom.getrType()).isEqualTo(DEFAULT_R_TYPE);
        assertThat(testRoom.getrAcreage()).isEqualTo(DEFAULT_R_ACREAGE);
        assertThat(testRoom.getrSize()).isEqualTo(DEFAULT_R_SIZE);
        assertThat(testRoom.getrImage()).isEqualTo(DEFAULT_R_IMAGE);
        assertThat(testRoom.getrImageContentType()).isEqualTo(DEFAULT_R_IMAGE_CONTENT_TYPE);
        assertThat(testRoom.getrPrice()).isEqualTo(DEFAULT_R_PRICE);
    }

    @Test
    @Transactional
    void createRoomWithExistingId() throws Exception {
        // Create the Room with an existing ID
        room.setId(1L);

        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkrTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setrTitle(null);

        // Create the Room, which fails.

        restRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkrTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setrType(null);

        // Create the Room, which fails.

        restRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkrAcreageIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setrAcreage(null);

        // Create the Room, which fails.

        restRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRooms() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList
        restRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
            .andExpect(jsonPath("$.[*].rTitle").value(hasItem(DEFAULT_R_TITLE)))
            .andExpect(jsonPath("$.[*].rType").value(hasItem(DEFAULT_R_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rAcreage").value(hasItem(DEFAULT_R_ACREAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].rSize").value(hasItem(DEFAULT_R_SIZE.toString())))
            .andExpect(jsonPath("$.[*].rImageContentType").value(hasItem(DEFAULT_R_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_R_IMAGE))))
            .andExpect(jsonPath("$.[*].rPrice").value(hasItem(DEFAULT_R_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get the room
        restRoomMockMvc
            .perform(get(ENTITY_API_URL_ID, room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(room.getId().intValue()))
            .andExpect(jsonPath("$.rTitle").value(DEFAULT_R_TITLE))
            .andExpect(jsonPath("$.rType").value(DEFAULT_R_TYPE.toString()))
            .andExpect(jsonPath("$.rAcreage").value(DEFAULT_R_ACREAGE.doubleValue()))
            .andExpect(jsonPath("$.rSize").value(DEFAULT_R_SIZE.toString()))
            .andExpect(jsonPath("$.rImageContentType").value(DEFAULT_R_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.rImage").value(Base64Utils.encodeToString(DEFAULT_R_IMAGE)))
            .andExpect(jsonPath("$.rPrice").value(DEFAULT_R_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingRoom() throws Exception {
        // Get the room
        restRoomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room
        Room updatedRoom = roomRepository.findById(room.getId()).get();
        // Disconnect from session so that the updates on updatedRoom are not directly saved in db
        em.detach(updatedRoom);
        updatedRoom
            .rTitle(UPDATED_R_TITLE)
            .rType(UPDATED_R_TYPE)
            .rAcreage(UPDATED_R_ACREAGE)
            .rSize(UPDATED_R_SIZE)
            .rImage(UPDATED_R_IMAGE)
            .rImageContentType(UPDATED_R_IMAGE_CONTENT_TYPE)
            .rPrice(UPDATED_R_PRICE);

        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoom.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getrTitle()).isEqualTo(UPDATED_R_TITLE);
        assertThat(testRoom.getrType()).isEqualTo(UPDATED_R_TYPE);
        assertThat(testRoom.getrAcreage()).isEqualTo(UPDATED_R_ACREAGE);
        assertThat(testRoom.getrSize()).isEqualTo(UPDATED_R_SIZE);
        assertThat(testRoom.getrImage()).isEqualTo(UPDATED_R_IMAGE);
        assertThat(testRoom.getrImageContentType()).isEqualTo(UPDATED_R_IMAGE_CONTENT_TYPE);
        assertThat(testRoom.getrPrice()).isEqualTo(UPDATED_R_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, room.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(room))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(room))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomWithPatch() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room using partial update
        Room partialUpdatedRoom = new Room();
        partialUpdatedRoom.setId(room.getId());

        partialUpdatedRoom.rType(UPDATED_R_TYPE).rAcreage(UPDATED_R_ACREAGE).rSize(UPDATED_R_SIZE).rPrice(UPDATED_R_PRICE);

        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getrTitle()).isEqualTo(DEFAULT_R_TITLE);
        assertThat(testRoom.getrType()).isEqualTo(UPDATED_R_TYPE);
        assertThat(testRoom.getrAcreage()).isEqualTo(UPDATED_R_ACREAGE);
        assertThat(testRoom.getrSize()).isEqualTo(UPDATED_R_SIZE);
        assertThat(testRoom.getrImage()).isEqualTo(DEFAULT_R_IMAGE);
        assertThat(testRoom.getrImageContentType()).isEqualTo(DEFAULT_R_IMAGE_CONTENT_TYPE);
        assertThat(testRoom.getrPrice()).isEqualTo(UPDATED_R_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateRoomWithPatch() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room using partial update
        Room partialUpdatedRoom = new Room();
        partialUpdatedRoom.setId(room.getId());

        partialUpdatedRoom
            .rTitle(UPDATED_R_TITLE)
            .rType(UPDATED_R_TYPE)
            .rAcreage(UPDATED_R_ACREAGE)
            .rSize(UPDATED_R_SIZE)
            .rImage(UPDATED_R_IMAGE)
            .rImageContentType(UPDATED_R_IMAGE_CONTENT_TYPE)
            .rPrice(UPDATED_R_PRICE);

        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getrTitle()).isEqualTo(UPDATED_R_TITLE);
        assertThat(testRoom.getrType()).isEqualTo(UPDATED_R_TYPE);
        assertThat(testRoom.getrAcreage()).isEqualTo(UPDATED_R_ACREAGE);
        assertThat(testRoom.getrSize()).isEqualTo(UPDATED_R_SIZE);
        assertThat(testRoom.getrImage()).isEqualTo(UPDATED_R_IMAGE);
        assertThat(testRoom.getrImageContentType()).isEqualTo(UPDATED_R_IMAGE_CONTENT_TYPE);
        assertThat(testRoom.getrPrice()).isEqualTo(UPDATED_R_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, room.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(room))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(room))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        // Delete the room
        restRoomMockMvc
            .perform(delete(ENTITY_API_URL_ID, room.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
