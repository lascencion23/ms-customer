package com.java.everis.client.service;

import com.java.everis.client.entity.Customer;
import com.java.everis.client.entity.TypeCustomer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService  {
    public Mono<Customer> create(Customer customer);

    public Flux<Customer> findAll();

    public Mono<Customer> findById(String id);

    public Mono<Customer> update(Customer customer);

    public Mono<Boolean> delete(String id);

    public Mono<TypeCustomer> findTypeCustomer(String id);
}
