package co.com.pragma.usecase.reporte.in;

import co.com.pragma.model.reporte.Reporte;

import reactor.core.publisher.Mono;

public interface ObtenerReporte {
    Mono<Reporte> obtenerReporte();
}
