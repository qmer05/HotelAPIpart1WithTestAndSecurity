package app.dao;

import app.model.Hotel;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class HotelDao extends DAO<Hotel>{

    private final EntityManagerFactory emf;

    public HotelDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Hotel> getAll() {
        try (var em = emf.createEntityManager()) {
            return em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
        }
    }

    public Hotel getById(long id) {
        try (var em = emf.createEntityManager()) {
            return em.find(Hotel.class, id);
        }
    }

    @Override
    public Hotel create(Hotel hotel) {
        try (var em = emf.createEntityManager()) {

            em.getTransaction().begin();
            em.persist(hotel);
            if (hotel.getRooms() != null) {
                for (var room : hotel.getRooms()) {
                    room.setHotel(hotel);
                    em.persist(room);
                }
            }
            em.getTransaction().commit();
        }
        return hotel;
    }

    @Override
    public Hotel update(Hotel hotel, Hotel updateHotel) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            hotel.setHotelName(updateHotel.getHotelName());
            hotel.setHotelAddress(updateHotel.getHotelAddress());

            // Update rooms
            if (updateHotel.getRooms() != null) {
                for (var room : updateHotel.getRooms()) {
                    room.setHotel(hotel);
                    hotel.getRooms().add(room);
                    em.persist(room);
                }
            }
            em.merge(hotel);
            em.getTransaction().commit();
        }
        return hotel;
    }

    public void delete(long id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, id);
            em.remove(hotel);
            em.getTransaction().commit();
        }
    }

    /*
    public List<Hotel> getAllHotels() {
        try (var em = emf.createEntityManager()) {
            return em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
        }
    }

    public Hotel getHotelById(long id) {
        try (var em = emf.createEntityManager()) {
            return em.find(Hotel.class, id);
        }
    }

    public void createHotel(Hotel hotel) {
        try (var em = emf.createEntityManager()) {

            em.getTransaction().begin();
            em.persist(hotel);
            if (hotel.getRooms() != null) {
                for (var room : hotel.getRooms()) {
                    room.setHotel(hotel);
                    em.persist(room);
                }
            }
            em.getTransaction().commit();
        }
    }

    public void updateHotel(Hotel hotel, Hotel updateHotel) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            hotel.setHotelName(updateHotel.getHotelName());
            hotel.setHotelAddress(updateHotel.getHotelAddress());

            // Update rooms
            if (updateHotel.getRooms() != null) {
                for (var room : updateHotel.getRooms()) {
                    room.setHotel(hotel);
                    hotel.getRooms().add(room);
                    em.persist(room);
                }
            }
            em.merge(hotel);
            em.getTransaction().commit();
        }
    }


    public void deleteHotel(long id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, id);
            em.remove(hotel);
            em.getTransaction().commit();
        }
    }

     */
}
