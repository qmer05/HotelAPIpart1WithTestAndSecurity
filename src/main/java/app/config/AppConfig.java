package app.config;

import app.controller.ExceptionController;
import app.errorhandling.ErrorHandling;
import app.exception.ApiException;
import app.routes.Routes;
import app.util.ApiProps;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppConfig {

    private static Logger log = LoggerFactory.getLogger(AppConfig.class);
    private static final Routes routes = new Routes();
    private static final ExceptionController exceptionController = new ExceptionController();

    private static void configuration(JavalinConfig config){
        config.router.contextPath = ApiProps.API_CONTEXT;

        config.bundledPlugins.enableRouteOverview("/routes");
        config.bundledPlugins.enableDevLogging();

        config.router.apiBuilder(routes.getApiRoutes());
    }

    public static void startServer() {
        var app = Javalin.create(AppConfig::configuration);
        ErrorHandling.notFound(app);
        exceptionContext(app);
        app.start(ApiProps.PORT);
    }

    // == exception ==
    private static void exceptionContext(Javalin app){
        app.exception(ApiException.class, exceptionController::apiExceptionHandler);
    }


}

