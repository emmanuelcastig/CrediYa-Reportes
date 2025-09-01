package co.com.pragma.dynamodb;

import co.com.pragma.model.reporte.gateways.ReporteRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqsPrestamosListener {

    private final ReporteRepository reporteRepository;

    @SqsListener("${spring.sqs.cola-reportes-crediYa}")
    public void onMessage(@Payload String mensajeJson) {
        log.info("Inicio actualizacion reporte desde SQS");
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(mensajeJson);
            String decision = json.get("decision").asText();

            log.info("decision recibida: {}", decision);

            if ("APROBADO".equals(decision) || "APROBADA".equals(decision)) {
                String id = "prestamos_aprobados";
                log.info("Incrementando prestamos aprobados para el reporte ID: {}", id);

                reporteRepository.incrementarPrestamosAprobados(id)
                        .subscribe(
                                unused -> log.info("Contador actualizado en DynamoDB"),
                                error -> log.error("Error actualizando contador: {}", error.getMessage(), error)
                        );
            }
        } catch (Exception e) {
            log.error("Error procesando mensaje de SQS reportes: {}", e.getMessage(), e);
        }
    }
}
