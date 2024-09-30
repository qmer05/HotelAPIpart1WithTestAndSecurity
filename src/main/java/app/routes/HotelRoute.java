package app.routes;

import app.config.HibernateConfig;
import app.controller.HotelController;
import app.dao.HotelDao;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HotelRoute {

    public final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    public final HotelDao hotelDao = new HotelDao(emf);
    public final HotelController hotelController = new HotelController(hotelDao);

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
