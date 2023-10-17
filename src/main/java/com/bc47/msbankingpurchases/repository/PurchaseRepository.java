package com.bc47.msbankingpurchases.repository;

import com.bc47.msbankingpurchases.entity.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase, String> {
}
