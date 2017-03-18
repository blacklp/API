package com.api.controller;

import com.api.model.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionsControllerTest {
    @Autowired
    private TransactionsController controller;

    @Test
    public void postTransactionWithNoErrors() {
        Transaction transaction = new Transaction(7890.23, System.currentTimeMillis());
        ResponseEntity<Transaction> response = controller.postTransaction(transaction);
        Assertions.assertThat(response).isEqualTo(ResponseEntity.noContent().build());
    }

    @Test(expected = ConstraintViolationException.class)
    public void postTransactionWithNoAmount() throws Exception {
        Transaction transaction = new Transaction(null, System.currentTimeMillis());
        controller.postTransaction(transaction);
    }

    @Test(expected = ConstraintViolationException.class)
    public void postTransactionWithNoTimestamp() throws Exception {
        Transaction transaction = new Transaction(7890.23, null);
        controller.postTransaction(transaction);
    }
}