package drivers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import org.joda.time.DateTime;

import java.util.List;

import entities.Billable;

public interface BillableRepository extends MongoRepository<Billable, String> {

	List<Billable> findByEntityBillableIdNumber(@Param("entitybillableIdNumber") String entitybillableIdNumber);
	// Sauce : http://stackoverflow.com/questions/17484153/how-to-delete-items-in-mongorepository-using-query-annotation
	Long deleteBillableByEntityBillableIdNumber(String entitybillableIdNumber);
	// Sauce : http://stackoverflow.com/questions/16715010/count-in-spring-data-mongodb-repository
	Long countByEntityBillableIdNumber(String entitybillableIdNumber);

	// Sauce : these just seems reasonable?
	List<Billable> findAll();
	List<Billable> findByMemberNumberService(@Param("memberNumberService") String memberNumberService);
	List<Billable> findByProviderNumberServicing(@Param("providerNumberServicing") String providerNumberServicing);
	// AND THEY WORK!! WOOT WOOT!!
	
	// Bryant adds a call to return billable entities between a date range:bash mongodb_run.bash
	List<Billable> findByDateServicedRecordedBetween(@Param("dateServicedRecorded")DateTime first, @Param("dateServicedRecorded")DateTime last);

	// And then we try this...
	List<Billable> findByProviderNumberServicingAndDateServicedRecordedBetween(@Param("providerNumberServicing") String providerNumberServicing, @Param("dateServicedRecorded") DateTime first, @Param("dateServicedRecorded") DateTime last);

	// And then we try this...
	List<Billable> findByMemberNumberServiceAndDateServicedRecordedBetween(@Param("memberNumberService") String memberNumberService, @Param("dateServicedRecorded") DateTime first, @Param("dateServicedRecorded") DateTime last);
	
	// We need the earliest billable, so starting here:
	// Sauce : http://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#repositories.limit-query-result
	Billable findFirstByOrderByDateServicedRecordedAsc();
	Billable findLastByOrderByDateServicedRecordedDesc();

	// This function gets the first billable record for the provider ID passed to it.
	Billable findByProviderNumberServicingOrderByDateServicedRecordedAsc(@Param("providerNumberServicing") String providerNumberServicing);
	// This function gets the last billable record for the provider ID passed to it.
	Billable findByProviderNumberServicingOrderByDateServicedRecordedDesc(@Param("providerNumberServicing") String providerNumberServicing);

	// This function gets the first billable record for the provider ID passed to it.
	Billable findByMemberNumberServiceOrderByDateServicedRecordedAsc(@Param("memberNumberService") String memberNumberService);
	// This function gets the last billable record for the provider ID passed to it.
	Billable findByMemberNumberServiceOrderByDateServicedRecordedDesc(@Param("memberNumberService") String memberNumberService);
}  
