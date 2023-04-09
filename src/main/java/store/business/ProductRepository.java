package store.business;

import store.domain.Product;

public interface ProductRepository {
        Product create(Product product);
        void removeAll();
}
