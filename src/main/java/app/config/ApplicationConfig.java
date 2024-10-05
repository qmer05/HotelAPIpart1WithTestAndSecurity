package app.config;

import app.controllers.ExceptionController;
import app.errorhandling.ErrorHandling;
import app.security.exceptions.ApiException;
import app.routes.Routes;
import app.security.controller.AccessController;
import app.security.controller.SecurityController;
import app.security.routes.SecurityRoutes;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import app.utils.Utils;
import io.javalin.http.Context;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ApplicationConfig {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("hotel");
    private static Routes routes = new Routes(emf);
    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();
    private static SecurityController securityController = SecurityController.getInstance();
    private static AccessController accessController = new AccessController();
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private static final ExceptionController exceptionController = new ExceptionController();

    private static void configuration(JavalinConfig config) {
        config.showJavalinBanner = false;
        config.router.contextPath = "/hotels"; // base path for all routes
        config.bundledPlugins.enableRouteOverview("/routes");
        config.bundledPlugins.enableDevLogging();
        config.router.apiBuilder(routes.getApiRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
    }

    public static Javalin startServer(int port, EntityManagerFactory emf) {
        routes = new Routes(emf);
        Javalin app = Javalin.create(ApplicationConfig::configuration);
        app.beforeMatched(accessController::accessHandler);
        app.exception(Exception.class, ApplicationConfig::generalExceptionHandler);
        app.exception(ApiException.class, ApplicationConfig::apiExceptionHandler);
        ErrorHandling.notFound(app);
        app.start(port);
        return app;
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }

    private static void generalExceptionHandler(Exception e, Context ctx) {
        logger.error("An unhandled exception occurred", e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "error", e.getMessage()));
    }

    public static void apiExceptionHandler(ApiException e, Context ctx) {
        ctx.status(e.getCode());
        logger.warn("An API exception occurred: Code: {}, Message: {}", e.getCode(), e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "warning", e.getMessage()));
    }

}

