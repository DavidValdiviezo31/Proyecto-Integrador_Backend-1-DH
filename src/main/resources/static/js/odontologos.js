import { fetchConfig, sweetAlert, validarTexto, validarNumeros } from './utils.js'

// VARIABLES DOM
const table = document.querySelector('#odontologoTableBody')
const updateButton = document.querySelector('#btnActualizarLista')
const closeFormButton = document.querySelector('#closeBtn')
const newOdontologoButton = document.querySelector('#btnAgregarOdontologo')

const odontologoFormContainer = document.querySelector('#odontologoFormContainer')
const odontologoForm = document.querySelector('#odontologoForm')
const idInput = odontologoForm.querySelector('#odontologoId')
const matriculaInput = odontologoForm.querySelector('#matricula')
const nombreInput = odontologoForm.querySelector('#nombre')
const apellidoInput = odontologoForm.querySelector('#apellido')
const submitButton = odontologoForm.querySelector('button')

const validacionesFormulario = {
  matricula: false,
  nombre: false,
  apellido: false
}

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
          <button class="bg-blue-600 hover:bg-blue-800 text-white font-bold py-2 px-4 rounded transition-all" id="btnEdit-${id}">
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
          <button class="bg-red-600 hover:bg-red-800 text-white font-bold py-2 px-4 rounded transition-all" id="btnDelete-${id}">
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
    id: parseInt(idInput.value),
    matricula: parseInt(matriculaInput.value),
    nombre: nombreInput.value,
    apellido: apellidoInput.value
  }
}

function insertarDatosFormulario({ id, matricula, nombre, apellido }) {
  idInput.value = id
  matriculaInput.value = matricula
  nombreInput.value = nombre
  apellidoInput.value = apellido
}

function limpiarFormulario() {
  odontologoForm.reset()
}

function mostrarFormulario(textoBoton) {
  limpiarFormulario()
  submitButton.textContent = textoBoton
  closeFormButton.classList.remove('hidden')
  odontologoFormContainer.classList.remove('hidden')
}

function ocultarFormulario() {
  limpiarFormulario()
  odontologoFormContainer.classList.add('hidden')
  closeFormButton.classList.add('hidden')

  matriculaInput.classList.remove('ring-red-500', 'ring-green-500')
  nombreInput.classList.remove('ring-red-500', 'ring-green-500')
  apellidoInput.classList.remove('ring-red-500', 'ring-green-500')
}

function validarTarget({ funcionValidar, target, text }) {
  if (!funcionValidar(target.value)) {
    sweetAlert({ type: 'warning', text })
    target.classList.remove('ring-green-500')
    target.classList.add('ring-red-500')
    return false
  } else {
    target.classList.remove('ring-red-500')
    target.classList.add('ring-green-500')
  }

  return true
}

// EVENT HANDLERS
function agregarEventListeners() {
  updateButton.addEventListener('click', actualizarLista)

  newOdontologoButton.addEventListener('click', () => {
    mostrarFormulario('Agregar Odontólogo')
  })

  table.addEventListener('click', handleClickTabla)

  odontologoForm.addEventListener('submit', handleSubmitFormulario)

  closeFormButton.addEventListener('click', ocultarFormulario)

  validarFormulario()
}

function validarFormulario() {
  matriculaInput.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarNumeros,
      target: e.target,
      text: 'La matrícula no puede contener letras'
    })
    validacionesFormulario.matricula = isValid
    submitButton.disabled = !Object.values(validacionesFormulario).every(Boolean)
  })

  nombreInput.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarTexto,
      target: e.target,
      text: 'El nombre no puede contener números'
    })
    validacionesFormulario.nombre = isValid
    submitButton.disabled = !Object.values(validacionesFormulario).every(Boolean)
  })

  apellidoInput.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarTexto,
      target: e.target,
      text: 'El apellido no puede contener números'
    })
    validacionesFormulario.apellido = isValid
    submitButton.disabled = !Object.values(validacionesFormulario).every(Boolean)
  })
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
  mostrarFormulario('Actualizar Odontólogo')
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
