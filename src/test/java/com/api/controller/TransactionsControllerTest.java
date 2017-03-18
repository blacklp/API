package com.api.controller;

import com.api.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionsControllerTest {
    @Autowired
    private TransactionsController controller;

    @Test
    public void postTransactionWithNoErrors() throws Exception {
        Transaction transaction = new Transaction(7890.23, System.currentTimeMillis());
        controller.postTransaction(transaction);
    }

}