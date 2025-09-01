package co.com.pragma.api;

import co.com.pragma.usecase.reporte.ReporteUseCase;
import co.com.pragma.usecase.reporte.in.ObtenerReporte;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    private final ObtenerReporte obtenerReporte;

    public Mono<ServerResponse> obtenerReporte(ServerRequest serverRequest) {
        log.trace("Iniciando obtencion de reporte");
        return obtenerReporte.obtenerReporte()
                .doOnNext(reporte -> log.debug("Reporte obtenido: {}", reporte))
                .flatMap(reporte ->
                        ServerResponse.ok()
                                .bodyValue(reporte)
                                .doOnSuccess(resp -> log.trace("Respuesta enviada correctamente")))
                .doOnError(error -> log.error("Error al obtener reporte",error.getMessage(), error));
    }

}
