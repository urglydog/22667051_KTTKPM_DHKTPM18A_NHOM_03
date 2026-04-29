const DEFAULT_HEADERS = {
  'Content-Type': 'application/json'
};

async function request(path, options = {}) {
  const response = await fetch(`/api${path}`, {
    ...options,
    headers: {
      ...DEFAULT_HEADERS,
      ...(options.headers || {})
    }
  });

  const text = await response.text();
  let payload = null;

  if (text) {
    try {
      payload = JSON.parse(text);
    } catch {
      payload = text;
    }
  }

  if (!response.ok) {
    const message = typeof payload === 'string'
      ? payload
      : payload?.message || `Request failed with status ${response.status}`;
    throw new Error(message);
  }

  return payload;
}

export const api = {
  register: (body) => request('/users/register', { method: 'POST', body: JSON.stringify(body) }),
  login: (body) => request('/users/login', { method: 'POST', body: JSON.stringify(body) }),
  getMovies: () => request('/movies'),
  createMovie: (body) => request('/movies', { method: 'POST', body: JSON.stringify(body) }),
  getBookings: () => request('/bookings'),
  createBooking: (body) => request('/bookings', { method: 'POST', body: JSON.stringify(body) }),
  getBooking: (bookingId) => request(`/bookings/${bookingId}`)
};
