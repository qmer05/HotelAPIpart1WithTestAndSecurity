package app.config;

import app.daos.HotelDao;
import app.entities.Hotel;
import app.entities.Room;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;


public class Populator {

    private static HotelDao hotelDao;
    private static EntityManagerFactory emf;

    public Populator(HotelDao hotelDao, EntityManagerFactory emf) {
        this.hotelDao = hotelDao;
        this.emf = emf;
    }

    public List<Hotel> populateHotels() {

        Room r1 = new Room(101, 150);
        Room r2 = new Room(102, 300);
        Room r3 = new Room(103, 450);
        Room r4 = new Room(104, 700);
        Room r5 = new Room(105, 200);
        Room r6 = new Room(106, 1000);
        List<Room> rooms1 = new ArrayList<>();
        rooms1.add(r1);
        rooms1.add(r2);
        List<Room> rooms2 = new ArrayList<>();
        rooms2.add(r3);
        rooms2.add(r4);
        List<Room> rooms3 = new ArrayList<>();
        rooms3.add(r5);
        rooms3.add(r6);

        Hotel hotel1 = new Hotel("The Enchanted Castle", "123 Fairy Tale Lane", rooms1);
        Hotel hotel2 = new Hotel("Oceanic Bliss", "456 Coral Reef Ave", rooms2);
        Hotel hotel3 = new Hotel("Celestial Resort", "789 Milky Way Blvd", rooms3);

        hotel1.addRoom(r1);
        hotel1.addRoom(r2);
        hotel2.addRoom(r3);
        hotel2.addRoom(r4);
        hotel3.addRoom(r5);
        hotel3.addRoom(r6);

        hotelDao.create(hotel1);
        hotelDao.create(hotel2);
        hotelDao.create(hotel3);

        return hotelDao.getAll();
    }

    public void cleanUpHotels() {
        // Delete all data from database
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Hotel").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE hotels_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanUpRooms() {
        // Delete all data from database
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Room").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE rooms_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
