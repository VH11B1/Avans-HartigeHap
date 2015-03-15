package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.Bill;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.repository.BillRepository;
import edu.avans.hartigehap.repository.OrderRepository;
import edu.avans.hartigehap.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("billService")
@Repository
@Transactional(rollbackFor = StateException.class)
public class BillServiceImpl implements BillService {
	final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private BillRepository billRepository;

	@Transactional(readOnly=true)
	public Bill findById(Long billId) {
		return billRepository.findOne(billId);
	}

	
	public void billHasBeenPaid(Bill bill) throws StateException {
		bill.paid();
	}
	

	@Transactional(readOnly=true)
	public List<Bill> findSubmittedBillsForRestaurant(Restaurant restaurant) {
	// a query created using a repository method name
	List<Bill> submittedBillsList = billRepository.
			findByBillStatusAndDiningTableRestaurant(
					Bill.BillStatus.SUBMITTED, 
					restaurant,
					new Sort(Sort.Direction.ASC, "submittedTime"));

	return submittedBillsList;

	}	
}
