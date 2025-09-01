package co.com.pragma.model.reporte.gateways;

import co.com.pragma.model.reporte.Reporte;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ReporteRepository {
    Mono<Reporte> mostrarCantidadPrestamosAprobados();
    Mono<Void> incrementarPrestamosAprobados(String id);
    Mono<Void> incrementarMontoTotalPrestamosAprobados(String id, BigDecimal monto);
}
