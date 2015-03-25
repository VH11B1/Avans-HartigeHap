package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.EmptyBillException;
import edu.avans.hartigehap.domain.StateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiningTableService {

    List<DiningTable> findAll ();

    DiningTable findById (Long id);

    DiningTable save (DiningTable diningTable);

    void delete (Long id);

    Page<DiningTable> findAllByPage (Pageable pageable);

    DiningTable fetchWarmedUp (Long diningTableId);

    void addOrderItem (DiningTable diningTable, String menuItemName);

    void deleteOrderItem (DiningTable diningTable, String menuItemName);

    void submitOrder (DiningTable diningTable) throws StateException;

    void submitBill (DiningTable diningTable) throws StateException, EmptyBillException;
}