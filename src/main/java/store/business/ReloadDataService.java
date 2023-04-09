package store.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@AllArgsConstructor
public class ReloadDataService {
        private CustomerService customerService;
        private  ProducerService producerService;
        private RandomDataService randomDataService;
        
        @Transactional
        public void loadRandomData(){
                customerService.removeAll();
                producerService.removeAll();
                for (int i = 0; i < 10; i++) {
                        randomDataService.create();
                }
                
        }
}
        
        
        
        
        
        


