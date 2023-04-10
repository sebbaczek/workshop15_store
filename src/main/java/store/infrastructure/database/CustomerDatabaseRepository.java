package store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import store.business.CustomerRepository;
import store.domain.Customer;
import store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Repository
@AllArgsConstructor
public class CustomerDatabaseRepository implements CustomerRepository {
        
        private static final String DELETE_ALL = "DELETE FROM CUSTOMER WHERE 1=1";
        private static final String SELECT_ONE_WHERE_EMAIL = "SELECT * FROM CUSTOMER WHERE EMAIL = :email";
        private static final String DELETE_WHERE_CUSTOMER_EMAIL = "DELETE FROM CUSTOMER WHERE EMAIL = :email";
        private static final String SELECT_ALL  = "SELECT * FROM CUSTOMER";
        private final SimpleDriverDataSource simpleDriverDataSource;
        
        private final DataBaseMapper dataBaseMapper;
        
        @Override
        public Customer create(Customer customer) {
        
                SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.CUSTOMER_TABLE)
              .usingGeneratedKeyColumns(DatabaseConfiguration.CUSTOMER_TABLE_PKEY.toLowerCase());
        
                Number customerId = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(customer));
                return customer.withId((long) customerId.intValue());
                
        }
        
        @Override
        public void removeAll() {
        new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
        }
        
        @Override
        public Optional<Customer> find(String email) {
                final var jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        
                Map<String,Object>params=Map.of(
                        "email",email
                );
                
       try{
               RowMapper<Customer> rowMapper = dataBaseMapper::mapCustomer;
               return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_ONE_WHERE_EMAIL, params, rowMapper));
       } catch (Exception e) {
               log.warn("Trying to find no-existing customer: [{}]",email);
               return Optional.empty();
       }
        
        
        

                
                
                
         
        }
        
        @Override
        public void remove(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_WHERE_CUSTOMER_EMAIL,Map.of("email",email));
        }
        
        @Override
        public List<Customer> findAll() {
     JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
                return jdbcTemplate.query(SELECT_ALL,dataBaseMapper::mapCustomer);
        }
}
