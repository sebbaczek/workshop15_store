package store.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.domain.Customer;
import store.domain.Product;
import store.domain.Purchase;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartService {
        
        private final CustomerService customerService;
        private final ProductService productService;
        private final PurchaseService purchaseService;
        
        @Transactional
        public void makePurchase(String email, String productCode, int quantity) {
                Customer customer = customerService.find(email);
             Product product =  productService.find(productCode);
                Purchase purchase = purchaseService.create(Purchase.builder()
                           .customer(customer)
                           .product(product)
                           .quantity(quantity)
                           .dateTime(OffsetDateTime.of(2020,1,1,10,5,1,0, ZoneOffset.ofHours(4)))
                           .build());
        
                log.info("Customer [{}] made a purchase [{}]  for product [{}] , quantity [{}] ", email,purchase,
                        productCode,
                        quantity);
        
        
        
        
        
        
        
        
                
        }
}
