import { fetchConfig, sweetAlert } from './utils.js'

// TODO: VALIDACIONES DE FORMULARIO

// VARIABLES DOM
const table = document.querySelector('#odontologoTableBody')
const updateButton = document.querySelector('#btnActualizarLista')
const newOdontologoButton = document.querySelector('#btnAgregarOdontologo')
const odontologoFormContainer = document.querySelector('#odontologoFormContainer')
const odontologoForm = document.querySelector('#odontologoForm')
const closeFormButton = document.querySelector('#closeBtn')

// CRUD METHODS
async function postOdontologos({ matricula, nombre, apellido }) {
  const config = fetchConfig({ method: 'POST', data: { matricula, nombre, apellido } })

  try {
    const res = await fetch('/odontologos', config)
    if (!res.ok) throw new Error('Error al crear odontólogo.')

    sweetAlert({ type: 'success', title: 'Odontólogo creado' })

    return await res.json()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function getOdontologoById(id) {
  const config = fetchConfig({ method: 'GET' })

  try {
    const res = await fetch(`/odontologos/${id}`, config)
    if (!res.ok) throw new Error('Error al buscar odontólogo')

    return await res.json()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function getAllOdontologos() {
  const config = fetchConfig({ method: 'GET' })

  try {
    const res = await fetch('/odontologos', config)
    if (!res.ok) throw new Error('Error al cargar la lista de odontólogos')

    return await res.json()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function putOdontologo({ id, matricula, nombre, apellido }) {
  const config = fetchConfig({ method: 'PUT', data: { matricula, nombre, apellido } })

  try {
    const res = await fetch(`/odontologos/${id}`, config)
    if (!res.ok) throw new Error('Error al actualizar odontólogo')

    sweetAlert({ type: 'success', title: 'Odontólogo actualizado' })

    odontologoForm.querySelector('button').textContent = 'Agregar'
    limpiarFormulario()
    odontologoFormContainer.classList.add('hidden')
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function deleteOdontologo(id) {
  const config = fetchConfig({ method: 'DELETE' })

  try {
    const res = await fetch(`/odontologos/${id}`, config)
    if (!res.ok) throw new Error('Error al eliminar odontólogo')

    sweetAlert({ type: 'success', title: 'Odontólogo eliminado' })
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
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

function actualizarLista() {
  getAllOdontologos().then(renderizarOdontologos)
}

// FORM FUNCTIONS
function obtenerDatosFormulario() {
  return {
    id: parseInt(odontologoForm.querySelector('#odontologoId').value),
    matricula: parseInt(odontologoForm.querySelector('#matricula').value),
    nombre: odontologoForm.querySelector('#nombre').value,
    apellido: odontologoForm.querySelector('#apellido').value
  }
}

function insertarDatosFormulario({ id, matricula, nombre, apellido }) {
  odontologoForm.querySelector('#odontologoId').value = id
  odontologoForm.querySelector('#matricula').value = matricula
  odontologoForm.querySelector('#nombre').value = nombre
  odontologoForm.querySelector('#apellido').value = apellido
}

function limpiarFormulario() {
  odontologoForm.reset()
}

function mostrarFormulario(textoBoton) {
  limpiarFormulario()
  odontologoForm.querySelector('button').textContent = textoBoton
  closeFormButton.classList.remove('hidden')
  odontologoFormContainer.classList.remove('hidden')
}

function ocultarFormulario() {
  limpiarFormulario()
  odontologoFormContainer.classList.add('hidden')
  closeFormButton.classList.add('hidden')
}

// EVENT HANDLERS
function agregarEventListeners() {
  updateButton.addEventListener('click', actualizarLista)

  newOdontologoButton.addEventListener('click', () => {
    mostrarFormulario('Agregar')
  })

  table.addEventListener('click', handleClickTabla)

  odontologoForm.addEventListener('submit', handleSubmitFormulario)

  closeFormButton.addEventListener('click', ocultarFormulario)
}

async function handleClickTabla(e) {
  const btn = e.target.closest('button')?.id
  if (!btn) return

  if (btn.includes('btnDelete')) {
    await handleEliminarOdontologo(btn)
  } else if (btn.includes('btnEdit')) {
    await handleEditarOdontologo(btn)
  }
}

async function handleEliminarOdontologo(btn) {
  const { isConfirmed } = await Swal.fire({
    title: '¿Estas seguro que deseas eliminar el Odontólogo?',
    showCancelButton: true,
    confirmButtonText: `Eliminar`,
    confirmButtonColor: '#DC2626',
    cancelButtonText: `Cancelar`
  })

  if (!isConfirmed) return

  const id = btn.split('-')[1]
  await deleteOdontologo(id)
  actualizarLista()
}

async function handleEditarOdontologo(btn) {
  const id = btn.split('-')[1]
  const odontologo = await getOdontologoById(id)
  mostrarFormulario('Actualizar')
  insertarDatosFormulario(odontologo)
}

async function handleSubmitFormulario(e) {
  e.preventDefault()

  const { id, matricula, nombre, apellido } = obtenerDatosFormulario()

  if (id) {
    await putOdontologo({ id, matricula, nombre, apellido })
  } else {
    await postOdontologos({ matricula, nombre, apellido })
  }

  ocultarFormulario()
  actualizarLista()
}

// MAIN FUNCTION
function main() {
  actualizarLista()
  agregarEventListeners()
}

main()
