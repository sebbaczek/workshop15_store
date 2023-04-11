package store.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import store.business.*;
import store.domain.Customer;
import store.domain.Producer;
import store.domain.Product;
import store.domain.StoreFixtures;
import store.infrastructure.configuration.ApplicationConfiguration;


//polecenie 9. - zakup w sklepie = dodanie purchase'a
@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartServiceTest {
        
        private ReloadDataService  reloadDataService;
        private ShoppingCartService shoppingCartService;
        private CustomerService customerService;
        private ProducerService producerService;
        private ProductService productService;
        private PurchaseService purchaseService;
        
        
        @BeforeEach
        public void setUp(){
                Assertions.assertNotNull(reloadDataService);
                reloadDataService.reloadData();
        }
        
        @Test
        @DisplayName("polecenie 9")
        void thatProductCanBeBoughtByCustomer(){
        
                final Customer customer = customerService.create(StoreFixtures.someCustomer());
                final Producer producer = producerService.create(StoreFixtures.someProducer());
                final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
                productService.create(StoreFixtures.someProduct2(producer));
        final var before = purchaseService.findAll(customer.getEmail(),product1.getProductCode());
        
        //when
                shoppingCartService.makePurchase(customer.getEmail(),product1.getProductCode(),10);
                
        //then
        final var after = purchaseService.findAll(customer.getEmail(),product1.getProductCode());
        Assertions.assertEquals(before.size()+1,after.size());
        
        
        
        
        }
        
        
        
        
        
        
        
}
