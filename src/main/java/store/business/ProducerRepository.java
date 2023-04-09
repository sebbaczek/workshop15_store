package store.business;

import store.domain.Producer;

public interface ProducerRepository {
        Producer create(Producer producer);
        void removeAll();
}
