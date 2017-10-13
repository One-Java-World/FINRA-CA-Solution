
package com.edom.mesfin.solution.repository;

import com.edom.mesfin.solution.entity.Document;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository for Document Entity
 * 
 * @author Edom.Mesfin
 */
@RepositoryRestResource
public interface DocumentRepository extends CrudRepository<Document, String> {
    Document findByUuid(String uuid);
    List<Document> findBySysName(String systemName);
    
}
