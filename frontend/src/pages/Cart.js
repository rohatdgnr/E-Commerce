import React from "react";
import { useCart } from "../context/CartContext"; // Sepet context'ini kullan
import "../styles/Cart.css";

const Cart = () => {
    const { cart, removeFromCart, clearCart } = useCart();

    return (
        <section className="cart-container">
            <h1>Sepetiniz</h1>
            {cart.length === 0 ? (
                <p>Sepetiniz boş.</p>
            ) : (
                <div className="cart-items">
                    {cart.map((item) => (
                        <div key={item.id} className="cart-item">
                            <img src={item.image} alt={item.name} />
                            <div>
                                <h2>{item.name}</h2>
                                <p className="price">{item.price} TL</p>
                                <button onClick={() => removeFromCart(item.id)}>Kaldır</button>
                            </div>
                        </div>
                    ))}
                    <button className="checkout-btn" onClick={clearCart}>Siparişi Tamamla</button>
                </div>
            )}
        </section>
    );
};

export default Cart;
