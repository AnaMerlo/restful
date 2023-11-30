package com.api.restful.repositories;

import com.api.restful.entities.Client;
import com.api.restful.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    void deleteByClient(Client client);
}
