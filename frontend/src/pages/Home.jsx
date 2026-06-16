import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { api } from '../services/api'
import ProductCard from '../components/ProductCard'
import Loading from '../components/Loading'

export default function Home() {
  const [products, setProducts] = useState([]); const [services, setServices] = useState([]); const [offers, setOffers] = useState([]); const [loading, setLoading] = useState(true)
  useEffect(() => { Promise.all([api('/products'), api('/services'), api('/offers')]).then(([p,s,o]) => { setProducts(p); setServices(s); setOffers(o) }).finally(() => setLoading(false)) }, [])
  return <>
    <section className="hero"><div className="container hero-grid"><div><span className="eyebrow">Motorcycle parts • servicing • bike wash</span><h1>Your bike deserves to <span>ride and shine.</span></h1><p>Browse parts, check current stock and request a workshop appointment before visiting RideNShine in Fatullah, Narayanganj.</p><div className="actions"><Link className="button" to="/booking">Book a service</Link><Link className="button ghost" to="/products">Browse parts</Link></div><div className="hero-points"><span>✓ Live stock status</span><span>✓ Booking reference tracking</span><span>✓ Verified review moderation</span></div></div><img src="/logo.png" alt="RideNShine" /></div></section>
    {offers.length > 0 && <section className="offer-strip"><div className="container">{offers.map(o => <div key={o.id}><strong>{o.title}</strong> — {o.description}</div>)}</div></section>}
    <section className="section container"><div className="section-head"><div><span className="eyebrow">Featured inventory</span><h2>Popular motorcycle parts</h2></div><Link to="/products">See all parts →</Link></div>{loading ? <Loading /> : <div className="card-grid">{products.filter(p=>p.featured).slice(0,3).map(p=><ProductCard key={p.id} product={p}/>)}</div>}</section>
    <section className="section alt"><div className="container"><div className="section-head"><div><span className="eyebrow">Workshop</span><h2>Services in one place</h2></div><Link to="/services">All services →</Link></div><div className="service-grid">{services.slice(0,4).map(s=><article className="service-card" key={s.id}><div className="service-icon">⚙</div><h3>{s.name}</h3><p>{s.description}</p><div><strong>From ৳{Number(s.price).toLocaleString()}</strong><span>{s.durationMinutes} min</span></div></article>)}</div></div></section>
    <section className="section container problem-grid"><div><span className="eyebrow">Why RideNShine Online?</span><h2>Less calling. Better preparation.</h2><p>Customers currently call or message to ask whether a part is available and visit the shop to take a servicing serial. This platform makes stock information, services and booking requests available in one place.</p></div><div className="metric-grid"><div><strong>2</strong><span>User roles</span></div><div><strong>5+</strong><span>Interacting modules</span></div><div><strong>24/7</strong><span>Online information</span></div><div><strong>1</strong><span>Business dashboard</span></div></div></section>
  </>
}
