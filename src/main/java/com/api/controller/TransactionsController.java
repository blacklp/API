package com.api.controller;

import com.api.model.Transaction;
import com.api.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     *
     * @param transaction to be created in the system
     * @return HTTP code 204, NO_CONTENT with an empty JSON body if the transaction was correctly created.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Transaction> postTransaction(@Valid @RequestBody Transaction transaction) {
        statisticsService.addTransaction(transaction);
        return ResponseEntity.noContent().build();
    }
}
