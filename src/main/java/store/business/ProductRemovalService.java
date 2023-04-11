package store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductRemovalService {
        
        private ProductService productService;
        
        private OpinionService opinionService;
        
        private PurchaseService purchaseService;
        
        @Transactional
        public void removeCompletely(String productCode) {
                purchaseService.removeAllByProductCode(productCode);
                opinionService.removeAllByProductCode(productCode);
                productService.removeCompletely(productCode);
        }
}