package app.daos;

import app.config.Populator;
import app.config.HibernateConfig;
import app.entities.Hotel;
import app.entities.Room;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HotelDaoTest {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final HotelDao hotelDao = new HotelDao(emf);
    private static final Populator populator = new Populator(hotelDao, emf);

    private static Hotel h1, h2, h3;
    private static Room r1, r2, r3, r4, r5, r6;

    @BeforeEach
    void setUp() {
        List<Hotel> hotels = populator.populateHotels();
        h1 = hotels.get(0);
        h2 = hotels.get(1);
        h3 = hotels.get(2);
        r1 = h1.getRooms().get(0);
        r2 = h1.getRooms().get(1);
        r3 = h2.getRooms().get(0);
        r4 = h2.getRooms().get(1);
        r5 = h3.getRooms().get(0);
        r6 = h3.getRooms().get(1);
    }

    @AfterEach
    void tearDown() {
        populator.cleanUpRooms();
        populator.cleanUpHotels();
    }

    @Test
    void getAllRooms() {
        List<Room> rooms = hotelDao.getAllRooms();
        assertEquals(6, rooms.size());
        assertThat(rooms, containsInAnyOrder(r1, r2, r3, r4, r5, r6));
    }

    @Test
    void getAll() {
        List <Hotel> hotels = hotelDao.getAll();
        assertEquals(3, hotels.size());
        assertThat(hotels, hasItem(h2));
        assertThat(hotels, containsInAnyOrder(h1, h2, h3));
    }

    @Test
    void getById() {
        Hotel hotel = hotelDao.getById(3L);
        assertEquals(h3, hotel);
    }

    @Test
    void create() {
        Hotel hotel = hotelDao.create(new Hotel("Paradise Hotel", "Bahamas",
                List.of(new Room(203, 5430),
                        new Room(405, 7650),
                        new Room(506, 9870))));
        assertEquals(hotel, hotelDao.getById(4L));
        assertEquals(3, hotel.getRooms().size());
    }

    @Test
    void update() {
        Hotel hotel = hotelDao.update(h2, new Hotel("Radisson Hotel", "New York", null));
        assertEquals(hotel, h2);
        assertEquals("Radisson Hotel", hotel.getHotelName());
        assertEquals("Radisson Hotel", hotelDao.getById(2L).getHotelName());
    }

    @Test
    void delete() {
        hotelDao.delete(1L);
        assertEquals(2, hotelDao.getAll().size());
        assertEquals(4, hotelDao.getAllRooms().size());
    }
}