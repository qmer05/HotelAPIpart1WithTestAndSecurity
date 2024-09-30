package app.routes;

import app.config.HibernateConfig;
import app.controller.RoomController;
import app.dao.HotelDao;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RoomRoute {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final HotelDao hotelDao = new HotelDao(emf);
    private final RoomController roomController = new RoomController(hotelDao);

    public EndpointGroup getRoomRoutes() {
        return () -> {
            get("/hotel/{id}/rooms", roomController::getAllRooms);
        };
    }

}
