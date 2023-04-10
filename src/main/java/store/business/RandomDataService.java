package store.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.domain.*;
import store.infrastructure.database.*;


@Slf4j
 @Service
 @AllArgsConstructor
 public class RandomDataService {
  
  private final RandomDataPreparationService randomDataPreparationService;
  
  private final CustomerDatabaseRepository customerDataBaseRepository;
  
  private final ProducerDatabaseRepository producerDataBaseRepository;
  
  private final ProductDatabaseRepository productDataBaseRepository;
  
  private final PurchaseDatabaseRepository purchaseDataBaseRepository;
  
  private final OpinionDatabaseRepository opinionDataBaseRepository;
  
  public void create() {
   Customer customer = customerDataBaseRepository.create(randomDataPreparationService.someCustomer());
   Producer producer = producerDataBaseRepository.create(randomDataPreparationService.someProducer());
   Product product = productDataBaseRepository.create(randomDataPreparationService.someProduct(producer));
   Purchase purchase = purchaseDataBaseRepository.create(randomDataPreparationService.somePurchase(customer, product));
   Opinion opinion = opinionDataBaseRepository.createOpinion(randomDataPreparationService.someOpinion(customer, product));
   
   log.debug("Random customer created: [[{}]", customer);
   log.debug("Random producer created: [[{}]", producer);
   log.debug("Random product created: [[{}]", product);
   log.debug("Random purchase created: [[{}]", purchase);
   log.debug("Random opinion created: [[{}]", opinion);
  }
  
 }








