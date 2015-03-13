package edu.avans.hartigehap.web.controller.pe;

import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.service.RestaurantService;

import java.beans.PropertyEditorSupport;

/**
 * Created by Bob on 11/03/15.
 */
public class RestaurantEditor extends PropertyEditorSupport {

    private final RestaurantService restaurantService;

    public RestaurantEditor(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Restaurant restaurant = restaurantService.findById(text);
        setValue(restaurant);
    }

}
