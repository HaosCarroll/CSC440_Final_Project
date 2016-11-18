package drivers;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.*;

public interface ProviderRepository extends MongoRepository<Provider, String> {

	List<Provider> findByProviderNumber(@Param("providerNumber") String providerNumber);

}