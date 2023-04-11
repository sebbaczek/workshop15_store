package store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.domain.Purchase;

import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseService {
        private final ProducerService producerService;
        private PurchaseRepository purchaseRepository;
        @Transactional
        public Purchase create(Purchase purchase){
                return purchaseRepository.create(purchase);
        }
        @Transactional
        public void removeAll(){
                purchaseRepository.removeAll();
        }
        @Transactional
        public void removeAll(String email) {
                purchaseRepository.removeAll(email);
        }
        
        public List<Purchase> findAll() {
                return purchaseRepository.findAll();
        }
        
        public List<Purchase> findAll(final String email) {
                return purchaseRepository.findAll(email);
        }
        
        public List<Purchase> findAll(final String email, final String productCode) {
                return purchaseRepository.findAll(email, productCode);
        }
        
        public List<Purchase> findAllByProductCode(String productCode) {
                return purchaseRepository.findAllByProductCode(productCode);
        }
        
        public void removeAllByProductCode(String productCode) {
                purchaseRepository.removeAllByProductCode(productCode);
        }
}