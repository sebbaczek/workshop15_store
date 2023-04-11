package store.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import store.business.*;
import store.domain.*;
import store.infrastructure.configuration.ApplicationConfiguration;
import store.infrastructure.configuration.DatabaseConfiguration;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceTest {
        
        private ReloadDataService reloadDataService;
        
      
        private CustomerService customerService;
        
        private OpinionService opinionService;
        
        private ProducerService producerService;
        
        private ProductService productService;
        
        private PurchaseService purchaseService;
        

        
        @BeforeEach
        public void setUp() {
                Assertions.assertNotNull(customerService);
                Assertions.assertNotNull(opinionService);
                Assertions.assertNotNull(producerService);
                Assertions.assertNotNull(productService);
                Assertions.assertNotNull(purchaseService);
                customerService.removeAll();
                producerService.removeAll();
//                reloadDataService.loadRandomData();
                reloadDataService.reloadData();
//                customerService.removeAll();
//                producerService.removeAll();
        }
        
        @Test
        @DisplayName("polecenie 4.1")
        void thatCustomerIsRemovedCorrectly() {
                final Customer customer = customerService.create(StoreFixtures.someCustomer());
                final Producer producer = producerService.create(StoreFixtures.someProducer());
                final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
                final Product product2 = productService.create(StoreFixtures.someProduct2(producer));
//                final Purchase purchase1 = purchaseService.create(StoreFixtures.somePurchase(customer, product1).withQuantity(1));
//                final Purchase purchase2 = purchaseService.create(StoreFixtures.somePurchase(customer, product2).withQuantity(3));
//        final Opinion opinion = opinionService.create(StoreFixtures.someOpinion(customer, product1));
                        purchaseService.create(StoreFixtures.somePurchase(customer, product1).withQuantity(1));
                purchaseService.create(StoreFixtures.somePurchase(customer, product2).withQuantity(3));
                opinionService.create(customer, StoreFixtures.someOpinion(customer, product1));
        
        assertEquals(customer,customerService.find(customer.getEmail()));
        
        
        //when
        customerService.remove(customer.getEmail());
        //then
                RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> customerService.find(customer.getEmail()));
        
                Assertions.assertEquals(String.format("Customer with email: [%s] is missing", customer.getEmail()), exception.getMessage());
        
                Assertions.assertTrue(purchaseService.findAll(customer.getEmail()).isEmpty());
                Assertions.assertTrue(opinionService.findAll(customer.getEmail()).isEmpty());
        
        }
     





     @Test
     @DisplayName("polecenie 4.2")
     void thatPuchaseAndOpinionIsNotRemovedWhenCustomerRemovingFails() {
             final Customer customer = customerService.create(StoreFixtures.someCustomer().withDateOfBirth(LocalDate.of(1950, 10, 4)));
             final Producer producer = producerService.create(StoreFixtures.someProducer());
             final Product product1 = productService.create(StoreFixtures.someProduct1(producer).withProductCode("g22s").withProductName("Shoes"));
             final Product product2 = productService.create(StoreFixtures.someProduct2(producer).withProductCode(
                     "Kl09").withProductName("TV"));
             final Purchase purchase1 = purchaseService.create(StoreFixtures
                                                                       .somePurchase(customer, product1)
                                                                       .withQuantity(1)
                                                                       .withDateTime(OffsetDateTime.of(2020, 1, 2, 11, 34, 10, 0, ZoneOffset.ofHours(2))));
             final Purchase purchase2 = purchaseService.create(StoreFixtures
                                                                       .somePurchase(customer, product2)
                                                                       .withQuantity(3)
                                                                       .withDateTime(OffsetDateTime.of(2020, 1, 4, 11, 34, 10, 0, ZoneOffset.ofHours(2))));
             final Opinion opinion1 = opinionService.create(customer, StoreFixtures.someOpinion(customer, product1));
        
             assertEquals(customer,customerService.find(customer.getEmail()));
        
        //when
             RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                     () -> customerService.remove(customer.getEmail()));
        
//             Assertions.assertEquals("He is older than 40, email: [%s]".formatted(customer.getEmail()), exception.getMessage());
             Assertions.assertEquals(String.format("He is older than 40, email: [%s]", customer.getEmail()), exception.getMessage());
        
             assertEquals(customer,customerService.find(customer.getEmail()));
 
             Assertions.assertEquals(
                     List.of(
                             purchase1
                                     .withDateTime(purchase1.getDateTime().withOffsetSameInstant(ZoneOffset.UTC))
                                     .withCustomer(Customer.builder().id(customer.getId()).build())
                                     .withProduct(Product.builder().id(product1.getId()).build()),
                             purchase2
                                     .withDateTime(purchase2.getDateTime().withOffsetSameInstant(ZoneOffset.UTC))
                                     .withCustomer(Customer.builder().id(customer.getId()).build())
                                     .withProduct(Product.builder().id(product2.getId()).build())
                     ),
                     purchaseService.findAll(customer.getEmail()));
//                     purchaseService.findAll(customer.getEmail()));
             
             Assertions.assertEquals(           List.of(
                     opinion1                    .withCustomer(Customer.builder().id(customer.getId()).build())
                             .withProduct(Product.builder().id(product1.getId()).build())
                             .withDateTime(opinion1.getDateTime().withOffsetSameInstant(ZoneOffset.UTC))),
                     opinionService.findAll(customer.getEmail())
             );
             
             
     }
     
     //polecenie 8
     @Test
        @DisplayName("polecenie 8")
     void thatCustomersGivingUnwantedOpinionsAreRemoved(){
              
                reloadDataService.reloadData();
                assertEquals(100,customerService.findAll().size());
                
                customerService.removeUnwantedCustomers();
                
                assertEquals(62,customerService.findAll().size());
                
                
                
                
     }
     
}
