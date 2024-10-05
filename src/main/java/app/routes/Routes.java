package app.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final HotelRoutes hotelRoutes;
    private final RoomRoutes roomRoutes;

    public Routes(EntityManagerFactory emf){
        hotelRoutes = new HotelRoutes(emf);
        roomRoutes = new RoomRoutes(emf);
    }

    public EndpointGroup getApiRoutes() {
        return () -> {
            path("/", hotelRoutes.getHotelRoutes());
            path("/", roomRoutes.getRoomRoutes());
        };
    }

}
