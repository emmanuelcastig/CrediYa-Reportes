package co.com.pragma.config;

import co.com.pragma.model.reporte.gateways.ReporteRepository;
import co.com.pragma.usecase.reporte.ReporteUseCase;
import co.com.pragma.usecase.reporte.in.ObtenerReporte;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "co.com.pragma.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {
        private final ReporteRepository reporteRepository;

        public UseCasesConfig(ReporteRepository reporteRepository) {
            this.reporteRepository = reporteRepository;
        }

        @Bean
        @Primary
        public ObtenerReporte obtenerReporte() {
                return new ReporteUseCase(reporteRepository);
        }
}
