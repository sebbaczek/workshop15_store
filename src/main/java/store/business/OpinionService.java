package store.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.domain.Customer;
import store.domain.Opinion;
import store.domain.Purchase;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OpinionService {
        private final OpinionRepository opinionRepository;
        private final PurchaseService purchaseService;
        @Transactional
        public Opinion create(Customer customer, Opinion opinion) {
                List<Purchase> purchases = purchaseService.findAll(customer.getEmail(), opinion.getProduct().getProductCode());
                log.debug("Customer: [{}] made: [{}] purchases of product: [{}]",
                        customer.getEmail(), purchases.size(), opinion.getProduct().getProductCode());
                
                if (purchases.isEmpty()) {
                        throw new RuntimeException(
                                String.format("Product codes mismatch. Customer: [%s] wants to give opinion for product: [%s] that didnt purchase",
                                        customer.getEmail(), opinion.getProduct().getProductCode()));
                }
                
                return opinionRepository.createOpinion(opinion);
        }
        @Transactional
        public void removeAll(){
                opinionRepository.removeAll();
        }
        @Transactional
        public void removeAll(String email) {
                opinionRepository.remove(email);
        }
        
        
        
        public List<Opinion> findAll(final String email) {
                return opinionRepository.findAll(email);
        }
        public List<Opinion> findAll() {
                return opinionRepository.findAll();
        }
        
        @Transactional
        public Opinion create(Opinion opinion) {
                List<Purchase> purchases = purchaseService.findAll(opinion.getCustomer().getEmail(), opinion.getProduct().getProductCode());
                if(purchases.isEmpty()){
                        throw new RuntimeException(("Customer: [%s] wants to give opinion for product: [%s] but there " +
                                                            "is no purchase").formatted(opinion.getCustomer().getEmail(),opinion.getProduct().getProductCode()));
                }
                
                return opinionRepository.create(opinion);
                
                
        }
        
        public List<Opinion> findUnwantedOpinions() {
                return opinionRepository.findUnwantedOpinions();
        }
        
        @Transactional
        public void removeUnwantedOpinions() {
                opinionRepository.removeUnwantedOpinions();
        }
        
        public boolean customerGivesUnwantedOpinions(String email) {
        return opinionRepository.customerGivesUnwantedOpinions(email);
        
        }
        
        public List<Opinion> findAllByProductCode(String productCode) {
                return opinionRepository.findAllByProductCode(productCode);
        }
        
        public void removeAllByProductCode(String productCode) {
                opinionRepository.removeAllByProductCode(productCode);
        }
}

