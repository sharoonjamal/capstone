package learn.aaron.closet.shop.models;


import java.sql.Timestamp;

public class Cart {
    private Long cartId;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private Timestamp addedToCart;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Timestamp getAddedToCart() {
        return addedToCart;
    }

    public void setAddedToCart(Timestamp addedToCart) {
        this.addedToCart = addedToCart;
    }
}
