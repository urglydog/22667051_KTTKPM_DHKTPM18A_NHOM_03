import axios from 'axios';

const GATEWAY_URL = 'http://192.168.1.10:8080/api';

export const login = (username, password) => 
    axios.post(`${GATEWAY_URL}/users/login`, { username, password });

export const getFoods = () => 
    axios.get(`${GATEWAY_URL}/foods`);

export const createOrder = (orderData) => 
    axios.post(`${GATEWAY_URL}/orders`, orderData);
