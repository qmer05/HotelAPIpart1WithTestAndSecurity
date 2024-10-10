package app.routes;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.config.Populator;
import app.daos.HotelDao;
import app.dtos.HotelDto;
import app.dtos.RoomDto;
import app.entities.Hotel;
import app.entities.Room;
import app.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HotelRoutesTest {

    private Javalin app;
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private HotelDao hotelDao = new HotelDao(emf);
    Populator populator = new Populator(hotelDao, emf);

    private static String BASE_URL = "http://localhost:7000/hotels";

    private List<Hotel> hotels;
    private Hotel h1, h2, h3;
    private List<Room> rooms;
    private Room r1, r2, r3, r4, r5, r6;

    private UserDTO admin = new UserDTO("admin", "test123");

    @BeforeAll
    void init() {
        app = ApplicationConfig.startServer(7000, emf);
    }

    @BeforeEach
    void setUp() {
        hotels = populator.populateHotels();
        h1 = hotels.get(0);
        h2 = hotels.get(1);
        h3 = hotels.get(2);
        rooms = h1.getRooms();
        r1 = rooms.get(0);
        r2 = rooms.get(1);
        rooms = h2.getRooms();
        r3 = rooms.get(0);
        r4 = rooms.get(1);
        rooms = h3.getRooms();
        r5 = rooms.get(0);
        r6 = rooms.get(1);
    }

    @AfterEach
    void tearDown() {
        populator.cleanUpRooms();
        populator.cleanUpHotels();
    }

    @AfterAll
    void closeDown() {
        ApplicationConfig.stopServer(app);
    }

    public String getAdminToken() {
        return given()
                .contentType("application/json")
                .body(admin)
                .when()
                .post(BASE_URL + "/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    @Test
    void testGetRooms(){
        RoomDto[] rooms = given()
                .when()
                .get(BASE_URL + "/hotel/1/rooms")
                .then()
                .statusCode(200)
                .extract()
                .as(RoomDto[].class);

        assertThat(rooms, arrayWithSize(6));
        assertThat(rooms, arrayContainingInAnyOrder(new RoomDto(r1), new RoomDto(r2), new RoomDto(r3), new RoomDto(r4), new RoomDto(r5), new RoomDto(r6)));
    }

    @Test
    void testGetHotelById() {
        HotelDto hotelDto = given()
                .when()
                .get(BASE_URL + "/hotel/1")
                .then()
                .statusCode(200)
                .extract()
                .as(HotelDto.class);

        assertThat(hotelDto, equalTo(new HotelDto(h1)));
    }

    @Test
    void testGetAllHotels() {
        HotelDto[] hotels = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .as(HotelDto[].class);

        assertThat(hotels, arrayWithSize(3));
        assertThat(hotels, arrayContainingInAnyOrder(new HotelDto(h1), new HotelDto(h2), new HotelDto(h3)));
    }

    @Test
    void testCreateHotel() throws ValidationException {
        String token = getAdminToken();
        HotelDto hotelDto = new HotelDto("Dracula Blood Bath", "Horror Avenue 999", List.of(new RoomDto(430, 4204), new RoomDto(587, 3450)));
        HotelDto createdHotel = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(hotelDto)
                .when()
                .post(BASE_URL + "/hotel")
                .then()
                .statusCode(201)
                .extract()
                .as(HotelDto.class);

        assertThat(createdHotel, equalTo(hotelDto));
        assertThat(hotelDao.getAll(), hasSize(4));
    }

    @Test
    void deleteHotel() {
        given()
                .when()
                .delete(BASE_URL + "/hotel/3")
                .then()
                .statusCode(204);

        assertThat(hotelDao.getAll(), hasSize(2));
        assertThat(hotelDao.getById(3L), nullValue());
    }

    @Test
    void testUpdateHotel() {
        HotelDto hotelDto = new HotelDto("Spongebob Paradise", "Pineapple House Avenue 20", List.of(new RoomDto(654, 5687), new RoomDto(424, 8642)));
        HotelDto updatedHotel = given()
                .contentType("application/json")
                .body(hotelDto)
                .when()
                .put(BASE_URL + "/hotel/2")
                .then()
                .statusCode(200)
                .extract()
                .as(HotelDto.class);

        assertThat(updatedHotel, equalTo(hotelDto));
        assertThat(hotelDao.getById(2L).getHotelName(), equalTo("Spongebob Paradise"));

    }
}