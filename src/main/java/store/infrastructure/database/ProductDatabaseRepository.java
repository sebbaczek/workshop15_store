package store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import store.business.ProductRepository;
import store.domain.Product;
import store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class ProductDatabaseRepository implements ProductRepository {
        private static final String SELECT_ALL  = "SELECT * FROM PRODUCT";
        public static final String DELETE_ALL = "DELETE FROM PRODUCT WHERE 1=1";
        private final SimpleDriverDataSource simpleDriverDataSource;
        private final DataBaseMapper dataBaseMapper;
        
        @Override
        public Product create(Product product) {
                SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                                                      .withTableName(DatabaseConfiguration.PRODUCT_TABLE)
                                                      .usingGeneratedKeyColumns(DatabaseConfiguration.PRODUCT_TABLE_PKEY.toLowerCase());
                
                Map<String, ?> params = dataBaseMapper.mapProduct(product);
                
                
                Number productId = jdbcInsert.executeAndReturnKey(params);
                return product.withId((long) productId.intValue());
        }
        
        @Override
        public void removeAll() {
                new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
        }
        @Override
        public List<Product> findAll() {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
                
                return jdbcTemplate.query(SELECT_ALL, dataBaseMapper::mapProduct2);
//                log.debug("All opinions: [{}]", result);
//                return result;
        }
}
