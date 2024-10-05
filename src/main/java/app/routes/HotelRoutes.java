package app.routes;

import app.controllers.HotelController;
import app.daos.HotelDao;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HotelRoutes {

    private final HotelController hotelController;

    public HotelRoutes(EntityManagerFactory emf) {
        hotelController = new HotelController(new HotelDao(emf));
    }

    public EndpointGroup getHotelRoutes() {
        return () -> {
            get("/", hotelController::getAllHotels);
            get("/hotel/{id}", hotelController::getHotelById);
            post("/hotel", hotelController::createHotel);
            put("/hotel/{id}", hotelController::updateHotel);
            delete("/hotel/{id}", hotelController::deleteHotel);
        };
    }

}
