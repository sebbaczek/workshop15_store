package store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import store.business.PurchaseRepository;
import store.domain.Purchase;
import store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class PurchaseDatabaseRepository implements PurchaseRepository {
        
        private static final String DELETE_ALL = "DELETE FROM PURCHASE WHERE 1=1";
        private static final String DELETE_WHERE_CUSTOMER_EMAIL = "DELETE FROM PURCHASE WHERE CUSTOMER_ID IN (SELECT ID FROM CUSTOMER WHERE EMAIL = :email)";
        private static final String SELECT_ALL = "SELECT * FROM PURCHASE ORDER BY DATE_TIME";
        private static final String SELECT_ALL_WHERE_CUSTOMER_EMAIL_AND_PRODUCT_CODE = """
                SELECT * FROM PURCHASE AS PUR
                        INNER JOIN CUSTOMER AS CUS ON CUS.ID = PUR.CUSTOMER_ID
                        INNER JOIN PRODUCT AS PROD ON PROD.ID = PUR.PRODUCT_ID
                       WHERE CUS.EMAIL = :email
                      AND PROD.PRODUCT_CODE = :productCode
                       ORDER BY DATE_TIME
                         """;
        private static final String SELECT_WHERE_CUSTOMER_EMAIL = """
                SELECT * FROM PURCHASE AS PUR
                INNER JOIN CUSTOMER AS CUS ON CUS.ID = PUR.CUSTOMER_ID WHERE CUS.EMAIL = :email
                ORDER BY DATE_TIME
                """;
        private final SimpleDriverDataSource simpleDriverDataSource;
        private final DataBaseMapper dataBaseMapper;
        
        @Override
        public Purchase create(Purchase purchase) {
                SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                                                      .withTableName(DatabaseConfiguration.PURCHASE_TABLE)
                                                      .usingGeneratedKeyColumns(DatabaseConfiguration.PURCHASE_TABLE_PKEY.toLowerCase());
                
                
                Map<String, ?> params = dataBaseMapper.mapPurchase(purchase);
                Number purchaseId = jdbcInsert.executeAndReturnKey(params);
                return purchase.withId((long) purchaseId.intValue());
        }
        
        @Override
        public void removeAll() {
                new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
        }
        
        @Override
        public void removeAll(String email) {
                NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate((simpleDriverDataSource));
                jdbcTemplate.update(DELETE_WHERE_CUSTOMER_EMAIL, Map.of("email", email));
        }
        
        @Override
        public List<Purchase> findAll(final String email) {
                NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
                return jdbcTemplate.query(SELECT_WHERE_CUSTOMER_EMAIL, Map.of("email", email),
                        dataBaseMapper::mapPurchase2);
        }
        
        @Override
        public List<Purchase> findAll(final String email, final String productCode) {
                NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
                return jdbcTemplate.query(
                        SELECT_ALL_WHERE_CUSTOMER_EMAIL_AND_PRODUCT_CODE,
                        Map.of(
                                "email", email,
                                "productCode", productCode
                        ),
                        dataBaseMapper::mapPurchase2);
        }
        
        @Override
        public List<Purchase> findAll() {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
                
                List<Purchase> result = jdbcTemplate.query(SELECT_ALL, dataBaseMapper::mapPurchase2);
                log.debug("All purchases: [{}]", result);
                return result;
        }
}
