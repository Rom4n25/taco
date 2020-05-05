/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.romanek.tacocloud.repository;

import pl.romanek.tacocloud.domain.Order;

/**
 *
 * @author Roman
 */
public interface OrderRepository {
    
    Order save(Order order);
    
}
