package store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import store.business.ProductRepository;
import store.domain.Customer;
import store.domain.Product;
import store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class ProductDatabaseRepository implements ProductRepository {
        private static final String SELECT_ALL  = "SELECT * FROM PRODUCT";
        private static final  String SELECT_ALL_WHERE_PRODUCT_CODE = "SELECT * FROM PRODUCT WHERE PRODUCT_CODE = :productCode";
        
        private static final String DELETE_ALL = "DELETE FROM PRODUCT WHERE 1=1";
        private static final String DELETE_WHERE_PRODUCT_CODE ="DELETE FROM PRODUCT WHERE PRODUCT_CODE = :productCode";
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
        
        @Override
        public Optional<Product> find(String productCode) {
                final var jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        
                Map<String,Object>params=Map.of(
                        "productCode",productCode
                );
        
                try{
                        RowMapper<Customer> rowMapper = dataBaseMapper::mapCustomer;
                        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_ALL_WHERE_PRODUCT_CODE, params,                                dataBaseMapper::mapProduct2));
                } catch (Exception e) {
                        log.warn("Trying to find no-existing product: [{}]",productCode);
                        return Optional.empty();
                }
        }
        
        @Override
        public void remove(String productCode) {
                final var jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_WHERE_PRODUCT_CODE,Map.of("productCode",productCode));
        }
        
        
}
