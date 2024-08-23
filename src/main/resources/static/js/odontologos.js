import { fetchConfig } from './utils.js'

// VARIABLES DOM
const table = document.querySelector('#odontologoTableBody')
const updateButton = document.querySelector('#btnActualizarLista')
const newOdontologoButton = document.querySelector('#btnAgregarOdontologo')
const odontologoFormContainer = document.querySelector('#odontologoFormContainer')
const odontologoForm = document.querySelector('#odontologoForm')

// CRUD METHODS
async function postOdontologos({ matricula, nombre, apellido }) {
  try {
    const config = fetchConfig({ method: 'POST', data: { matricula, nombre, apellido } })

    const res = await fetch('/odontologos', config)
    if (!res.ok) throw new Error('Error al crear odontólogo')

    Swal.fire({
      icon: 'success',
      title: 'Odontólogo creado',
      showConfirmButton: false,
      timer: 1500
    })

    return await res.json()
  } catch (err) {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: err.message
    })
  }
}

async function getOdontologoById(id) {
  try {
    const config = fetchConfig({ method: 'GET' })

    const res = await fetch(`/odontologos/${id}`, config)
    if (!res.ok) throw new Error('Error al cargar odontólogo')

    return await res.json()
  } catch (err) {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: err.message
    })
  }
}

async function getAllOdontologos() {
  try {
    const config = fetchConfig({ method: 'GET' })

    const res = await fetch('/odontologos', config)
    if (!res.ok) throw new Error('Error al cargar la lista de odontólogos')

    return await res.json()
  } catch (err) {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: err.message
    })
  }
}

async function putOdontologo({ id, matricula, nombre, apellido }) {
  try {
    const config = fetchConfig({ method: 'PUT', data: { matricula, nombre, apellido } })

    const res = await fetch(`/odontologos/${id}`, config)
    if (!res.ok) throw new Error('Error al actualizar odontólogo')

    Swal.fire({
      icon: 'success',
      title: 'Odontólogo actualizado',
      showConfirmButton: false,
      timer: 1500
    })

    odontologoForm.querySelector('button').textContent = 'Agregar'
    limpiarFormulario()
    odontologoFormContainer.classList.add('hidden')
  } catch (err) {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: err.message
    })
  }
}

async function deleteOdontologo(id) {
  try {
    const config = fetchConfig({ method: 'DELETE' })

    const res = await fetch(`/odontologos/${id}`, config)
    if (!res.ok) throw new Error('Error al eliminar odontólogo')

    Swal.fire({
      icon: 'success',
      title: 'Odontólogo eliminado',
      showConfirmButton: false,
      timer: 1500
    })
  } catch (err) {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: err.message
    })
  }
}

// UTILS FUNCTIONS
function renderizarOdontologos(odontologos) {
  table.innerHTML = ''

  odontologos.forEach(({ id, matricula, nombre, apellido }) => {
    const elementHTML = `
      <tr class="bg-white border-b hover:bg-gray-50 text-center">
        <th
          scope="row"
          class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap"
        >
          ${id}
        </th>
        <td class="px-6 py-4">${matricula}</td>
        <td class="px-6 py-4">${nombre}</td>
        <td class="px-6 py-4">${apellido}</td>
        <td class="px-6 py-4 text-center">
          <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" id="btnEdit-${id}">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="icon icon-tabler icons-tabler-outline icon-tabler-edit"
            >
              <path
                stroke="none"
                d="M0 0h24v24H0z"
                fill="none"
              />
              <path d="M7 7h-1a2 2 0 0 0 -2 2v9a2 2 0 0 0 2 2h9a2 2 0 0 0 2 -2v-1" />
              <path d="M20.385 6.585a2.1 2.1 0 0 0 -2.97 -2.97l-8.415 8.385v3h3l8.385 -8.415z" />
              <path d="M16 5l3 3" />
            </svg>
          </button>
        </td>
        <td class="px-6 py-4 text-center">
          <button class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded" id="btnDelete-${id}">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="icon icon-tabler icons-tabler-outline icon-tabler-trash-x"
            >
              <path
                stroke="none"
                d="M0 0h24v24H0z"
                fill="none"
              />
              <path d="M4 7h16" />
              <path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" />
              <path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" />
              <path d="M10 12l4 4m0 -4l-4 4" />
            </svg>
          </button>
        </td>
      </tr>
    `

    table.insertAdjacentHTML('beforeend', elementHTML)
  })
}

async function actualizarLista() {
  const odontologos = await getAllOdontologos()
  renderizarOdontologos(odontologos)
}

actualizarLista()

function getFormData() {
  const id = parseInt(odontologoForm.querySelector('#odontologoId').value)
  const matricula = parseInt(odontologoForm.querySelector('#matricula').value)
  const nombre = odontologoForm.querySelector('#nombre').value
  const apellido = odontologoForm.querySelector('#apellido').value

  return { id, matricula, nombre, apellido }
}

function setFormData({ id, matricula, nombre, apellido }) {
  odontologoForm.querySelector('#odontologoId').value = id
  odontologoForm.querySelector('#matricula').value = matricula
  odontologoForm.querySelector('#nombre').value = nombre
  odontologoForm.querySelector('#apellido').value = apellido
}

function limpiarFormulario() {
  odontologoForm.reset()
}

// EVENTS LISTENERS
updateButton.addEventListener('click', actualizarLista)

newOdontologoButton.addEventListener('click', () => {
  const btnText = odontologoForm.querySelector('button')

  if (btnText.textContent === 'Actualizar' && !odontologoFormContainer.classList.contains('hidden')) {
    btnText.textContent = 'Agregar'
    limpiarFormulario()
  } else {
    odontologoFormContainer.classList.toggle('hidden')
  }
})

table.addEventListener('click', async e => {
  const btn = e.target.closest('button')?.id

  if (!btn) return

  if (btn.includes('btnDelete')) {
    const id = btn.split('-')[1]
    await deleteOdontologo(id)
    actualizarLista()
  }

  if (btn.includes('btnEdit')) {
    const id = btn.split('-')[1]
    const odontologo = await getOdontologoById(id)
    odontologoFormContainer.classList.remove('hidden')
    odontologoForm.querySelector('button').textContent = 'Actualizar'
    setFormData(odontologo)
  }
})

odontologoForm.addEventListener('submit', async e => {
  e.preventDefault()

  const { id, matricula, nombre, apellido } = getFormData()

  if (id) {
    await putOdontologo({ id, matricula, nombre, apellido })
    odontologoFormContainer.classList.add('hidden')
    actualizarLista()
    return
  }

  await postOdontologos({ matricula, nombre, apellido })

  odontologoFormContainer.classList.add('hidden')
  actualizarLista()
})
