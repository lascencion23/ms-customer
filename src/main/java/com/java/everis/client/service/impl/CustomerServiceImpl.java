package com.java.everis.client.service.impl;

import com.java.everis.client.entity.Customer;
import com.java.everis.client.entity.TypeCustomer;
import com.java.everis.client.repository.CustomerRepository;
import com.java.everis.client.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final WebClient webClient;
	private final ReactiveCircuitBreaker reactiveCircuitBreaker;
	
	String uri = "http://gateway:8090/api/ms-profile/typecustomer/find/{id}";
	
	public CustomerServiceImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
		this.webClient = WebClient.builder().baseUrl(this.uri).build();
		this.reactiveCircuitBreaker = circuitBreakerFactory.create("typeCustomer");
	}

	@Autowired
	CustomerRepository customerRepository;

	// Plan A
	@Override
	public Mono<TypeCustomer> findTypeCustomer(String id) {
		return reactiveCircuitBreaker.run(webClient.get().uri(this.uri,id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(TypeCustomer.class),
				throwable -> {
					return this.getDefaultTypeCustomer();
				});
	}

	// Plan B
	public Mono<TypeCustomer> getDefaultTypeCustomer() {
		Mono<TypeCustomer> tcustomer = Mono.just(new TypeCustomer("0", null, null));
		return tcustomer;
	}

	@Override
	public Mono<Customer> create(Customer customer) {

		return customerRepository.save(customer);
	}

	@Override
	public Flux<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Mono<Customer> findById(String id) {
		return customerRepository.findById(id);
	}

	@Override
	public Mono<Customer> update(Customer customer) {
		return customerRepository.findById(customer.getId()).flatMap(custDB -> {
			return customerRepository.save(customer);
		});

	}

	@Override
	public Mono<Boolean> delete(String id) {
		return customerRepository.findById(id)
				.flatMap(deleteCustomer -> customerRepository.delete(deleteCustomer).then(Mono.just(Boolean.TRUE)))
				.defaultIfEmpty(Boolean.FALSE);
	}

}
