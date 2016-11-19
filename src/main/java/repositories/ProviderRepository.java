package drivers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.mongodb.WriteResult;

import java.util.List;

import entities.Provider;

public interface ProviderRepository extends MongoRepository<Provider, String> {

	List<Provider> findByProviderNumber(@Param("providerNumber") String providerNumber);
	// Sauce : http://stackoverflow.com/questions/17484153/how-to-delete-items-in-mongorepository-using-query-annotation
	Long deleteProviderByProviderNumber(String providerNumber);
	// Sauce : http://stackoverflow.com/questions/16715010/count-in-spring-data-mongodb-repository
	Long countByProviderNumber(String providerNumber);
	// Sauce : this just seems reasonable?
	List<Provider> findAll();
	
}