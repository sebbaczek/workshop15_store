package store.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Slf4j
@Repository
@AllArgsConstructor
public class ReloadDataService {
        private CustomerService customerService;
        private  ProducerService producerService;
        private RandomDataService randomDataService;
        private ReloadDataRepository reloadDataRepository;
        
        @Transactional
        public void loadRandomData(){
                customerService.removeAll();
                producerService.removeAll();
                for (int i = 0; i < 10; i++) {
                        randomDataService.create();
                }
                
        }
        @Transactional
        public void reloadData(){
                customerService.removeAll();
                producerService.removeAll();
//zadanie 6. - wkładanie insertów z pliku sql w resources
                
                try {
                        Path filepath = ResourceUtils.getFile("classpath:w15-project-sql-inserts.sql").toPath();
                       Stream.of(Files.readString(filepath).split("INSERT"))
                               .filter(line->!line.isBlank())
                               .map(line->"INSERT"+line)
                               .toList()
                               .forEach(sql->reloadDataRepository.run(sql));

                }catch (Exception e){
                        log.error("Unable to load SQL inserts", e);
                }
                
                
        }
}
        
        
        
        
        
        


