package store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import store.business.ProducerRepository;
import store.domain.Producer;
import store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class ProducerDatabaseRepository implements ProducerRepository {
        
        private static final String SELECT_ALL  = "SELECT * FROM PRODUCER";
        public static final String DELETE_ALL = "DELETE FROM PRODUCER WHERE 1=1";
        private final SimpleDriverDataSource simpleDriverDataSource;
        private final DataBaseMapper dataBaseMapper;
        @Override
        public Producer create(Producer producer) {
                SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                                                      .withTableName(DatabaseConfiguration.PRODUCER_TABLE)
                                                      .usingGeneratedKeyColumns(DatabaseConfiguration.PRODUCER_TABLE_PKEY.toLowerCase());
        
                Number producerId = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(producer));
                return producer.withId((long) producerId.intValue());
        }
        
        @Override
        public void removeAll() {
                new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
        }
        
        @Override
        public List<Producer> findAll() {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
                
               return jdbcTemplate.query(SELECT_ALL, dataBaseMapper::mapProducer);

        }
        
        
        
}
