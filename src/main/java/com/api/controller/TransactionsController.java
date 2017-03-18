package com.api.controller;

import com.api.model.Transaction;
import com.api.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(method = RequestMethod.POST)
    public void postTransaction(@RequestBody Transaction transaction) {
        System.out.println("Saving transaction...");

        statisticsService.addTransaction(transaction);
    }
}
