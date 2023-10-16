package com.bc47.msbankingpurchases.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "purchases")
public class Purchase {

    @Id
    private Long id;
    private Long customerId;
    private String customerType;
    private String customerName;
    private Long productId;
    private String productType;
    private String productCategory;
    private String createdAt;
    private String state;
    private String accountNo;
    private Double balance;
    private Boolean hasMaintenanceCommission;
    private Double maintenanceCommissionPercentage;
    private Boolean hasMonthTransactionLimitQty;
    private Integer monthTransactionLimitQty;
    private Integer maxCreditsQuantityAllowed;
    private Double creditAmountLimit;
    private Integer customerCurrentMonthTransactionsMade;
}
