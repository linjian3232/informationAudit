import axios from 'axios'
import router from '../router'

const client = axios.create({ timeout: 5000 })

client.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('userName')
      localStorage.removeItem('level')
      if (router.currentRoute.path !== '/') {
        router.replace('/')
      }
    }
    return Promise.reject(error)
  }
)

export function get (url, params, options = {}) {
  return client.get(url, { ...options, params }).then(response => response.data)
}

export function post (url, data, options) {
  return client.post(url, data, options).then(response => response.data)
}
