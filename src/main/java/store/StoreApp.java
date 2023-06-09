package store;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import store.business.CustomerService;
import store.business.RandomDataService;
import store.business.ReloadDataService;
import store.infrastructure.configuration.ApplicationConfiguration;

public class StoreApp {
        public static void main(String[] args) {
                ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
                ReloadDataService reloadDataService = context.getBean(ReloadDataService.class);
                reloadDataService.reloadData();
        
//                RandomDataService randomDataService = context.getBean(RandomDataService.class);
//                randomDataService.create();
//
//                CustomerService customerService = context.getBean(CustomerService.class);
        }
}
