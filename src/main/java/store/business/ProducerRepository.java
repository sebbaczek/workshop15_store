package store.business;

import store.domain.Producer;

import java.util.List;

public interface ProducerRepository {
        Producer create(Producer producer);
        void removeAll();
        
        List<Producer> findAll();
}
