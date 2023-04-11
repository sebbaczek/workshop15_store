package store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.domain.Product;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
        private ProductRepository productRepository;
        private OpinionService opinionService;
        private PurchaseService purchaseService;
        @Transactional
        public Product create (Product product){
        return productRepository.create(product);
        }
        public List<Product> findAll() {
                return productRepository.findAll();
        }
        @Transactional
        public void removeAll(){

                productRepository.removeAll();
        }

        
        public Product find(String productCode) {
                return productRepository.find(productCode)
                               .orElseThrow(()->new RuntimeException("product code: [%s] is missing".formatted(productCode)));
        }
        @Transactional
        public void removeCompletely(String productCode) {
        purchaseService.removeAllByProductCode(productCode);
        opinionService.removeAllByProductCode(productCode);
        productRepository.remove(productCode);
        }
}