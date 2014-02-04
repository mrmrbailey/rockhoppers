/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mxbailey
 */
public class ShoppingCart {
    
private final Map<User, List<Product>>    entries    = new HashMap<User, List<Product>>();
 
 public void add(final User user, final Product product) {
 List<Product> purchases;
 if (entries.containsKey(user)) {
 purchases = entries.get(user);
 } else {
 purchases = new ArrayList<Product>();
 }
 purchases.add(product);
 entries.put(user, purchases);
 }
 
 public List<Product> getProductsByUser(final User user) {
 return entries.get(user);
 }
 
}