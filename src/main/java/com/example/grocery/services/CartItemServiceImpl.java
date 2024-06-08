package com.example.grocery.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.grocery.dto.CartItemDTO;
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
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final BeerRepository beerRepository;
    private final BreadRepository breadRepository;
    private final InventoryRepository inventoryRepository;
    private final VegetableRepository vegetableRepository;
    private final QuantityDiscountRepository quantityDiscountRepository;
    private final BreadDiscountRepository breadDiscountRepository;
    private final VegetableDiscountRepository vegetableDiscountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository,
            BeerRepository beerRepository,
            BreadRepository breadRepository,
            InventoryRepository inventoryRepository,
            VegetableRepository vegetableRepository,
            QuantityDiscountRepository quantityDiscountRepository,
            BreadDiscountRepository breadDiscountRepository,
            VegetableDiscountRepository vegetableDiscountRepository,
            ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.beerRepository = beerRepository;
        this.breadRepository = breadRepository;
        this.inventoryRepository = inventoryRepository;
        this.vegetableRepository = vegetableRepository;
        this.quantityDiscountRepository = quantityDiscountRepository;
        this.breadDiscountRepository = breadDiscountRepository;
        this.vegetableDiscountRepository = vegetableDiscountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CartItemDTO> findAll() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return cartItems.stream().map(this::convertToDTO).collect(Collectors.toList());
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
    public CartItemDTO addCartItem(Long inventoryId, int quantity) {

        CartItem cartItem;
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        Optional<CartItem> existingCartItemOpt = cartItemRepository.findByInventoryId(inventoryId);

        if (existingCartItemOpt.isPresent()) {
            cartItem = existingCartItemOpt.get();
            quantity += cartItem.getQuantity();
            cartItem.setQuantity(quantity);
        }

        else {
            cartItem = new CartItem();
            cartItem.setInventory(inventory);
            cartItem.setQuantity(quantity);
        }

        BigDecimal price = BigDecimal.ZERO;
        if (inventory.getProductType().equals("Beer")) {
            Beer beer = beerRepository.findByInventoryId(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Beer not found"));
            price = beer.getPricePerUnit().multiply(BigDecimal.valueOf(quantity));
            cartItem.setPrice(price);
            cartItem.setProductName(beer.getProductName());
            applyBeerDiscount(cartItem, beer);
        } else if (inventory.getProductType().equals("Bread")) {
            Bread bread = breadRepository.findByInventoryId(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Bread not found"));
            price = bread.getPricePerUnit().multiply(BigDecimal.valueOf(quantity));
            cartItem.setPrice(price);
            cartItem.setProductName(bread.getProductName());
            applyBreadDiscount(cartItem, bread);
        } else if (inventory.getProductType().equals("Vegetable")) {
            Vegetable vegetable = vegetableRepository.findByInventoryId(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Vegetable not found"));
            price = vegetable.getPricePer100g().multiply(BigDecimal.valueOf(quantity / 100.0));
            cartItem.setPrice(price);
            cartItem.setProductName(vegetable.getProductName());
            applyVegetableDiscount(cartItem, vegetable);
        }

        cartItem = cartItemRepository.save(cartItem);
        return convertToDTO(cartItem);
    }

    @Override
    public CartItemDTO updateCartItem(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItem.setQuantity(quantity);

        Inventory inventory = cartItem.getInventory();
        BigDecimal price = BigDecimal.ZERO;

        if (inventory.getProductType().equals("Beer")) {
            Beer beer = beerRepository.findByInventoryId(inventory.getId())
                    .orElseThrow(() -> new RuntimeException("Beer not found"));
            price = beer.getPricePerUnit().multiply(BigDecimal.valueOf(quantity));
            cartItem.setPrice(price);
            cartItem.setProductName(beer.getProductName());
            applyBeerDiscount(cartItem, beer);
        } else if (inventory.getProductType().equals("Bread")) {
            Bread bread = breadRepository.findByInventoryId(inventory.getId())
                    .orElseThrow(() -> new RuntimeException("Bread not found"));
            price = bread.getPricePerUnit().multiply(BigDecimal.valueOf(quantity));
            cartItem.setPrice(price);
            cartItem.setProductName(bread.getProductName());
            applyBreadDiscount(cartItem, bread);
        } else if (inventory.getProductType().equals("Vegetable")) {
            Vegetable vegetable = vegetableRepository.findByInventoryId(inventory.getId())
                    .orElseThrow(() -> new RuntimeException("Vegetable not found"));
            price = vegetable.getPricePer100g().multiply(BigDecimal.valueOf(quantity / 100.0));
            cartItem.setPrice(price);
            cartItem.setProductName(vegetable.getProductName());
            applyVegetableDiscount(cartItem, vegetable);
        }

        cartItem = cartItemRepository.save(cartItem);
        return convertToDTO(cartItem);

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
        Optional<QuantityDiscount> discountOpt = quantityDiscountRepository.findByBeerId(beer.getId());

        if (discountOpt.isPresent()) {
            QuantityDiscount quantityDiscount = discountOpt.get();
            int discountQuantity = quantityDiscount.getQuantity();
            int applicableMultiplier = cartItem.getQuantity() / discountQuantity;
            if (applicableMultiplier > 0) {
                BigDecimal totalDiscount = quantityDiscount.getDiscountAmount()
                        .multiply(BigDecimal.valueOf(applicableMultiplier));
                cartItem.setPrice(cartItem.getPrice().subtract(totalDiscount));
            }
        }
    }

    @Override
    public void applyBreadDiscount(CartItem cartItem, Bread bread) {
        BreadDiscount breadDiscount = breadDiscountRepository.findDiscountByDaysOld(bread.getAge());

        if (breadDiscount != null) {
            int discountedQuantity = cartItem.getQuantity() * breadDiscount.getQuantityMultiplier();
            cartItem.setQuantity(discountedQuantity);
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

    private CartItemDTO convertToDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }

}
