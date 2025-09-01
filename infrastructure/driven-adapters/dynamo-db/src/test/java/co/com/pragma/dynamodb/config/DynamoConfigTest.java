package co.com.pragma.dynamodb.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DynamoConfigTest {

    @Mock
    private MetricPublisher publisher;

    @Mock
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    private final DynamoConfig dynamoConfig = new DynamoConfig();

    @Test
    void testAmazonDynamoDB() {

        DynamoDbAsyncClient result = dynamoConfig.amazonDynamoDB(
                "http://aws.dynamo.test",
                "region",
                publisher);

        assertNotNull(result);
    }

    @Test
    void testAmazonDynamoDBAsync() {

        DynamoDbAsyncClient result = dynamoConfig.amazonDynamoDBAsync(
                publisher,
                "region");

        assertNotNull(result);
    }


    @Test
    void testGetDynamoDbEnhancedAsyncClient() {
        DynamoDbEnhancedAsyncClient result = dynamoConfig.getDynamoDbEnhancedAsyncClient(dynamoDbAsyncClient);

        assertNotNull(result);
    }
}
