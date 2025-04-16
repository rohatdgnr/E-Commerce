import React from "react";
import { useCart } from "../context/CartContext"; // Sepet context'ini kullan
import "../styles/Shop.css";

import shoes1 from "../assets/95.jpeg";
import shoes2 from "../assets/96.jpeg";
import shoes3 from "../assets/97.jpeg";
import shoes4 from "../assets/98.jpeg";

const products = [
    { id: 1, name: "SOME SHOES", price: 15.00, image: shoes1, description: "Lorem ipsum dolor sit amet" },
    { id: 2, name: "SOME SHOES", price: 15.00, image: shoes2, description: "Lorem ipsum dolor sit amet" },
    { id: 3, name: "SOME SHOES", price: 15.00, image: shoes3, description: "Lorem ipsum dolor sit amet" },
    { id: 4, name: "SOME SHOES", price: 15.00, image: shoes4, description: "Lorem ipsum dolor sit amet" },
    { id: 5, name: "SOME SHOES", price: 15.00, image: shoes1, description: "Lorem ipsum dolor sit amet" },
    { id: 6, name: "SOME SHOES", price: 15.00, image: shoes2, description: "Lorem ipsum dolor sit amet" },
    { id: 7, name: "SOME SHOES", price: 15.00, image: shoes3, description: "Lorem ipsum dolor sit amet" },
    { id: 8, name: "SOME SHOES", price: 15.00, image: shoes4, description: "Lorem ipsum dolor sit amet" },
    { id: 9, name: "SOME SHOES", price: 15.00, image: shoes1, description: "Lorem ipsum dolor sit amet" },
    { id: 10, name: "SOME SHOES", price: 15.00, image: shoes2, description: "Lorem ipsum dolor sit amet" },
    { id: 11, name: "SOME SHOES", price: 15.00, image: shoes3, description: "Lorem ipsum dolor sit amet" },
    { id: 12, name: "SOME SHOES", price: 15.00, image: shoes4, description: "Lorem ipsum dolor sit amet" }
];

const Shop = () => {
    const { addToCart } = useCart(); // Sepete ekleme fonksiyonunu çağır

    return (
        <section className="shop-container">
            <h1>882 STORE</h1>
            <div className="list">
                {products.map((product) => (
                    <div key={product.id} className="product">
                        <img src={product.image} alt={product.name} />
                        <div>
                            <h2>{product.name}</h2>
                            <p className="price">{product.price}<sup>.00</sup> TL</p>
                            <p className="descr">{product.description}</p>
                            <br />
                            <button onClick={() => addToCart(product)}>Add to cart</button>
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
};

export default Shop;
