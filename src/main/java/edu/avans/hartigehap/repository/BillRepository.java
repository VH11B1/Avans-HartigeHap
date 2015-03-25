package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.Bill;
import edu.avans.hartigehap.domain.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {

    // Spring creates an implementation based one the method name
    // in this case all orders for which:
    // bill.getBillStatus() == billStatus
    // AND
    // bill.getDiningTable().getRestaurant() == restaurant
    List<Bill> findByBillStatusAndDiningTableRestaurant (
            Bill.BillStatus billStatus,
            Restaurant restaurant,
            Sort sort);
}