package store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import store.business.OpinionRepository;
import store.domain.Opinion;
import store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class OpinionDatabaseRepository implements OpinionRepository {
        
        

        private final SimpleDriverDataSource simpleDriverDataSource;
        private final DataBaseMapper dataBaseMapper;
        
        private static final String SELECT_ALL = "SELECT * FROM OPINION ORDER BY DATE_TIME";
        private static final String DELETE_ALL = "DELETE FROM OPINION WHERE 1=1";
        private static final String DELETE_WHERE_CUSTOMER_EMAIL = "DELETE FROM OPINION WHERE CUSTOMER_ID IN (SELECT ID FROM CUSTOMER WHERE EMAIL = :email)";
        private static final String SELECT_WHERE_CUSTOMER_EMAIL = """
                SELECT * FROM OPINION AS OPN
                INNER JOIN CUSTOMER AS CUS ON CUS.ID = OPN.CUSTOMER_ID WHERE CUS.EMAIL = :email
                ORDER BY DATE_TIME
                """;

        
        @Override
        public Opinion create(Opinion opinion) {
                SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                                                      .withTableName(DatabaseConfiguration.OPINION_TABLE)
                                                      .usingGeneratedKeyColumns(DatabaseConfiguration.OPINION_TABLE_PKEY.toLowerCase());
        
                Map<String,?> params = dataBaseMapper.mapOpinion(opinion);
                Number opinionId = jdbcInsert.executeAndReturnKey(params);
                return opinion.withId((long) opinionId.intValue());
        }
        
        @Override
        public void removeAll() {
                new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
        }
        
        @Override
        public void remove(String email) {
                NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
                jdbcTemplate.update(DELETE_WHERE_CUSTOMER_EMAIL,Map.of("email",email));
                
        }
        
     
        @Override
        public List<Opinion> findAll(String email) {
                NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
                return jdbcTemplate.query(SELECT_WHERE_CUSTOMER_EMAIL, Map.of("email", email),dataBaseMapper::mapOpinion2);
        }
        
        @Override
        public List<Opinion> findAll() {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
                
             return jdbcTemplate.query(SELECT_ALL, dataBaseMapper::mapOpinion2);
//                log.debug("All opinions: [{}]", result);
//                return result;
        }
        
        @Override
        public Opinion createOpinion(final Opinion opinion) {
                SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                                                      .withTableName(DatabaseConfiguration.OPINION_TABLE)
                                                      .usingGeneratedKeyColumns(DatabaseConfiguration.OPINION_TABLE_PKEY.toLowerCase());
        
                Map<String,?> params = dataBaseMapper.mapOpinion(opinion);
                Number purchaseId = jdbcInsert.executeAndReturnKey(params);
                return opinion.withId((long) purchaseId.intValue());
                
        }
}
