package co.com.pragma.dynamodb;

import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;

@Data
@Builder
@DynamoDbBean
public class ModelEntity {

    private String id;
    private int cantidadPrestamosAprobados;
    private BigDecimal montoTotalPrestamosAprobados;

    public ModelEntity() {
    }

    public ModelEntity(String id, int cantidadPrestamosAprobados, BigDecimal montoTotalPrestamosAprobados) {
        this.id = id;
        this.cantidadPrestamosAprobados = cantidadPrestamosAprobados;
        this.montoTotalPrestamosAprobados = montoTotalPrestamosAprobados;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("cantidadPrestamosAprobados")
    public int getCantidadPrestamosAprobados() {
        return cantidadPrestamosAprobados;
    }

    @DynamoDbAttribute("montoTotalPrestamosAprobados")
    public BigDecimal getMontoTotalPrestamosAprobados() {
        return montoTotalPrestamosAprobados;
    }

    public void setCantidadPrestamosAprobados(int cantidadPrestamosAprobados) {
        this.cantidadPrestamosAprobados = cantidadPrestamosAprobados;
    }
}
