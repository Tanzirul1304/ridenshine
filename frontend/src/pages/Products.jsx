import { useEffect, useMemo, useState } from 'react'
import { api } from '../services/api'
import ProductCard from '../components/ProductCard'
import Loading from '../components/Loading'
import Message from '../components/Message'
export default function Products() {
  const [products,setProducts]=useState([]); const [search,setSearch]=useState(''); const [category,setCategory]=useState('All'); const [loading,setLoading]=useState(true); const [error,setError]=useState('')
  useEffect(()=>{ api('/products').then(setProducts).catch(e=>setError(e.message)).finally(()=>setLoading(false)) },[])
  const categories=['All',...new Set(products.map(p=>p.category))]
  const filtered=useMemo(()=>products.filter(p=>(category==='All'||p.category===category)&&`${p.name} ${p.brand} ${p.description}`.toLowerCase().includes(search.toLowerCase())),[products,search,category])
  return <section className="section container"><div className="page-title"><span className="eyebrow">Parts catalogue</span><h1>Find parts before visiting</h1><p>Prices and stock can be maintained by the shop owner. Final compatibility and fitting are confirmed at the workshop.</p></div><div className="filters"><input value={search} onChange={e=>setSearch(e.target.value)} placeholder="Search product or brand"/><select value={category} onChange={e=>setCategory(e.target.value)}>{categories.map(c=><option key={c}>{c}</option>)}</select></div><Message type="error">{error}</Message>{loading?<Loading/>:<><p className="result-count">{filtered.length} product(s)</p><div className="card-grid">{filtered.map(p=><ProductCard key={p.id} product={p}/>)}</div></>}</section>
}
