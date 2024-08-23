export function fetchConfig({ method, data }) {
  if (method !== 'GET' && method !== 'POST' && method !== 'PUT' && method !== 'DELETE') return

  if (method === 'GET') {
    return {
      method,
      headers: {
        'Content-Type': 'application/json',
      }
    }
  }

  if (method === 'POST' || method === 'PUT') {
    return {
      method,
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data)
    }
  }

  if (method === 'DELETE') {
    return {
      method,
      'Content-Type': 'application/json',
    }
  }
}