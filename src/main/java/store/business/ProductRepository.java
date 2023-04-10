package store.business;

import store.domain.Product;

import java.util.List;

public interface ProductRepository {
        Product create(Product product);
        void removeAll();
        
        List<Product> findAll();
}
