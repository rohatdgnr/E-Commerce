import { Link } from "react-router-dom";
import { FiShoppingCart, FiUser } from "react-icons/fi";
import logo from "../assets/logo.png";
import "../styles/App.css";

const Header = () => {
    return (
        <header className="header">
            <div className="logo">
                <Link to="/">
                    <img src={logo} alt="Logo" />
                </Link>
            </div>
            <nav>
                <ul className="menu">
                    <li><Link to="/">Ana Sayfa</Link></li>
                    <li><Link to="/shop">Shop</Link></li>
                    <li className="submenu">
                        <Link to="/category">Category</Link>
                        <ul className="submenu-items">
                            <li><Link to="/category1">Araba Koleksiyonu</Link></li>
                            <li><Link to="/category2">Futbol Koleksiyonu</Link></li>
                            <li><Link to="/category3">Sanatçı Koleksiyonu</Link></li>
                            <li><Link to="/category4">Sanat Koleksiyonu</Link></li>
                            <li><Link to="/category5">Basketbol Koleksiyonu</Link></li>
                            <li><Link to="/category6">Mutlak Rekabet</Link></li>
                        </ul>
                    </li>
                    <li><Link to="/contact">İletişim</Link></li>
                </ul>
            </nav>
            <div className="header-actions">
                <Link to="/cart" className="cart-button">
                    <FiShoppingCart size={20} /> Sepet
                </Link>
                <Link to="/login" className="login-button">
                    <FiUser size={20} /> Giriş
                </Link>
            </div>
        </header>
    );
};

export default Header;
