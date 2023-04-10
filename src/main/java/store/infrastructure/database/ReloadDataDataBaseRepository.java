package store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import store.business.ReloadDataRepository;

@Slf4j
@Repository
@AllArgsConstructor
public class ReloadDataDataBaseRepository implements ReloadDataRepository {
        
        private final SimpleDriverDataSource simpleDriverDataSource;
        
        @Override
        public void run(String sql) {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
                jdbcTemplate.update(sql);
        }
}
