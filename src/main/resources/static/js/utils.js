export function fetchConfig({ method, data }) {
  if (method !== 'GET' && method !== 'POST' && method !== 'PUT' && method !== 'DELETE') return

  if (method === 'GET') {
    return {
      method,
      headers: {
        'Content-Type': 'application/json'
      }
    }
  }

  if (method === 'POST' || method === 'PUT') {
    return {
      method,
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    }
  }

  if (method === 'DELETE') {
    return {
      method,
      'Content-Type': 'application/json'
    }
  }
}

export function sweetAlert({ type, title, text }) {
  switch (type) {
    case 'error':
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text
      })
      break
    case 'success':
      Swal.fire({
        icon: 'success',
        title,
        showConfirmButton: false,
        timer: 1750
      })
      break
    default:
      break
  }
}

export function validarNumeros(cadena) {
  const regex = /^[0-9]+$/
  return regex.test(cadena)
}

export function validarTexto(cadena) {
  const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/
  return regex.test(cadena)
}

export function validarFecha(cadena) {
  const regex = /^\d{4}-\d{2}-\d{2}$/
  return regex.test(cadena)
}
