const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

export async function api(path, options = {}, admin = false) {
  const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) }
  if (admin) {
    const token = sessionStorage.getItem('rns_admin_auth')
    if (token) headers.Authorization = `Basic ${token}`
  }
  const response = await fetch(`${API_URL}${path}`, { ...options, headers })
  if (!response.ok) {
    let message = `Request failed (${response.status})`
    try {
      const body = await response.json()
      message = body.message || Object.values(body.validationErrors || {}).join(', ') || message
    } catch { /* non-JSON response */ }
    throw new Error(message)
  }
  if (response.status === 204) return null
  return response.json()
}
export async function adminLogin(username, password) {
  const token = btoa(`${username}:${password}`)
  sessionStorage.setItem('rns_admin_auth', token)
  try { await api('/admin/ping', {}, true); return true }
  catch (e) { sessionStorage.removeItem('rns_admin_auth'); throw e }
}
export function adminLogout() { sessionStorage.removeItem('rns_admin_auth') }
export function isAdminLoggedIn() { return Boolean(sessionStorage.getItem('rns_admin_auth')) }
