
package com.edom.mesfin.solution.repository;

import com.edom.mesfin.solution.entity.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository for Document Entity
 * 
 * @author Edom.Mesfin
 */
@RepositoryRestResource
public interface DocumentRepository extends CrudRepository<Document, Long> {
    Document findByUuid(String uuid);
    
    
}
