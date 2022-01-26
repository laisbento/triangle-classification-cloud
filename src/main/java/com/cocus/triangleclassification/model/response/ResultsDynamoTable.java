package com.cocus.triangleclassification.model.response;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@DynamoDBTable(tableName = "triangle_results")
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Data
public class ResultsDynamoTable {
    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String index;

    @DynamoDBAttribute
    private String request;

    @DynamoDBAttribute
    private String result;

}
