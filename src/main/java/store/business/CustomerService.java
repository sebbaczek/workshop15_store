package store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.domain.Customer;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
        private OpinionService  opinionService;
        private PurchaseService purchaseService;
        private CustomerRepository customerRepository;
       
        @Transactional
        public Customer create(Customer customer){
                return customerRepository.create(customer);
        }
        
        @Transactional
        public void removeAll(){
                opinionService.removeAll();
                purchaseService.removeAll();
                customerRepository.removeAll();
        }
        
        public Customer find(String email) {
                return customerRepository.find(email)
                               .orElseThrow(()->new RuntimeException("Customer with email: [%s] is missing".formatted(email)));
        }
        
        @Transactional
        public void remove(final String email) {
                Optional <Customer> existingCustomer = customerRepository.find(email);
                if (existingCustomer.isEmpty()) {
                        throw new RuntimeException(String.format("Customer with email: [%s] not found", email));
                }
                if(isOldeThan40(existingCustomer.get())){
                        throw new RuntimeException("He is older than 40, email: [%s]".formatted(email));}
                opinionService.removeAll(email);
                purchaseService.removeAll(email);
                customerRepository.remove(email);

                
  
        }
        
        private boolean isOldeThan40(Customer existingCustomer) {
                return LocalDateTime.now().getYear() - existingCustomer.getDateOfBirth().getYear()>40;
        }
}


