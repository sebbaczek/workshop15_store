package store.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import store.business.*;
import store.domain.Opinion;
import store.domain.Product;
import store.domain.Purchase;
import store.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;
import java.util.Objects;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceTest {
        private ReloadDataService reloadDataService;
//        private ShoppingCartService shoppingCartService;
        private ProductService productService;
        private PurchaseService purchaseService;
        private OpinionService opinionService;
        private ProductRemovalService productRemovalService;
        
        @BeforeEach
        public void setUp(){
                Assertions.assertNotNull(reloadDataService);
                Assertions.assertNotNull(productService);
                Assertions.assertNotNull(purchaseService);
                Assertions.assertNotNull(opinionService);
                Assertions.assertNotNull(productRemovalService);
                reloadDataService.reloadData();
        }
        
        @Test
        @DisplayName("polecenie 10")
void thatProductIsWiped(){
        
        final var productCode = "60560-1072";
                Product before = productService.find(productCode);
                List<Opinion> opinionsBefore = opinionService.findAllByProductCode(productCode);
                List<Purchase> purchasesBefore = purchaseService.findAllByProductCode(productCode);
        
                Assertions.assertTrue(Objects.nonNull(before));
        Assertions.assertEquals(3,opinionsBefore.size());
        Assertions.assertEquals(4,purchasesBefore.size());
        
        
                productRemovalService.removeCompletely(productCode);
        
                RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> productService.find((productCode)));
                Assertions.assertEquals("product code: [%s] is missing".formatted(productCode),exception.getMessage());
                
                Assertions.assertTrue(opinionService.findAllByProductCode(productCode).isEmpty());
                Assertions.assertTrue(purchaseService.findAllByProductCode(productCode).isEmpty());
        
        
        
        
                
        }










        
}
