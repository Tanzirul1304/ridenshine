import { NavLink, Link } from 'react-router-dom'
export default function Header() {
  const linkClass = ({ isActive }) => isActive ? 'nav-link active' : 'nav-link'
  return <header className="site-header"><div className="container nav-wrap">
    <Link className="brand" to="/"><img src="/logo.png" alt="RideNShine logo" /><span>RideNShine</span></Link>
    <nav><NavLink className={linkClass} to="/">Home</NavLink><NavLink className={linkClass} to="/products">Parts</NavLink><NavLink className={linkClass} to="/services">Services</NavLink><NavLink className={linkClass} to="/booking">Book Service</NavLink><NavLink className={linkClass} to="/reviews">Reviews</NavLink><NavLink className={linkClass} to="/admin">Admin</NavLink></nav>
  </div></header>
}
