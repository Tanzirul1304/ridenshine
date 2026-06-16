import { Routes, Route } from 'react-router-dom'
import Header from './components/Header'
import Footer from './components/Footer'
import Home from './pages/Home'
import Products from './pages/Products'
import Services from './pages/Services'
import Booking from './pages/Booking'
import Reviews from './pages/Reviews'
import Admin from './pages/Admin'
import NotFound from './pages/NotFound'
export default function App() {
  return <><Header /><main><Routes>
    <Route path="/" element={<Home />} />
    <Route path="/products" element={<Products />} />
    <Route path="/services" element={<Services />} />
    <Route path="/booking" element={<Booking />} />
    <Route path="/reviews" element={<Reviews />} />
    <Route path="/admin" element={<Admin />} />
    <Route path="*" element={<NotFound />} />
  </Routes></main><Footer /></>
}
