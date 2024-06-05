package com.example.grocery.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.grocery.entities.Beer;
import com.example.grocery.entities.Bread;
import com.example.grocery.entities.BreadDiscount;
import com.example.grocery.entities.CartItem;
import com.example.grocery.entities.Inventory;
import com.example.grocery.entities.QuantityDiscount;
import com.example.grocery.entities.Vegetable;
import com.example.grocery.entities.VegetableDiscount;
import com.example.grocery.repositories.BeerRepository;
import com.example.grocery.repositories.BreadDiscountRepository;
import com.example.grocery.repositories.BreadRepository;
import com.example.grocery.repositories.CartItemRepository;
import com.example.grocery.repositories.InventoryRepository;
import com.example.grocery.repositories.QuantityDiscountRepository;
import com.example.grocery.repositories.VegetableDiscountRepository;
import com.example.grocery.repositories.VegetableRepository;

import jakarta.transaction.Transactional;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private BreadRepository breadRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private VegetableRepository vegetableRepository;
    @Autowired
    private QuantityDiscountRepository quantityDiscountRepository;
    @Autowired
    private BreadDiscountRepository breadDiscountRepository;
    @Autowired
    private VegetableDiscountRepository vegetableDiscountRepository;    
    

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);        
    }  

    @Transactional
    @Override
    public CartItem addCartItem(Long inventoryId, int quantity) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(()-> new RuntimeException("Inventory not found"));
        BigDecimal price = BigDecimal.ZERO;        

        CartItem cartItem = new CartItem();
        cartItem.setInventory(inventory);
        cartItem.setQuantity(quantity);
        

        if (inventory.getProductType().equals("Beer")) {
            Beer beer = beerRepository.findByInventoryId(inventoryId).orElseThrow(()-> new RuntimeException("Beer not found"));
            price = beer.getPricePerUnit().multiply(BigDecimal.valueOf(quantity));
            cartItem.setPrice(price);
            applyBeerDiscount(cartItem, beer);
        }
        else if (inventory.getProductType().equals("Bread")) {
            Bread bread = breadRepository.findByInventoryId(inventoryId).orElseThrow(()-> new RuntimeException("Bread not found"));
            price = bread.getPricePerUnit().multiply(BigDecimal.valueOf(quantity));
            cartItem.setPrice(price);
            applyBreadDiscount(cartItem, bread);
        }
        else if (inventory.getProductType().equals("Vegetable")) {
            Vegetable vegetable = vegetableRepository.findByInventoryId(inventoryId).orElseThrow(()-> new RuntimeException("Vegetable not found"));
            price = vegetable.getPricePer100g().multiply(BigDecimal.valueOf(quantity / 100.0));
            cartItem.setPrice(price);
            applyVegetableDiscount(cartItem, vegetable); //might consider splitting by 100 later
        }

        return cartItemRepository.save(cartItem);
    }

    
    @Override
    public CartItem updateCartItem(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItem.setQuantity(quantity);
        
        Inventory inventory = cartItem.getInventory();
        BigDecimal price = BigDecimal.ZERO;

        if (inventory.getProductType().equals("Beer")) {
            Beer beer = beerRepository.findByInventoryId(inventory.getId()).orElseThrow(()-> new RuntimeException("Beer not found"));
            price = beer.getPricePerUnit().multiply(BigDecimal.valueOf(quantity));
            cartItem.setPrice(price);
            applyBeerDiscount(cartItem, beer);
        }
        else if (inventory.getProductType().equals("Bread")) {
            Bread bread = breadRepository.findByInventoryId(inventory.getId()).orElseThrow(()-> new RuntimeException("Bread not found"));
            price = bread.getPricePerUnit().multiply(BigDecimal.valueOf(quantity));
            cartItem.setPrice(price);
            applyBreadDiscount(cartItem, bread);
        }
        else if (inventory.getProductType().equals("Vegetable")) {
            Vegetable vegetable = vegetableRepository.findByInventoryId(inventory.getId()).orElseThrow(()-> new RuntimeException("Bread not found"));
            price = vegetable.getPricePer100g().multiply(BigDecimal.valueOf(quantity / 100.0));
            cartItem.setPrice(price);
            applyVegetableDiscount(cartItem, vegetable); //might consider splitting by 100 later
        }

        return cartItemRepository.save(cartItem);
    }

    @Override
    public BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalAmount = totalAmount.add(cartItem.getPrice());
        }
        return totalAmount;
    }


    @Override
    public void applyBeerDiscount(CartItem cartItem, Beer beer) {
        Optional<QuantityDiscount> discountOpt = quantityDiscountRepository.findByBeerIdAndQUantity(beer.getId(), cartItem.getQuantity());

        if(discountOpt.isPresent()) {
            QuantityDiscount quantityDiscount = discountOpt.get();
            cartItem.setPrice(cartItem.getPrice().subtract(quantityDiscount.getDiscountAmount()));
        }
    }


    @Override
    public void applyBreadDiscount(CartItem cartItem, Bread bread) {
        BreadDiscount breadDiscount = breadDiscountRepository.findDiscountByDaysOld(bread.getAge());

        if (breadDiscount != null) {
            int discountedQuantity = cartItem.getQuantity() * breadDiscount.getQuantityMultiplier();
            BigDecimal discountedPrice = bread.getPricePerUnit().multiply(BigDecimal.valueOf(discountedQuantity));
            cartItem.setPrice(discountedPrice);
        }
    }


    @Override
    public void applyVegetableDiscount(CartItem cartItem, Vegetable vegetable) {
        VegetableDiscount discount = vegetableDiscountRepository.findDiscountByGrams(cartItem.getQuantity());

        if (discount != null) {
            BigDecimal discountAMount = cartItem.getPrice().multiply(discount.getDiscountPercentage());
            cartItem.setPrice(cartItem.getPrice().subtract(discountAMount));
        }

    }

    @Transactional
    @Override
    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
    
}
