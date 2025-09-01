package co.com.pragma.usecase.reporte;

import co.com.pragma.model.reporte.Reporte;
import co.com.pragma.model.reporte.gateways.ReporteRepository;
import co.com.pragma.usecase.reporte.in.ObtenerReporte;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReporteUseCase implements ObtenerReporte {

    private final ReporteRepository repository;

    @Override
    public Mono<Reporte> obtenerReporte() {
        return repository.mostrarCantidadPrestamosAprobados();
    }
}
