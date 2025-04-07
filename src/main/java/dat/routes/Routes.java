package dat.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.controllers.SkiLessonController;
import dat.controllers.SecurityController;
import dat.enums.Roles;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes
{
    private final SkiLessonController skiLessonController;
    private final SecurityController securityController;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public Routes(SkiLessonController skiLessonController, SecurityController securityController)
    {
        this.skiLessonController = skiLessonController;
        this.securityController = securityController;
    }

    public  EndpointGroup getRoutes()
    {
        return () -> {
            path("skilesson", SkiLessonRoutes());
            path("auth", authRoutes());
            path("protected", protectedRoutes());
        };
    }

    private  EndpointGroup SkiLessonRoutes()
    {
        return () -> {
            get(skiLessonController::getAll); // Tested
            get("/level", skiLessonController::filterByLevel);
            get("/stats", skiLessonController::getInstructorStats);
            get("/{id}", skiLessonController::getById); // Tested
            get("/instructor/{id}/overview", skiLessonController::getSkiLessonsByInstructor);
            post(skiLessonController::create); // Tested
            put("/{lessonId}/instructor/{instructorId}", skiLessonController::addInstructor); // Tested
            patch("/{id}", skiLessonController::update); // Tested
            delete("/{id}", skiLessonController::delete); // Tested
            post("/populate", skiLessonController::populate); // NO NEED FOR TESTING
        };
    }

    private  EndpointGroup authRoutes()
    {
        return () -> {
            get("/test", ctx->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from Open")), Roles.ANYONE);
            get("/healthcheck", securityController::healthCheck, Roles.ANYONE);
            post("/login", securityController::login, Roles.ANYONE);
            post("/register", securityController::register, Roles.ANYONE);
            get("/verify", securityController::verify , Roles.ANYONE);
            get("/tokenlifespan", securityController::timeToLive , Roles.ANYONE);
        };
    }

    private  EndpointGroup protectedRoutes()
    {
        return () -> {
            get("/user_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from USER Protected")), Roles.USER);
            get("/admin_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from ADMIN Protected")), Roles.ADMIN);
        };
    }

}
