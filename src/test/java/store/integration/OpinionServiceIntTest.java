package store.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import store.business.OpinionService;
import store.business.ReloadDataService;
import store.domain.Opinion;
import store.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OpinionServiceIntTest {

        private ReloadDataService reloadDataService;
        private OpinionService opinionService;
        
        @BeforeEach
        void setUp(){
                Assertions.assertNotNull(reloadDataService);
                Assertions.assertNotNull(opinionService);
                reloadDataService.reloadData();
        }

@Test
        @DisplayName("polecenie 7")
        void thatUnwantedOpinionAreRemoved(){
        
                Assertions.assertEquals(140,opinionService.findAll().size());
        List<Opinion> unwantedOpinions = opinionService.findUnwantedOpinions();
        
        opinionService.removeUnwantedOpinions();
        
        
        Assertions.assertEquals(140-unwantedOpinions.size(),opinionService.findAll().size());
        
        
        
}











}
