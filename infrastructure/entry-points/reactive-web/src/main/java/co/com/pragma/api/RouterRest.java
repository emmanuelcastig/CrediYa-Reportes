package co.com.pragma.api;

import co.com.pragma.model.reporte.Reporte;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/reportes",
                    beanClass = Handler.class,
                    beanMethod = "obtenerReporte",
                    operation = @Operation(
                            operationId = "obtenerReporte",
                            summary = "Obtener reporte",
                            description = "Devuelve un reporte con estadísticas de préstamos aprobados",
                            tags = {"Reportes"},
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Reporte obtenido exitosamente",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = Reporte.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno del servidor",
                                            content = @Content(
                                                    mediaType = "application/json"
                                            )
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/v1/reportes"), handler::obtenerReporte);
    }
}
