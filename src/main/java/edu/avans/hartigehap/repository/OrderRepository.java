package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository
	extends PagingAndSortingRepository<Order, Long>, OrderRepositoryCustom {
	
	// Spring creates an implementation based one the method name
	// in this case all orders for which:
	// order.getOrderStatus() == orderStatus
	// AND
	// order.getBill().getDiningTable().getRestaurant() == restaurant
	List<Order> findByOrderStatusAndBillDiningTableRestaurant(
			Order.OrderStatus orderStatus, 
			Restaurant restaurant, 
			Sort sort);

	// compare to:
	// @NamedQuery(
	// name = "Order.findSubmittedOrders",
	// query =	"SELECT o FROM Order o " +
	//			"WHERE o.orderStatus = edu.avans.hartigehap.domain.Order$OrderStatus.SUBMITTED " +
	//			"AND o.bill.diningTable.restaurant = :restaurant " +
	//			"ORDER BY o.submittedTime")
	
}

