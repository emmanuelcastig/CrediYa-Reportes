package co.com.pragma.usecase.reporte;

import co.com.pragma.model.reporte.Reporte;
import co.com.pragma.model.reporte.gateways.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ReporteUseCaseTest {

    private ReporteRepository repository;
    private ReporteUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ReporteRepository.class);
        useCase = new ReporteUseCase(repository);
    }

    @Test
    void obtenerReporte_exito() {
        // Arrange
        Reporte reporte = new Reporte("1", 10);
        when(repository.mostrarCantidadPrestamosAprobados()).thenReturn(Mono.just(reporte));

        StepVerifier.create(useCase.obtenerReporte())
                .expectNext(reporte) // esperamos que devuelva el mismo reporte
                .verifyComplete();

        verify(repository, times(1)).mostrarCantidadPrestamosAprobados();
    }

    @Test
    void obtenerReporte_error() {
        RuntimeException ex = new RuntimeException("Error en DynamoDB");
        when(repository.mostrarCantidadPrestamosAprobados()).thenReturn(Mono.error(ex));

        StepVerifier.create(useCase.obtenerReporte())
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Error en DynamoDB"))
                .verify();

        verify(repository, times(1)).mostrarCantidadPrestamosAprobados();
    }
}
