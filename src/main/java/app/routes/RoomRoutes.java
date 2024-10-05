package app.routes;

import app.controllers.RoomController;
import app.daos.HotelDao;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RoomRoutes {

    private final RoomController roomController;

    public RoomRoutes(EntityManagerFactory emf) {
        roomController = new RoomController(new HotelDao(emf));
    }

    public EndpointGroup getRoomRoutes() {
        return () -> {
            get("/hotel/{id}/rooms", roomController::getAllRooms);
        };
    }

}
