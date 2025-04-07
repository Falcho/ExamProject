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
            path("skilesson", tripRoutes());
            path("auth", authRoutes());
            path("protected", protectedRoutes());
        };
    }

    private  EndpointGroup tripRoutes()
    {
        return () -> {
            get(skiLessonController::getAll);
            get("/level", skiLessonController::filterByLevel);
            get("/{id}", skiLessonController::getById);
            get("/instructor/{id}/overview", skiLessonController::getInstructorsOverview);
            post(skiLessonController::create);
            put("/{lessonId}/instructor/{instructorId}", skiLessonController::addInstructor);
            patch("/{id}", skiLessonController::update);
            delete("/{id}", skiLessonController::delete);
            post("/populate", skiLessonController::populate);
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
