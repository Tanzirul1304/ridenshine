import { useEffect, useState } from 'react'
import { adminLogin, adminLogout, api, isAdminLoggedIn } from '../services/api'
import Message from '../components/Message'

const emptyProduct={name:'',category:'',brand:'',price:'',stockQuantity:'',description:'',imageUrl:'',featured:false}
const emptyService={name:'',description:'',price:'',durationMinutes:30,active:true}
const emptyOffer={title:'',description:'',discountPercent:0,validUntil:'',active:true}

const MAX_PRODUCT_IMAGE_BYTES = 6 * 1024 * 1024

function compressProductImage(file) {
 return new Promise((resolve, reject) => {
  if (!file.type.startsWith('image/')) {
   reject(new Error('Please choose a JPG, PNG, WEBP or another image file.'))
   return
  }
  if (file.size > MAX_PRODUCT_IMAGE_BYTES) {
   reject(new Error('The selected image is larger than 6 MB. Please choose a smaller image.'))
   return
  }

  const reader = new FileReader()
  reader.onerror = () => reject(new Error('The image could not be read.'))
  reader.onload = () => {
   const image = new Image()
   image.onerror = () => reject(new Error('The selected file is not a valid image.'))
   image.onload = () => {
    const maxDimension = 900
    const scale = Math.min(1, maxDimension / Math.max(image.width, image.height))
    const width = Math.max(1, Math.round(image.width * scale))
    const height = Math.max(1, Math.round(image.height * scale))
    const canvas = document.createElement('canvas')
    canvas.width = width
    canvas.height = height
    const context = canvas.getContext('2d')
    context.fillStyle = '#ffffff'
    context.fillRect(0, 0, width, height)
    context.drawImage(image, 0, 0, width, height)
    resolve(canvas.toDataURL('image/jpeg', 0.78))
   }
   image.src = reader.result
  }
  reader.readAsDataURL(file)
 })
}

export default function Admin(){
 const [logged,setLogged]=useState(isAdminLoggedIn()); const [credentials,setCredentials]=useState({username:'admin',password:''}); const [loginError,setLoginError]=useState('');
 const [tab,setTab]=useState('overview'); const [data,setData]=useState({stats:null,products:[],services:[],bookings:[],reviews:[],offers:[],errors:[]}); const [message,setMessage]=useState(''); const [error,setError]=useState('');
 const [product,setProduct]=useState(emptyProduct); const [productEditId,setProductEditId]=useState(null); const [productImageKey,setProductImageKey]=useState(0); const [service,setService]=useState(emptyService); const [serviceEditId,setServiceEditId]=useState(null); const [offer,setOffer]=useState(emptyOffer); const [offerEditId,setOfferEditId]=useState(null)

 async function load(){
  setError('')
  try{
   const [stats,products,services,bookings,reviews,offers,errors]=await Promise.all([
    api('/admin/stats',{},true),api('/admin/products',{},true),api('/admin/services',{},true),api('/admin/bookings',{},true),api('/admin/reviews',{},true),api('/admin/offers',{},true),api('/admin/errors',{},true)
   ])
   setData({stats,products,services,bookings,reviews,offers,errors})
  }catch(e){setError(e.message); if(e.message.includes('401')){adminLogout();setLogged(false)}}
 }
 useEffect(()=>{if(logged)load()},[logged])
 async function login(e){e.preventDefault();setLoginError('');try{await adminLogin(credentials.username,credentials.password);setLogged(true)}catch{setLoginError('Invalid admin username or password.')}}
 function logout(){adminLogout();setLogged(false)}
 async function action(work,success){setMessage('');setError('');try{await work();setMessage(success);await load()}catch(e){setError(e.message)}}
 async function saveProduct(e){e.preventDefault();const body={...product,price:Number(product.price),stockQuantity:Number(product.stockQuantity)};await action(()=>api(productEditId?`/admin/products/${productEditId}`:'/admin/products',{method:productEditId?'PUT':'POST',body:JSON.stringify(body)},true),productEditId?'Product updated.':'Product added.');setProduct(emptyProduct);setProductEditId(null);setProductImageKey(key=>key+1)}
 function editProduct(p){setProduct({...p});setProductEditId(p.id);setProductImageKey(key=>key+1);window.scrollTo({top:0,behavior:'smooth'})}
 async function chooseProductImage(e){
  const file=e.target.files?.[0]
  if(!file)return
  setError('')
  try{
   const imageUrl=await compressProductImage(file)
   setProduct(current=>({...current,imageUrl}))
  }catch(imageError){
   setError(imageError.message)
   setProductImageKey(key=>key+1)
  }
 }
 function removeProductImage(){setProduct(current=>({...current,imageUrl:''}));setProductImageKey(key=>key+1)}
 async function saveService(e){e.preventDefault();const body={...service,price:Number(service.price),durationMinutes:Number(service.durationMinutes)};await action(()=>api(serviceEditId?`/admin/services/${serviceEditId}`:'/admin/services',{method:serviceEditId?'PUT':'POST',body:JSON.stringify(body)},true),serviceEditId?'Service updated.':'Service added.');setService(emptyService);setServiceEditId(null)}
 function editService(s){setService({...s});setServiceEditId(s.id);window.scrollTo({top:0,behavior:'smooth'})}
 async function saveOffer(e){e.preventDefault();const body={...offer,discountPercent:Number(offer.discountPercent)};await action(()=>api(offerEditId?`/admin/offers/${offerEditId}`:'/admin/offers',{method:offerEditId?'PUT':'POST',body:JSON.stringify(body)},true),offerEditId?'Offer updated.':'Offer added.');setOffer(emptyOffer);setOfferEditId(null)}
 function editOffer(o){setOffer({...o,validUntil:o.validUntil||''});setOfferEditId(o.id);window.scrollTo({top:0,behavior:'smooth'})}

 if(!logged)return <section className="section container narrow"><div className="page-title"><span className="eyebrow">Protected area</span><h1>RideNShine Admin</h1><p>Use the administrator credentials stored in backend environment variables.</p></div><form className="panel form" onSubmit={login}><Message type="error">{loginError}</Message><label>Username<input value={credentials.username} onChange={e=>setCredentials({...credentials,username:e.target.value})}/></label><label>Password<input type="password" value={credentials.password} onChange={e=>setCredentials({...credentials,password:e.target.value})}/></label><button className="button">Sign in</button><p className="disclaimer">•	Administrator access is protected using secure production environment variables.</p></form></section>

 const tabs=['overview','products','services','bookings','reviews','offers','errors']
 return <section className="section container admin"><div className="admin-title"><div><span className="eyebrow">Business operations</span><h1>Admin dashboard</h1></div><button className="button ghost small" onClick={logout}>Sign out</button></div><div className="admin-tabs">{tabs.map(t=><button key={t} className={tab===t?'active':''} onClick={()=>setTab(t)}>{t}</button>)}</div><Message type="success">{message}</Message><Message type="error">{error}</Message>
  {tab==='overview'&&<Overview stats={data.stats} />}
  {tab==='products'&&<><form className="panel compact-form" onSubmit={saveProduct}><h2>{productEditId?'Edit product':'Add product'}</h2><input placeholder="Name" value={product.name} onChange={e=>setProduct({...product,name:e.target.value})} required/><input placeholder="Category" value={product.category} onChange={e=>setProduct({...product,category:e.target.value})} required/><input placeholder="Brand" value={product.brand||''} onChange={e=>setProduct({...product,brand:e.target.value})}/><input type="number" min="0" placeholder="Price BDT" value={product.price} onChange={e=>setProduct({...product,price:e.target.value})} required/><input type="number" min="0" placeholder="Stock" value={product.stockQuantity} onChange={e=>setProduct({...product,stockQuantity:e.target.value})} required/><div className="product-image-field"><label>Product picture<input key={productImageKey} type="file" accept="image/*" onChange={chooseProductImage}/></label><small>JPG, PNG or WEBP. The browser compresses the picture before saving it.</small>{product.imageUrl&&<div className="product-image-preview"><img src={product.imageUrl} alt="Product preview"/><button type="button" className="button ghost small" onClick={removeProductImage}>Remove picture</button></div>}</div><input placeholder="Image URL (optional alternative)" value={product.imageUrl?.startsWith('data:')?'':product.imageUrl||''} onChange={e=>setProduct({...product,imageUrl:e.target.value})}/><textarea placeholder="Description" value={product.description||''} onChange={e=>setProduct({...product,description:e.target.value})}/><label className="check"><input type="checkbox" checked={product.featured} onChange={e=>setProduct({...product,featured:e.target.checked})}/> Featured</label><button className="button small">{productEditId?'Save changes':'Add product'}</button>{productEditId&&<button type="button" className="button ghost small" onClick={()=>{setProduct(emptyProduct);setProductEditId(null);setProductImageKey(key=>key+1)}}>Cancel</button>}</form><DataTable headers={['Picture','Name','Category','Price','Stock','Actions']} rows={data.products.map(p=>[<img className="admin-product-thumb" src={p.imageUrl||'/product-placeholder.svg'} alt={p.name} onError={e=>{e.currentTarget.src='/product-placeholder.svg'}}/>,p.name,p.category,`৳${p.price}`,p.stockQuantity,<div className="row-actions"><button onClick={()=>editProduct(p)}>Edit</button><button onClick={()=>action(()=>api(`/admin/products/${p.id}`,{method:'DELETE'},true),'Product removed.')}>Delete</button></div>])}/></>}
  {tab==='services'&&<><form className="panel compact-form" onSubmit={saveService}><h2>{serviceEditId?'Edit service':'Add service'}</h2><input placeholder="Name" value={service.name} onChange={e=>setService({...service,name:e.target.value})} required/><input type="number" min="0" placeholder="Price BDT" value={service.price} onChange={e=>setService({...service,price:e.target.value})} required/><input type="number" min="5" placeholder="Duration minutes" value={service.durationMinutes} onChange={e=>setService({...service,durationMinutes:e.target.value})} required/><textarea placeholder="Description" value={service.description} onChange={e=>setService({...service,description:e.target.value})}/><label className="check"><input type="checkbox" checked={service.active} onChange={e=>setService({...service,active:e.target.checked})}/> Active</label><button className="button small">{serviceEditId?'Save changes':'Add service'}</button></form><DataTable headers={['Service','Price','Duration','Active','Actions']} rows={data.services.map(s=>[s.name,`৳${s.price}`,`${s.durationMinutes} min`,s.active?'Yes':'No',<div className="row-actions"><button onClick={()=>editService(s)}>Edit</button><button onClick={()=>action(()=>api(`/admin/services/${s.id}`,{method:'DELETE'},true),'Service removed.')}>Delete</button></div>])}/></>}
  {tab==='bookings'&&<DataTable headers={['Reference','Customer','Bike / Service','Date','Status']} rows={data.bookings.map(b=>[b.referenceCode,<span>{b.customerName}<br/><small>{b.phone}</small></span>,<span>{b.bikeModel}<br/><small>{b.service.name}</small></span>,b.preferredDate,<select value={b.status} onChange={e=>action(()=>api(`/admin/bookings/${b.id}/status`,{method:'PATCH',body:JSON.stringify({status:e.target.value})},true),'Booking status updated.')}>{['REQUESTED','CONFIRMED','IN_PROGRESS','COMPLETED','CANCELLED'].map(s=><option key={s}>{s}</option>)}</select>])}/>} 
  {tab==='reviews'&&<DataTable headers={['Customer','Rating','Comment','State','Actions']} rows={data.reviews.map(r=>[r.customerName,`${r.rating}/5`,r.comment,r.approved?'Approved':'Pending',<div className="row-actions">{!r.approved&&<button onClick={()=>action(()=>api(`/admin/reviews/${r.id}/approve`,{method:'PATCH'},true),'Review approved.')}>Approve</button>}<button onClick={()=>action(()=>api(`/admin/reviews/${r.id}`,{method:'DELETE'},true),'Review removed.')}>Delete</button></div>])}/>} 
  {tab==='offers'&&<><form className="panel compact-form" onSubmit={saveOffer}><h2>{offerEditId?'Edit offer':'Add offer'}</h2><input placeholder="Title" value={offer.title} onChange={e=>setOffer({...offer,title:e.target.value})} required/><input type="number" min="0" max="100" placeholder="Discount %" value={offer.discountPercent} onChange={e=>setOffer({...offer,discountPercent:e.target.value})}/><input type="date" value={offer.validUntil||''} onChange={e=>setOffer({...offer,validUntil:e.target.value})}/><textarea placeholder="Description" value={offer.description} onChange={e=>setOffer({...offer,description:e.target.value})} required/><label className="check"><input type="checkbox" checked={offer.active} onChange={e=>setOffer({...offer,active:e.target.checked})}/> Active</label><button className="button small">{offerEditId?'Save changes':'Add offer'}</button></form><DataTable headers={['Title','Discount','Valid until','Active','Actions']} rows={data.offers.map(o=>[o.title,`${o.discountPercent||0}%`,o.validUntil||'—',o.active?'Yes':'No',<div className="row-actions"><button onClick={()=>editOffer(o)}>Edit</button><button onClick={()=>action(()=>api(`/admin/offers/${o.id}`,{method:'DELETE'},true),'Offer removed.')}>Delete</button></div>])}/></>}
  {tab==='errors'&&<><div className="panel"><h2>Built-in error tracking</h2><p>Unhandled server errors are assigned a request ID, written to application logs and stored here for administrator review.</p></div><DataTable headers={['Time','Request ID','Path','Type','Message']} rows={data.errors.map(e=>[e.occurredAt,e.requestId,e.path,e.exceptionType,e.message])}/></>}
 </section>
}
function Overview({stats}){if(!stats)return <div className="loading">Loading dashboard…</div>;return <><div className="stats-grid"><Stat n={stats.products} label="Products"/><Stat n={stats.outOfStockProducts} label="Stock-out items"/><Stat n={stats.services} label="Services"/><Stat n={stats.bookings} label="Bookings"/><Stat n={stats.pendingReviews} label="Pending reviews"/><Stat n={stats.trackedErrors} label="Tracked errors"/></div><div className="panel"><h2>Booking workflow</h2><div className="workflow-counts">{Object.entries(stats.bookingsByStatus).map(([k,v])=><div key={k}><strong>{v}</strong><span>{k.replace('_',' ')}</span></div>)}</div></div></>}
function Stat({n,label}){return <div className="stat"><strong>{n}</strong><span>{label}</span></div>}
function DataTable({headers,rows}){return <div className="table-wrap"><table><thead><tr>{headers.map(h=><th key={h}>{h}</th>)}</tr></thead><tbody>{rows.length?rows.map((row,i)=><tr key={i}>{row.map((cell,j)=><td key={j}>{cell}</td>)}</tr>):<tr><td colSpan={headers.length}>No data yet.</td></tr>}</tbody></table></div>}
