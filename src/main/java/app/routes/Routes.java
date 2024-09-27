package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final HotelRoute hotelRoute = new HotelRoute();

    public EndpointGroup getApiRoutes(){
        return () -> {
            path("/", hotelRoute.getHotelRoutes());
        };
    }

}
