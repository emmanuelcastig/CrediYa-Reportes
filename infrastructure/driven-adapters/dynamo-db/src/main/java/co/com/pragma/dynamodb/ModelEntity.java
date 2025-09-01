package co.com.pragma.dynamodb;

import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@Builder
@DynamoDbBean
public class ModelEntity {

    private String id;
    private int cantidadPrestamosAprobados;

    public ModelEntity() {
    }

    public ModelEntity(String id, int cantidadPrestamosAprobados) {
        this.id = id;
        this.cantidadPrestamosAprobados = cantidadPrestamosAprobados;
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

    public void setCantidadPrestamosAprobados(int cantidadPrestamosAprobados) {
        this.cantidadPrestamosAprobados = cantidadPrestamosAprobados;
    }
}
