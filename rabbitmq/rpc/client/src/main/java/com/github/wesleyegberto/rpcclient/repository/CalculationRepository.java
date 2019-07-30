package com.github.wesleyegberto.rpcclient.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.github.wesleyegberto.rpcclient.entity.Calculation;

@Repository
public class CalculationRepository {
	private MongoTemplate mongoTemplate;

	public CalculationRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public void save(Calculation calculation) {
		this.mongoTemplate.save(calculation);
	}
	
	public Calculation getByTransactionid(String transactionId ) {
		var query = new Query()
			.addCriteria(where("transactionId").is(transactionId));
		
		return this.mongoTemplate.findOne(query, Calculation.class);
	}
	
	public void updateResult(Calculation calculation) {
		var query = new Query()
			.addCriteria(where("_id").is(calculation.getId()));
		
		var update = new Update()
			.set("result", calculation.getResult())
			.set("status", calculation.getStatus())
			.set("replayDate", calculation.getReplayDate())
			.set("processingMessageProperties", calculation.getProcessingMessageProperties())
			.set("delayedReply", calculation.isDelayedReply())
			.currentDate("updateDate");
		
		if (calculation.getRequestMessageProperties() != null) {
			update.set("requestMessageProperties", calculation.getRequestMessageProperties());
		}
			
		this.mongoTemplate.updateFirst(query, update, Calculation.class);
	}
	
	public void updateError(Calculation calculation) {
		var query = new Query()
			.addCriteria(where("_id").is(calculation.getId()));
		
		var update = new Update()
			.set("errorOccurred", calculation.isErrorOccurred())
			.set("errorMessage", calculation.getErrorMessage())
			.set("replayDate", calculation.getReplayDate())
			.set("processingMessageProperties", calculation.getProcessingMessageProperties())
			.set("delayedReply", calculation.isDelayedReply())
			.currentDate("updateDate");
		
		if (calculation.getRequestMessageProperties() != null) {
			update.set("requestMessageProperties", calculation.getRequestMessageProperties());
		}
			
		this.mongoTemplate.updateFirst(query, update, Calculation.class);
	}
}
