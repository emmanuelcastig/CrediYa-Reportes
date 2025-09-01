package co.com.pragma.dynamodb;

import co.com.pragma.dynamodb.helper.TemplateAdapterOperations;
import co.com.pragma.model.reporte.Reporte;
import co.com.pragma.model.reporte.gateways.ReporteRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class DynamoDBTemplateAdapter
        extends TemplateAdapterOperations<Reporte, String, ModelEntity>
        implements ReporteRepository {

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper, DynamoDbAsyncClient dynamoDbAsyncClient) {
        super(connectionFactory, mapper, d -> mapper.map(d, Reporte.class), "ReportePrestamos");
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
    }

    @Override
    public Mono<Reporte> mostrarCantidadPrestamosAprobados() {
        return Mono.fromFuture(
                table.getItem(r -> r.key(Key.builder()
                        .partitionValue("prestamos_aprobados")
                        .build()))
        ).map(entity -> Reporte.builder()
                .id(entity.getId())
                .cantidadPrestamosAprobados(entity.getCantidadPrestamosAprobados())
                .montoTotalPrestamosAprobados(entity.getMontoTotalPrestamosAprobados())
                .build());
    }

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    @Override
    public Mono<Void> incrementarPrestamosAprobados(String id) {
        Map<String, AttributeValue> key = Map.of("id", AttributeValue.builder().s(id).build());

        return Mono.fromFuture(
                dynamoDbAsyncClient.updateItem(builder -> builder
                        .tableName("ReportePrestamos")
                        .key(key)
                        .updateExpression("SET cantidadPrestamosAprobados = if_not_exists(cantidadPrestamosAprobados," +
                                " :zero) + :inc")
                        .expressionAttributeValues(Map.of(
                                ":inc", AttributeValue.builder().n("1").build(),
                                ":zero", AttributeValue.builder().n("0").build()
                        ))
                )
        ).then();
    }

    @Override
    public Mono<Void> incrementarMontoTotalPrestamosAprobados(String id, BigDecimal monto) {
        Map<String, AttributeValue> key = Map.of("id", AttributeValue.builder().s(id).build());

        return Mono.fromFuture(
                dynamoDbAsyncClient.updateItem(builder -> builder
                        .tableName("ReportePrestamos")
                        .key(key)
                        .updateExpression("SET montoTotalPrestamosAprobados = if_not_exists(montoTotalPrestamosAprobados," +
                                " :zero) + :monto")
                        .expressionAttributeValues(Map.of(
                                ":monto", AttributeValue.builder().n(monto.toPlainString()).build(),
                                ":zero", AttributeValue.builder().n("0").build()
                        ))
                )
        ).then();
    }
}