package edu.avans.hartigehap.service.impl;

import com.google.common.collect.Lists;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.RestaurantRepository;
import edu.avans.hartigehap.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("restaurantService")
@Repository
@Transactional
public class RestaurantServiceImpl implements RestaurantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public List<Restaurant> findAll () {
        // MySQL and H2 return the restaurants of findAll() in different order
        // sorting the result makes the behavior less database vendor dependent
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return Lists.newArrayList(restaurantRepository.findAll(sort));
    }

    @Transactional(readOnly = true)
    public Restaurant findById (String restaurant) {
        return restaurantRepository.findOne(restaurant);
    }

    public Restaurant save (Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void delete (String restaurant) {
        restaurantRepository.delete(restaurant);
    }

    @Transactional(readOnly = true)
    public Page<Restaurant> findAllByPage (Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    /**
     * to be able to follow associations outside the context of a transaction, prefetch the associated
     * entities by traversing the associations
     */
    @Transactional(readOnly = true)
    public Restaurant fetchWarmedUp (String restaurantName) {
        Restaurant restaurant = restaurantRepository.findOne(restaurantName);
        restaurant.warmup();

        return restaurant;
    }

}
