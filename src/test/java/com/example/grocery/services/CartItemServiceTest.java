package com.example.grocery.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.grocery.dto.CartItemDTO;
import com.example.grocery.entities.*;
import com.example.grocery.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

class CartItemServicelTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private BeerRepository beerRepository;

    @Mock
    private BreadRepository breadRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private VegetableRepository vegetableRepository;

    @Mock
    private QuantityDiscountRepository quantityDiscountRepository;

    @Mock
    private BreadDiscountRepository breadDiscountRepository;

    @Mock
    private VegetableDiscountRepository vegetableDiscountRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /*
     * @Test
     * void findAll_ShouldReturnAllCartItems() {
     * // Arrange
     * CartItem cartItem1 = new CartItem();
     * CartItem cartItem2 = new CartItem();
     * List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);
     * when(cartItemRepository.findAll()).thenReturn(cartItems);
     * when(modelMapper.map(any(CartItem.class), eq(CartItemDTO.class)))
     * .thenAnswer(invocation -> new CartItemDTO());
     * 
     * // Act
     * List<CartItemDTO> result = cartItemService.findAll();
     * 
     * // Assert
     * assertThat(result).hasSize(2);
     * }
     * 
     * @Test
     * void findById_ShouldReturnCartItem() {
     * // Arrange
     * CartItem cartItem = new CartItem();
     * when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(cartItem)
     * );
     * 
     * // Act
     * CartItem result = cartItemService.findById(1L);
     * 
     * // Assert
     * assertThat(result).isEqualTo(cartItem);
     * }
     * 
     * @Test
     * void save_ShouldSaveCartItem() {
     * // Arrange
     * CartItem cartItem = new CartItem();
     * when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
     * 
     * // Act
     * CartItem result = cartItemService.save(cartItem);
     * 
     * // Assert
     * assertThat(result).isEqualTo(cartItem);
     * verify(cartItemRepository, times(1)).save(cartItem);
     * }
     * 
     * @Test
     * void addCartItem_ShouldAddCartItemWithDiscount() {
     * // Arrange
     * Long inventoryId = 1L;
     * int quantity = 5;
     * Inventory inventory = new Inventory();
     * inventory.setId(inventoryId);
     * inventory.setProductType("Beer");
     * CartItem cartItem = new CartItem();
     * cartItem.setInventory(inventory);
     * cartItem.setQuantity(quantity);
     * 
     * Beer beer = new Beer();
     * beer.setPricePerUnit(BigDecimal.valueOf(2.00));
     * beer.setProductName("Beer");
     * beer.setId(1L);
     * 
     * when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(
     * inventory));
     * when(cartItemRepository.findByInventoryId(anyLong())).thenReturn(Optional.
     * empty());
     * when(beerRepository.findByInventoryId(anyLong())).thenReturn(Optional.of(beer
     * ));
     * when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
     * when(modelMapper.map(any(CartItem.class), eq(CartItemDTO.class)))
     * .thenAnswer(invocation -> new CartItemDTO());
     * 
     * // Act
     * CartItemDTO result = cartItemService.addCartItem(inventoryId, quantity);
     * 
     * // Assert
     * assertThat(result).isNotNull();
     * verify(inventoryRepository, times(1)).findById(inventoryId);
     * verify(cartItemRepository, times(1)).findByInventoryId(inventoryId);
     * verify(beerRepository, times(1)).findByInventoryId(inventoryId);
     * verify(cartItemRepository, times(1)).save(any(CartItem.class));
     * }
     * 
     * @Test
     * void updateCartItem_ShouldUpdateCartItem() {
     * // Arrange
     * Long cartItemId = 1L;
     * int quantity = 10;
     * CartItem cartItem = new CartItem();
     * Inventory inventory = new Inventory();
     * inventory.setProductType("Beer");
     * cartItem.setInventory(inventory);
     * cartItem.setQuantity(5);
     * 
     * Beer beer = new Beer();
     * beer.setPricePerUnit(BigDecimal.valueOf(2.00));
     * beer.setProductName("Beer");
     * beer.setId(1L);
     * 
     * when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(cartItem)
     * );
     * when(beerRepository.findByInventoryId(anyLong())).thenReturn(Optional.of(beer
     * ));
     * when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
     * when(modelMapper.map(any(CartItem.class), eq(CartItemDTO.class)))
     * .thenAnswer(invocation -> new CartItemDTO());
     * 
     * // Act
     * CartItemDTO result = cartItemService.updateCartItem(cartItemId, quantity);
     * 
     * // Assert
     * assertThat(result).isNotNull();
     * assertThat(cartItem.getQuantity()).isEqualTo(quantity);
     * verify(cartItemRepository, times(1)).findById(cartItemId);
     * verify(cartItemRepository, times(1)).save(cartItem);
     * }
     * 
     * @Test
     * void calculateTotalAmount_ShouldReturnTotalAmount() {
     * // Arrange
     * CartItem cartItem1 = new CartItem();
     * cartItem1.setPrice(BigDecimal.valueOf(2.00));
     * CartItem cartItem2 = new CartItem();
     * cartItem2.setPrice(BigDecimal.valueOf(20));
     * List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);
     * 
     * // Act
     * BigDecimal result = cartItemService.calculateTotalAmount(cartItems);
     * 
     * // Assert
     * assertThat(result).isEqualTo(BigDecimal.valueOf(30));
     * }
     * 
     * @Test
     * void removeCartItem_ShouldRemoveCartItem() {
     * // Arrange
     * Long cartItemId = 1L;
     * doNothing().when(cartItemRepository).deleteById(anyLong());
     * 
     * // Act
     * cartItemService.removeCartItem(cartItemId);
     * 
     * // Assert
     * verify(cartItemRepository, times(1)).deleteById(cartItemId);
     * }
     * 
     * @Test
     * void applyBeerDiscount_ShouldApplyDiscount() {
     * // Arrange
     * CartItem cartItem = new CartItem();
     * cartItem.setQuantity(10);
     * Beer beer = new Beer();
     * beer.setId(1L);
     * beer.setPricePerUnit(BigDecimal.valueOf(2.00));
     * QuantityDiscount discount = new QuantityDiscount();
     * discount.setQuantity(5);
     * discount.setDiscountAmount(BigDecimal.ONE);
     * 
     * when(quantityDiscountRepository.findByBeerId(anyLong())).thenReturn(Optional.
     * of(discount));
     * 
     * // Act
     * cartItemService.applyBeerDiscount(cartItem, beer);
     * 
     * // Assert
     * assertThat(cartItem.getPrice()).isEqualTo(BigDecimal.valueOf(90));
     * }
     * 
     * @Test
     * void applyBreadDiscount_ShouldApplyDiscount() {
     * // Arrange
     * CartItem cartItem = new CartItem();
     * cartItem.setQuantity(10);
     * Bread bread = new Bread();
     * bread.setId(1L);
     * bread.setPricePerUnit(BigDecimal.valueOf(2.00));
     * bread.setAge(1);
     * BreadDiscount discount = new BreadDiscount();
     * discount.setQuantityMultiplier(2);
     * 
     * when(breadDiscountRepository.findDiscountByDaysOld(anyInt())).thenReturn(
     * discount);
     * 
     * // Act
     * cartItemService.applyBreadDiscount(cartItem, bread);
     * 
     * // Assert
     * assertThat(cartItem.getPrice()).isEqualTo(BigDecimal.valueOf(20));
     * }
     * 
     * @Test
     * void applyVegetableDiscount_ShouldApplyDiscount() {
     * // Arrange
     * CartItem cartItem = new CartItem();
     * cartItem.setQuantity(200);
     * Vegetable vegetable = new Vegetable();
     * vegetable.setId(1L);
     * vegetable.setPricePer100g(BigDecimal.valueOf(1.00));
     * VegetableDiscount discount = new VegetableDiscount();
     * discount.setDiscountPercentage(BigDecimal.valueOf(0.1));
     * 
     * when(vegetableDiscountRepository.findDiscountByGrams(anyInt())).thenReturn(
     * discount);
     * 
     * // Act
     * cartItemService.applyVegetableDiscount(cartItem, vegetable);
     * 
     * // Assert
     * assertThat(cartItem.getPrice()).isEqualTo(BigDecimal.valueOf(18));
     * }
     */
}
