package store.business;

import store.domain.Opinion;

import java.util.List;

public interface OpinionRepository {
        Opinion create(Opinion opinion);
        void removeAll();
        
        void remove(String email);
        
        List<Opinion> findAll(String email);
        
        List<Opinion> findAll();
        
        Opinion createOpinion(Opinion opinion);
        
        List<Opinion> findUnwantedOpinions();
        
        void removeUnwantedOpinions();
        
        boolean customerGivesUnwantedOpinions(String email);
        
        List<Opinion> findAllByProductCode(String productCode);
        
        void removeAllByProductCode(String productCode);
}
