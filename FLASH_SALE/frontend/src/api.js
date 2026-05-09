import axios from 'axios';

const PRODUCT_URL = import.meta.env.VITE_PRODUCT_URL || 'http://localhost:8081';
const CART_URL = import.meta.env.VITE_CART_URL || 'http://localhost:8082';
const ORDER_URL = import.meta.env.VITE_ORDER_URL || 'http://localhost:8083';
const INVENTORY_URL = import.meta.env.VITE_INVENTORY_URL || 'http://localhost:8084';
const USER_URL = import.meta.env.VITE_USER_URL || 'http://localhost:8085';

export const getProducts = () => axios.get(`${PRODUCT_URL}/api/products`);
export const getProduct = (id) => axios.get(`${PRODUCT_URL}/api/products/${id}`);
export const getStock = (id) => axios.get(`${INVENTORY_URL}/api/stock/${id}`);
export const getUser = (id) => axios.get(`${USER_URL}/api/users/${id}`);

export const addToCart = (userId, item) => axios.post(`${CART_URL}/api/cart/add?userId=${userId}`, item);
export const getCart = (userId) => axios.get(`${CART_URL}/api/cart/${userId}`);

export const checkout = (userId) => axios.post(`${ORDER_URL}/api/orders/checkout?userId=${userId}`);
