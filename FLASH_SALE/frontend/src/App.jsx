import React, { useState, useEffect } from 'react';
import { ShoppingCart, Zap, Package, ShoppingBag, X, User as UserIcon, LogOut } from 'lucide-react';
import * as api from './api';
import './App.css';

function App() {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [stocks, setStocks] = useState({});
  const [user, setUser] = useState(null);
  const [currentUserId, setCurrentUserId] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isCartOpen, setIsCartOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);

  useEffect(() => {
    fetchData();
    const interval = setInterval(fetchStocks, 3000);
    return () => clearInterval(interval);
  }, []);

  const fetchData = async () => {
    try {
      const res = await api.getProducts();
      setProducts(res.data);
      fetchStocks();
    } catch (e) {
      console.error("Error fetching products", e);
    }
  };

  const fetchStocks = async () => {
    try {
      const res = await api.getProducts();
      const newStocks = {};
      for (const p of res.data) {
        const stockRes = await api.getStock(p.id);
        newStocks[p.id] = stockRes.data;
      }
      setStocks(newStocks);
    } catch (e) {}
  };

  const loadCart = async (userId) => {
    try {
      const res = await api.getCart(userId);
      setCart(res.data);
    } catch (e) {}
  };

  const handleLogout = () => {
    setUser(null);
    setCurrentUserId(null);
    setIsLoggedIn(false);
    setCart([]);
    setMessage({ type: 'info', text: 'You have logged out.' });
    setTimeout(() => setMessage(null), 2000);
  };

  const handleLogin = async () => {
    const id = prompt("Enter User ID (user_1, user_2, user_3):", "user_1");
    if (id) {
      try {
        const res = await api.getUser(id);
        if (res.data) {
          setUser(res.data);
          setCurrentUserId(id);
          setIsLoggedIn(true);
          loadCart(id);
          setMessage({ type: 'success', text: `Welcome back, ${res.data.name}!` });
          setTimeout(() => setMessage(null), 3000);
        } else {
          alert("User not found!");
        }
      } catch (e) {
        alert("Login failed! Make sure User Service is running.");
      }
    }
  };

  const handleAddToCart = async (product) => {
    if (!isLoggedIn) {
      setMessage({ type: 'error', text: 'Please login to shop!' });
      setTimeout(() => setMessage(null), 3000);
      return;
    }
    try {
      await api.addToCart(currentUserId, {
        productId: product.id,
        productName: product.name,
        quantity: 1,
        price: product.price
      });
      loadCart(currentUserId);
      setMessage({ type: 'success', text: `Added ${product.name} to cart!` });
      setTimeout(() => setMessage(null), 3000);
    } catch (e) {
      setMessage({ type: 'error', text: 'Failed to add to cart' });
    }
  };

  const handleCheckout = async () => {
    setLoading(true);
    try {
      const res = await api.checkout(currentUserId);
      if (res.data.success) {
        setMessage({ type: 'success', text: res.data.message });
        setCart([]);
        setIsCartOpen(false);
        fetchStocks();
      } else {
        setMessage({ type: 'error', text: res.data.message });
      }
    } catch (e) {
      setMessage({ type: 'error', text: 'Checkout failed' });
    }
    setLoading(false);
  };

  return (
    <div className="app-container">
      <header className="header">
        <div className="logo" onClick={() => window.location.reload()}>
          <Zap className="zap-icon" />
          <span>FLASH SALE</span>
        </div>
        
        <div className="user-info">
          {isLoggedIn && user ? (
            <div className="user-section">
              <div className="user-badge">
                <UserIcon size={18} />
                <div className="user-details">
                  <span className="user-name">{user.name}</span>
                  <span className="user-rank">{user.rank} Member</span>
                </div>
              </div>
              <button className="logout-btn" onClick={handleLogout} title="Logout">
                <LogOut size={18} />
              </button>
            </div>
          ) : (
            <button className="login-btn" onClick={handleLogin}>Login</button>
          )}
          <div className="cart-trigger" onClick={() => setIsCartOpen(true)}>
            <ShoppingCart />
            <span className="cart-count">{cart.length}</span>
          </div>
        </div>
      </header>

      <main className="main">
        {message && (
          <div className={`alert ${message.type}`}>
            {message.text}
          </div>
        )}

        <div className="hero-section">
          <h1>Don't Miss Out!</h1>
          <p>The biggest sale of the year is live now.</p>
        </div>

        <div className="product-grid">
          {products.map(product => (
            <div key={product.id} className="product-card">
              <div className="product-image">
                <Package size={48} />
              </div>
              <div className="product-info">
                <h3>{product.name}</h3>
                <p className="price">${product.price}</p>
                <div className="stock-bar">
                  <div 
                    className="stock-fill" 
                    style={{ width: `${Math.min(100, (stocks[product.id] || 0))}%` }}
                  ></div>
                </div>
                <p className={`stock-text ${stocks[product.id] <= 10 ? 'low' : ''}`}>
                  {stocks[product.id] || 0} items left
                </p>
                <button 
                  onClick={() => handleAddToCart(product)}
                  disabled={stocks[product.id] <= 0}
                  className="add-btn"
                >
                  {stocks[product.id] <= 0 ? 'Out of Stock' : 'Add to Cart'}
                </button>
              </div>
            </div>
          ))}
        </div>
      </main>

      {isCartOpen && (
        <div className="cart-overlay" onClick={() => setIsCartOpen(false)}>
          <div className="cart-sidebar" onClick={e => e.stopPropagation()}>
            <div className="cart-header">
              <h2>Your Cart</h2>
              <X onClick={() => setIsCartOpen(false)} style={{ cursor: 'pointer' }} />
            </div>
            <div className="cart-items">
              {cart.length === 0 ? (
                <div className="empty-cart">
                  <ShoppingBag size={48} />
                  <p>Your cart is empty</p>
                </div>
              ) : cart.map((item, i) => (
                <div key={i} className="cart-item">
                  <div className="item-details">
                    <span className="item-name">{item.productName}</span>
                    <span className="item-meta">x{item.quantity}</span>
                  </div>
                  <span className="item-price">${item.price * item.quantity}</span>
                </div>
              ))}
            </div>
            {cart.length > 0 && (
              <div className="cart-footer">
                <div className="total-row">
                  <span>Subtotal</span>
                  <span>${cart.reduce((acc, item) => acc + (item.price * item.quantity), 0)}</span>
                </div>
                <button className="checkout-btn" onClick={handleCheckout} disabled={loading}>
                  {loading ? 'Processing...' : 'Checkout Now'}
                </button>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
