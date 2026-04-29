import { useEffect, useMemo, useState } from 'react';
import { api } from './api';

const emptyRegister = {
  username: '',
  email: '',
  fullName: '',
  password: ''
};

const emptyLogin = {
  username: '',
  password: ''
};

const emptyMovie = {
  title: '',
  genre: '',
  duration: '',
  description: '',
  price: 120000
};

const emptyBooking = {
  movieId: '',
  seatNumber: 'A1',
  seatCount: 1
};

function toMoney(value) {
  if (typeof value !== 'number') {
    return value;
  }
  return new Intl.NumberFormat('vi-VN').format(value);
}

function unwrapData(payload) {
  if (!payload) {
    return [];
  }
  if (Array.isArray(payload)) {
    return payload;
  }
  if (Array.isArray(payload.data)) {
    return payload.data;
  }
  return [];
}

function statusLabel(status) {
  switch (status) {
    case 'COMPLETED':
      return 'Thành công';
    case 'FAILED':
      return 'Thất bại';
    default:
      return 'Đang chờ';
  }
}

export default function App() {
  const [user, setUser] = useState(() => {
    const stored = localStorage.getItem('movie-ticket-user');
    return stored ? JSON.parse(stored) : null;
  });
  const [registerForm, setRegisterForm] = useState(emptyRegister);
  const [loginForm, setLoginForm] = useState(emptyLogin);
  const [movieForm, setMovieForm] = useState(emptyMovie);
  const [bookingForm, setBookingForm] = useState(emptyBooking);
  const [movies, setMovies] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [loadingMovies, setLoadingMovies] = useState(false);
  const [loadingBookings, setLoadingBookings] = useState(false);
  const [message, setMessage] = useState('Sẵn sàng kết nối gateway.');
  const [error, setError] = useState('');

  const selectedMovie = useMemo(
    () => movies.find((movie) => movie.id === bookingForm.movieId),
    [movies, bookingForm.movieId]
  );

  useEffect(() => {
    loadMovies();
    loadBookings();
  }, []);

  useEffect(() => {
    if (user?.id) {
      setBookingForm((current) => ({ ...current, userId: user.id }));
    }
  }, [user]);

  useEffect(() => {
    const timer = setInterval(() => {
      loadBookings({ silent: true });
    }, 5000);
    return () => clearInterval(timer);
  }, []);

  async function loadMovies() {
    setLoadingMovies(true);
    try {
      const payload = await api.getMovies();
      const list = unwrapData(payload);
      setMovies(list);
      if (!bookingForm.movieId && list.length > 0) {
        setBookingForm((current) => ({ ...current, movieId: list[0].id }));
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoadingMovies(false);
    }
  }

  async function loadBookings(options = {}) {
    if (!options.silent) {
      setLoadingBookings(true);
    }
    try {
      const payload = await api.getBookings();
      setBookings(unwrapData(payload));
      if (!options.silent) {
        setMessage('Đã tải danh sách booking.');
      }
    } catch (err) {
      if (!options.silent) {
        setError(err.message);
      }
    } finally {
      if (!options.silent) {
        setLoadingBookings(false);
      }
    }
  }

  async function handleRegister(event) {
    event.preventDefault();
    setError('');
    try {
      const payload = await api.register(registerForm);
      const account = payload?.data;
      setUser(account);
      localStorage.setItem('movie-ticket-user', JSON.stringify(account));
      setMessage(`Đăng ký thành công: ${account.username}`);
      setRegisterForm(emptyRegister);
      setBookingForm((current) => ({ ...current, userId: account.id }));
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleLogin(event) {
    event.preventDefault();
    setError('');
    try {
      const payload = await api.login(loginForm);
      const account = payload?.data;
      setUser(account);
      localStorage.setItem('movie-ticket-user', JSON.stringify(account));
      setMessage(`Đăng nhập thành công: ${account.username}`);
      setLoginForm(emptyLogin);
      setBookingForm((current) => ({ ...current, userId: account.id }));
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleMovieSubmit(event) {
    event.preventDefault();
    setError('');
    try {
      await api.createMovie({
        ...movieForm,
        price: Number(movieForm.price)
      });
      setMessage('Đã lưu phim mới.');
      setMovieForm(emptyMovie);
      await loadMovies();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleBookingSubmit(event) {
    event.preventDefault();
    setError('');
    const currentMovie = movies.find((movie) => movie.id === bookingForm.movieId);
    if (!user?.id) {
      setError('Vui lòng đăng ký hoặc đăng nhập trước khi đặt vé.');
      return;
    }
    if (!currentMovie) {
      setError('Vui lòng chọn phim.');
      return;
    }

    try {
      await api.createBooking({
        userId: user.id,
        movieId: bookingForm.movieId,
        seatNumber: bookingForm.seatNumber,
        amount: Number(currentMovie.price) * Number(bookingForm.seatCount || 1)
      });
      setMessage('Đã tạo booking. Đợi payment-service xử lý vài giây rồi refresh danh sách.');
      setBookingForm((current) => ({
        ...current,
        seatNumber: 'A1',
        seatCount: 1
      }));
      await loadBookings();
    } catch (err) {
      setError(err.message);
    }
  }

  function logout() {
    localStorage.removeItem('movie-ticket-user');
    setUser(null);
    setMessage('Đã đăng xuất.');
  }

  return (
    <div className="app-shell">
      <div className="ambient ambient-one" />
      <div className="ambient ambient-two" />

      <header className="hero-card">
        <div>
          <span className="eyebrow">Event-Driven Architecture</span>
          <h1>Movie Ticket System</h1>
          <p>
            Frontend gọi thẳng vào API Gateway để test đăng ký, xem phim, đặt vé
            và theo dõi trạng thái booking theo luồng RabbitMQ.
          </p>
        </div>
        <div className="hero-meta">
          <div>
            <span>Gateway</span>
            <strong>http://localhost:8080</strong>
          </div>
          <div>
            <span>Eureka</span>
            <strong>http://localhost:8761</strong>
          </div>
          <div>
            <span>RabbitMQ UI</span>
            <strong>http://localhost:15672</strong>
          </div>
        </div>
      </header>

      <main className="grid-layout">
        <section className="panel auth-panel">
          <div className="panel-header">
            <h2>Tài khoản</h2>
            <div className="pill">{user ? `Đã đăng nhập: ${user.username}` : 'Chưa đăng nhập'}</div>
          </div>

          <div className="dual-form">
            <form className="form-card" onSubmit={handleRegister}>
              <h3>Đăng ký</h3>
              <label>
                Username
                <input value={registerForm.username} onChange={(e) => setRegisterForm({ ...registerForm, username: e.target.value })} required />
              </label>
              <label>
                Email
                <input type="email" value={registerForm.email} onChange={(e) => setRegisterForm({ ...registerForm, email: e.target.value })} required />
              </label>
              <label>
                Họ tên
                <input value={registerForm.fullName} onChange={(e) => setRegisterForm({ ...registerForm, fullName: e.target.value })} required />
              </label>
              <label>
                Password
                <input type="password" value={registerForm.password} onChange={(e) => setRegisterForm({ ...registerForm, password: e.target.value })} required />
              </label>
              <button type="submit">Đăng ký</button>
            </form>

            <form className="form-card" onSubmit={handleLogin}>
              <h3>Đăng nhập</h3>
              <label>
                Username
                <input value={loginForm.username} onChange={(e) => setLoginForm({ ...loginForm, username: e.target.value })} required />
              </label>
              <label>
                Password
                <input type="password" value={loginForm.password} onChange={(e) => setLoginForm({ ...loginForm, password: e.target.value })} required />
              </label>
              <button type="submit">Đăng nhập</button>
              {user && <button type="button" className="ghost" onClick={logout}>Đăng xuất</button>}
            </form>
          </div>
        </section>

        <section className="panel">
          <div className="panel-header">
            <h2>Danh sách phim</h2>
            <button className="ghost" type="button" onClick={loadMovies}>
              {loadingMovies ? 'Đang tải...' : 'Refresh'}
            </button>
          </div>
          <div className="movie-grid">
            {movies.map((movie) => (
              <article key={movie.id} className="movie-card">
                <div className="movie-topline">
                  <strong>{movie.title}</strong>
                  <span>{movie.genre}</span>
                </div>
                <p>{movie.description}</p>
                <div className="movie-meta">
                  <span>{movie.duration}</span>
                  <span>{toMoney(movie.price)} VND</span>
                </div>
              </article>
            ))}
          </div>
          {!movies.length && <p className="muted">Chưa có phim.</p>}
        </section>

        <section className="panel">
          <div className="panel-header">
            <h2>Thêm phim</h2>
            <div className="pill">POST /api/movies</div>
          </div>
          <form className="stack-form" onSubmit={handleMovieSubmit}>
            <div className="two-cols">
              <label>
                Tên phim
                <input value={movieForm.title} onChange={(e) => setMovieForm({ ...movieForm, title: e.target.value })} required />
              </label>
              <label>
                Thể loại
                <input value={movieForm.genre} onChange={(e) => setMovieForm({ ...movieForm, genre: e.target.value })} required />
              </label>
            </div>
            <div className="two-cols">
              <label>
                Thời lượng
                <input value={movieForm.duration} onChange={(e) => setMovieForm({ ...movieForm, duration: e.target.value })} required />
              </label>
              <label>
                Giá vé
                <input type="number" min="0" value={movieForm.price} onChange={(e) => setMovieForm({ ...movieForm, price: e.target.value })} required />
              </label>
            </div>
            <label>
              Mô tả
              <textarea rows="3" value={movieForm.description} onChange={(e) => setMovieForm({ ...movieForm, description: e.target.value })} required />
            </label>
            <button type="submit">Lưu phim</button>
          </form>
        </section>

        <section className="panel">
          <div className="panel-header">
            <h2>Đặt vé</h2>
            <div className="pill">POST /api/bookings</div>
          </div>
          <form className="stack-form" onSubmit={handleBookingSubmit}>
            <label>
              User ID
              <input value={user?.id || ''} disabled placeholder="Đăng nhập để tự điền" />
            </label>
            <label>
              Chọn phim
              <select value={bookingForm.movieId} onChange={(e) => setBookingForm({ ...bookingForm, movieId: e.target.value })} required>
                {movies.length === 0 && <option value="">Chưa có phim</option>}
                {movies.map((movie) => (
                  <option key={movie.id} value={movie.id}>
                    {movie.title} - {toMoney(movie.price)} VND
                  </option>
                ))}
              </select>
            </label>
            <div className="two-cols">
              <label>
                Ghế
                <input value={bookingForm.seatNumber} onChange={(e) => setBookingForm({ ...bookingForm, seatNumber: e.target.value })} placeholder="A1" required />
              </label>
              <label>
                Số vé
                <input type="number" min="1" value={bookingForm.seatCount} onChange={(e) => setBookingForm({ ...bookingForm, seatCount: e.target.value })} required />
              </label>
            </div>
            <div className="summary-box">
              <span>Phim chọn: {selectedMovie?.title || 'Chưa chọn'}</span>
              <strong>
                Tạm tính: {selectedMovie ? toMoney(Number(selectedMovie.price) * Number(bookingForm.seatCount || 1)) : 0} VND
              </strong>
            </div>
            <button type="submit">Tạo booking</button>
          </form>
        </section>

        <section className="panel bookings-panel">
          <div className="panel-header">
            <h2>Booking hiện tại</h2>
            <button className="ghost" type="button" onClick={() => loadBookings()}>
              {loadingBookings ? 'Đang tải...' : 'Refresh'}
            </button>
          </div>
          <div className="booking-list">
            {bookings.map((booking) => (
              <article key={booking.bookingId} className="booking-card">
                <div className="movie-topline">
                  <strong>#{booking.bookingId}</strong>
                  <span className={booking.status === 'COMPLETED' ? 'badge success' : booking.status === 'FAILED' ? 'badge danger' : 'badge'}>
                    {statusLabel(booking.status)}
                  </span>
                </div>
                <div className="booking-grid">
                  <span>User: {booking.userId}</span>
                  <span>Movie: {booking.movieId}</span>
                  <span>Ghế: {booking.seatNumber}</span>
                  <span>Amount: {toMoney(booking.amount)} VND</span>
                </div>
              </article>
            ))}
          </div>
          {!bookings.length && <p className="muted">Chưa có booking nào.</p>}
        </section>
      </main>

      <footer className="status-bar">
        <span>{message}</span>
        {error ? <span className="error-text">{error}</span> : <span className="muted">Đợi payment-service và notification-service xử lý trong vài giây.</span>}
      </footer>
    </div>
  );
}
