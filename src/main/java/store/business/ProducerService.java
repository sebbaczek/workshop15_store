package store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.domain.Producer;

@Service
@AllArgsConstructor
public class ProducerService {
        private final ProductService productService;
        private ProducerRepository producerRepository;
        
        @Transactional
        public Producer create (Producer producer){
                return producerRepository.create(producer);
        }
        
        @Transactional
        public void removeAll(){
                
                productService.removeAll();
                producerRepository.removeAll();
        }
}