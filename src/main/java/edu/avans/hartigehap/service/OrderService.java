package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.StateException;

import java.util.List;

public interface OrderService {
	Order findById(Long orderId);
	List<Order> findSubmittedOrdersForRestaurant(Restaurant restaurant);	
	List<Order> findPlannedOrdersForRestaurant(Restaurant restaurant);	
	List<Order> findPreparedOrdersForRestaurant(Restaurant restaurant);
	void planOrder(Order order) throws StateException;
	void orderPrepared(Order order) throws StateException;
	void orderServed(Order order) throws StateException;
	
}
