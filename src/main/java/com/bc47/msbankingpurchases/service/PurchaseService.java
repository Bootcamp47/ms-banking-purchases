package com.bc47.msbankingpurchases.service;

import com.bc47.msbankingpurchases.api.PurchasesApiDelegate;
import com.bc47.msbankingpurchases.entity.Purchase;
import com.bc47.msbankingpurchases.model.PurchaseDTO;
import com.bc47.msbankingpurchases.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService implements PurchasesApiDelegate {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public ResponseEntity<List<PurchaseDTO>> retrieveAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<PurchaseDTO> purchaseDTOList =
                purchases
                        .stream()
                        .map(this::createDTO)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(purchaseDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PurchaseDTO> retrievePurchase(String id) {
        List<Purchase> purchases = purchaseRepository.findAll();
        Optional<PurchaseDTO> purchaseFound =
                purchases
                        .stream()
                        .filter(p -> Objects.equals(p.getId(), id))
                        .map(this::createDTO)
                        .findFirst();
        return purchaseFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new PurchaseDTO()));
    }

    @Override
    public ResponseEntity<PurchaseDTO> savePurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = Purchase
                .builder()
                .id(purchaseDTO.getId())
                .customerId(purchaseDTO.getCustomerId())
                .customerType(purchaseDTO.getCustomerType())
                .customerName(purchaseDTO.getCustomerName())
                .productId(purchaseDTO.getProductId())
                .productType(purchaseDTO.getProductType())
                .productCategory(purchaseDTO.getProductCategory())
                .createdAt(new Date().toString())
                .state(purchaseDTO.getState())
                .accountNo(purchaseDTO.getAccountNo())
                .balance(purchaseDTO.getBalance())
                .hasMaintenanceCommission(purchaseDTO.getHasMaintenanceCommission())
                .maintenanceCommissionPercentage(purchaseDTO.getMaintenanceCommissionPercentage())
                .hasMonthTransactionLimitQty(purchaseDTO.getHasMonthTransactionLimitQty())
                .monthTransactionLimitQty(purchaseDTO.getMonthTransactionLimitQty())
                .maxCreditsQuantityAllowed(purchaseDTO.getMaxCreditsQuantityAllowed())
                .creditAmountLimit(purchaseDTO.getCreditAmountLimit())
                .customerCurrentMonthTransactionsMade(purchaseDTO.getCustomerCurrentMonthTransactionsMade())
                .build();
        purchaseRepository.save(purchase);
        purchaseDTO.setCreatedAt(new Date().toString());
        return ResponseEntity.ok(purchaseDTO);
    }

    @Override
    public ResponseEntity<PurchaseDTO> updatePurchase(PurchaseDTO purchaseDTO) {
        return savePurchase(purchaseDTO);
    }

    @Override
    public ResponseEntity<PurchaseDTO> deletePurchase(String id) {
        List<Purchase> purchases = purchaseRepository.findAll();
        Optional<PurchaseDTO> purchaseFound =
                purchases
                        .stream()
                        .filter(p -> Objects.equals(p.getId(), id))
                        .map(purchase -> {
                            purchaseRepository.deleteById(purchase.getId());
                            return createDTO(purchase);
                        })
                        .findFirst();
        return purchaseFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new PurchaseDTO()));
    }

    private PurchaseDTO createDTO(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setCustomerId(purchase.getCustomerId());
        purchaseDTO.setCustomerType(purchase.getCustomerType());
        purchaseDTO.setCustomerName(purchase.getCustomerName());
        purchaseDTO.setProductId(purchase.getProductId());
        purchaseDTO.setProductType(purchase.getProductType());
        purchaseDTO.setProductCategory(purchase.getProductCategory());
        purchaseDTO.setCreatedAt(purchase.getCreatedAt());
        purchaseDTO.setState(purchase.getState());
        purchaseDTO.setAccountNo(purchase.getAccountNo());
        purchaseDTO.setBalance(purchase.getBalance());
        purchaseDTO.setHasMaintenanceCommission(purchase.getHasMaintenanceCommission());
        purchaseDTO.setMaintenanceCommissionPercentage(purchase.getMaintenanceCommissionPercentage());
        purchaseDTO.setHasMonthTransactionLimitQty(purchase.getHasMonthTransactionLimitQty());
        purchaseDTO.setMonthTransactionLimitQty(purchase.getMonthTransactionLimitQty());
        purchaseDTO.setMaxCreditsQuantityAllowed(purchase.getMaxCreditsQuantityAllowed());
        purchaseDTO.setCreditAmountLimit(purchase.getCreditAmountLimit());
        purchaseDTO.setCustomerCurrentMonthTransactionsMade(purchase.getCustomerCurrentMonthTransactionsMade());
        return purchaseDTO;
    }
}
