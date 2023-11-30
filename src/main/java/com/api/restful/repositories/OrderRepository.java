package com.api.restful.repositories;

import com.api.restful.entities.Client;
import com.api.restful.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByClient(Client client);
    void deleteByClient(Client client);
}
