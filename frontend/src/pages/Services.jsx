import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { api } from '../services/api'
import Loading from '../components/Loading'
import Message from '../components/Message'
export default function Services(){
 const [items,setItems]=useState([]); const [error,setError]=useState('');
 useEffect(()=>{api('/services').then(setItems).catch(e=>setError(e.message))},[])
 return <section className="section container"><div className="page-title"><span className="eyebrow">Workshop services</span><h1>Maintenance, repair and wash</h1><p>Choose a service and request your preferred date. The workshop confirms the final serial by phone.</p></div><Message type="error">{error}</Message>{!items.length&&!error?<Loading/>:<div className="service-list">{items.map(s=><article key={s.id}><div><span className="service-icon">🔧</span><h2>{s.name}</h2><p>{s.description}</p></div><div className="service-meta"><strong>৳{Number(s.price).toLocaleString()}</strong><span>Approx. {s.durationMinutes} minutes</span><Link className="button small" to={`/booking?service=${s.id}`}>Request booking</Link></div></article>)}</div>}<p className="disclaimer">Displayed prices are demo/starting prices and should be confirmed by RideNShine before public launch. Replacement parts are charged separately.</p></section>
}
