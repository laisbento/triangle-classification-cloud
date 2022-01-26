package com.cocus.triangleclassification.repository;

import com.cocus.triangleclassification.model.response.ResultsDynamoTable;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ResultsRepository extends CrudRepository<ResultsDynamoTable, String> {
}
