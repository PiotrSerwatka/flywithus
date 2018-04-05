package com.flywithus;

import com.flywithus.BaseTest.MockBeans;
import com.flywithus.payment.PaymentProvider;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest(showSql = true)
@ContextConfiguration(classes = {
        MockBeans.class
})
@Transactional
@Rollback
@ComponentScan("com.flywithus")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public abstract class BaseTest {


    public static class MockBeans {

        @Bean
        public PaymentProvider paymentProvider() {
            return Mockito.mock(PaymentProvider.class);
        }
    }

}
