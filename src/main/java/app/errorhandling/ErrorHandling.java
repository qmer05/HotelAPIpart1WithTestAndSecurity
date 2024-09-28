package app.errorhandling;

import app.exception.ApiException;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHandling {

    public static Logger log = LoggerFactory.getLogger(ErrorHandling.class);

    public static void notFound(Javalin app) {
        app.error(404, ctx -> {
            String path = ctx.path();
            log.error("404 Not Found: {}", path);
            // Check if the path matches /hotel/{id}
            if (!path.matches("/hotels/hotel/\\d+")) {
                throw new ApiException(404, "Not found");
            }
        });
    }
}
