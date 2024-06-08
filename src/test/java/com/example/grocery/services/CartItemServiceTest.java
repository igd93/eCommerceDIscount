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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

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
        // Setup code if needed
    }

    @Test
    void findAll_ShouldReturnAllCartItems() {
        // Arrange
        CartItem cartItem1 = new CartItem();
        CartItem cartItem2 = new CartItem();
        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);
        when(cartItemRepository.findAll()).thenReturn(cartItems);
        when(modelMapper.map(any(CartItem.class), eq(CartItemDTO.class)))
                .thenAnswer(invocation -> new CartItemDTO());

        // Act
        List<CartItemDTO> result = cartItemService.findAll();

        // Assert
        assertThat(result).hasSize(2);
    }

    @Test
    void findById_ShouldReturnCartItem() {
        // Arrange
        CartItem cartItem = new CartItem();
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(cartItem));

        // Act
        CartItem result = cartItemService.findById(1L);

        // Assert
        assertThat(result).isEqualTo(cartItem);
    }

    @Test
    void save_ShouldSaveCartItem() {
        // Arrange
        CartItem cartItem = new CartItem();
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Act
        CartItem result = cartItemService.save(cartItem);

        // Assert
        assertThat(result).isEqualTo(cartItem);
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    // failing tests
    @Test
    void addCartItem_ShouldAddCartItemWithDiscount() {
        // Arrange
        Long inventoryId = 1L;
        int quantity = 5;
        Inventory inventory = new Inventory();
        inventory.setId(inventoryId);
        inventory.setProductType("Beer");
        CartItem cartItem = new CartItem();
        cartItem.setInventory(inventory);
        cartItem.setQuantity(quantity);

        Beer beer = new Beer();
        beer.setPricePerUnit(BigDecimal.valueOf(2.00));
        beer.setProductName("Beer");
        beer.setIsPack(false);
        beer.setId(1L);

        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(inventory));
        when(cartItemRepository.findByInventoryId(anyLong())).thenReturn(Optional.empty());
        when(beerRepository.findByInventoryId(anyLong())).thenReturn(Optional.of(beer));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(modelMapper.map(any(CartItem.class), eq(CartItemDTO.class)))
                .thenAnswer(invocation -> new CartItemDTO());

        // Act
        CartItemDTO result = cartItemService.addCartItem(inventoryId, quantity);

        // Assert
        assertThat(result).isNotNull();
        verify(inventoryRepository, times(1)).findById(inventoryId);
        verify(cartItemRepository, times(1)).findByInventoryId(inventoryId);
        verify(beerRepository, times(1)).findByInventoryId(inventoryId);
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    void updateCartItem_ShouldUpdateCartItem() {
        // Arrange
        Long cartItemId = 1L;
        int quantity = 10;
        CartItem cartItem = new CartItem();
        Inventory inventory = new Inventory();
        inventory.setProductType("Beer");
        inventory.setId(1L);
        cartItem.setInventory(inventory);
        cartItem.setQuantity(5);

        Beer beer = new Beer();
        beer.setPricePerUnit(BigDecimal.valueOf(2.00));
        beer.setProductName("Beer");
        beer.setId(1L);
        beer.setInventory(inventory);
        beer.setIsPack(false);

        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(cartItem));
        when(beerRepository.findByInventoryId(anyLong())).thenReturn(Optional.of(beer));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(modelMapper.map(any(CartItem.class), eq(CartItemDTO.class)))
                .thenAnswer(invocation -> new CartItemDTO());

        // Act
        CartItemDTO result = cartItemService.updateCartItem(cartItemId, quantity);

        // Assert
        assertThat(result).isNotNull();
        assertThat(cartItem.getQuantity()).isEqualTo(quantity);
        verify(cartItemRepository, times(1)).findById(cartItemId);
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void calculateTotalAmount_ShouldReturnTotalAmount() {
        // Arrange
        CartItem cartItem1 = new CartItem();
        cartItem1.setPrice(BigDecimal.valueOf(2.00));
        CartItem cartItem2 = new CartItem();
        cartItem2.setPrice(BigDecimal.valueOf(20.00));
        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);

        // Act
        BigDecimal result = cartItemService.calculateTotalAmount(cartItems);

        // Assert
        assertThat(result).isEqualTo(BigDecimal.valueOf(22.00));
    }

    @Test
    void removeCartItem_ShouldRemoveCartItem() {
        // Arrange
        Long cartItemId = 1L;
        doNothing().when(cartItemRepository).deleteById(anyLong());

        // Act
        cartItemService.removeCartItem(cartItemId);

        // Assert
        verify(cartItemRepository, times(1)).deleteById(cartItemId);
    }

    @Test
    void applyBeerDiscount_ShouldApplyDiscount() {
        // Arrange
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(10);

        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setProductType("Beer");
        Beer beer = new Beer();
        beer.setId(1L);
        beer.setProductName("Dutch");
        beer.setPricePerUnit(BigDecimal.valueOf(2.00));
        beer.setIsPack(false);
        beer.setInventory(inventory);

        cartItem.setInventory(inventory);
        cartItem.setPrice(BigDecimal.valueOf(20.00));
        cartItem.setProductName(beer.getProductName());
        QuantityDiscount discount = new QuantityDiscount();
        discount.setQuantity(6);
        discount.setBeer(beer);
        discount.setInventory(inventory);
        discount.setId(1L);
        discount.setDiscountAmount(BigDecimal.valueOf(2.00));

        when(quantityDiscountRepository.findByBeerId(anyLong())).thenReturn(Optional.of(discount));

        // Act
        cartItemService.applyBeerDiscount(cartItem, beer);

        // Assert
        assertThat(cartItem.getPrice()).isEqualTo(BigDecimal.valueOf(18.00));
    }

    @Test
    void applyBreadDiscount_ShouldApplyDiscount() {
        // Arrange
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(10);

        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setProductType("Bread");
        Bread bread = new Bread();
        bread.setId(1L);
        bread.setPricePerUnit(BigDecimal.valueOf(2.00));
        bread.setAge(4);
        bread.setProductName("Ciabatta");
        bread.setInventory(inventory);

        cartItem.setPrice(BigDecimal.valueOf(20.00));

        BreadDiscount discount = new BreadDiscount();
        discount.setQuantityMultiplier(2);
        discount.setId(1L);
        discount.setMinDaysOld(3);
        discount.setMaxDaysOld(5);

        when(breadDiscountRepository.findDiscountByDaysOld(anyInt())).thenReturn(
                discount);

        // Act
        cartItemService.applyBreadDiscount(cartItem, bread);

        // Assert
        assertThat(cartItem.getPrice()).isEqualTo(BigDecimal.valueOf(20.00));
        assertThat(cartItem.getQuantity()).isEqualTo(20);
    }

    @Test
    void applyVegetableDiscount_ShouldApplyDiscount() {

        // Arrange
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setProductType("Vegetable");
        Vegetable vegetable = new Vegetable();
        vegetable.setId(1L);
        vegetable.setPricePer100g(BigDecimal.valueOf(1.00));
        vegetable.setProductName("Carrot");
        vegetable.setInventory(inventory);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(200);
        cartItem.setInventory(inventory);
        cartItem.setProductName(vegetable.getProductName());
        cartItem.setInventory(inventory);
        cartItem.setPrice(BigDecimal.valueOf(2.00));

        VegetableDiscount discount = new VegetableDiscount();
        discount.setDiscountPercentage(BigDecimal.valueOf(0.10));
        discount.setMinGrams(101);
        discount.setMaxGrams(300);

        when(vegetableDiscountRepository.findDiscountByGrams(anyInt())).thenReturn(
                discount);

        // Act
        cartItemService.applyVegetableDiscount(cartItem, vegetable);

        // Assert
        assertThat(cartItem.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(1.80));
    }

}
