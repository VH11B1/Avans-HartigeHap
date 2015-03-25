package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.Bill;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.StateException;

import java.util.List;

public interface BillService {
    Bill findById (Long billId);

    void billHasBeenPaid (Bill bill) throws StateException;

    List<Bill> findSubmittedBillsForRestaurant (Restaurant restaurant);
}
