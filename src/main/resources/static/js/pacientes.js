import { fetchConfig, sweetAlert } from './utils.js'

// TODO: VALIDACIONES DE FORMULARIO

// VARIABLES DOM
const btnAtras = document.querySelector('#btnAtras')
const pacienteFormContainer = document.querySelector('#pacienteFormContainer')
const domicilioFormContainer = document.querySelector('#domicilioFormContainer')
const pacienteForm = document.querySelector('#pacienteForm')
const domicilioForm = document.querySelector('#domicilioForm')
const table = document.querySelector('#pacienteTableBody')
const newPacienteButton = document.querySelector('#btnAgregarPaciente')
const closeFormButton = document.querySelector('#closeBtn')

// CRUD METHODS
async function postPacientes({ dni, nombre, apellido, domicilio, fechaAlta }) {
  try {
    const config = fetchConfig({ method: 'POST', data: { dni, nombre, apellido, domicilio, fechaAlta } })

    const res = await fetch('/pacientes', config)
    if (!res.ok) throw new Error('Error al crear paciente.')

    sweetAlert({ type: 'success', title: 'Paciente creado' })

    return await res.json()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function getPacienteById(id) {
  try {
    const config = fetchConfig({ method: 'GET' })

    const res = await fetch(`/pacientes/${id}`, config)
    if (!res.ok) throw new Error('Error al buscar paciente')

    return await res.json()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function getAllPacientes() {
  try {
    const config = fetchConfig({ method: 'GET' })

    const res = await fetch('/pacientes', config)
    if (!res.ok) throw new Error('Error al cargar la lista de pacientes')

    return await res.json()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function putPaciente({ id, dni, nombre, apellido, domicilio, fechaAlta }) {
  try {
    const config = fetchConfig({ method: 'PUT', data: { id, dni, nombre, apellido, domicilio, fechaAlta } })

    const res = await fetch(`/pacientes/${id}`, config)
    if (!res.ok) throw new Error('Error al actualizar paciente.')

    sweetAlert({ type: 'success', title: 'Paciente actualizado' })

    limpiarFormulario()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function deletePaciente(id) {
  try {
    const config = fetchConfig({ method: 'DELETE' })

    const res = await fetch(`/pacientes/${id}`, config)
    if (!res.ok) throw new Error('Error al eliminar paciente.')

    sweetAlert({ type: 'success', title: 'Paciente eliminado' })
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

// UTILS FUNCTIONS
function renderizarPacientes(pacientes) {
  table.innerHTML = ''

  pacientes.forEach(({ id, dni, nombre, apellido, domicilio, fechaAlta }) => {
    const { calle, numero, localidad, provincia } = domicilio
    const elementHTML = `
      <tr class="bg-white border-b hover:bg-gray-50 text-center">
        <th
          scope="row"
          class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap"
        >
          ${id}
        </th>
        <td class="px-6 py-4">${dni}</td>
        <td class="px-6 py-4">${nombre}</td>
        <td class="px-6 py-4">${apellido}</td>
        <td class="px-6 py-4">${calle} ${numero}, ${localidad}, ${provincia}</td>
        <td class="px-6 py-4">${fechaAlta}</td>
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
  getAllPacientes().then(renderizarPacientes)
}

// FORM FUNCTIONS
function obtenerDatosFormulario() {
  return {
    id: parseInt(pacienteForm.querySelector('#pacienteId').value),
    dni: parseInt(pacienteForm.querySelector('#dni').value),
    nombre: pacienteForm.querySelector('#nombre').value,
    apellido: pacienteForm.querySelector('#apellido').value,
    fechaAlta: pacienteForm.querySelector('#fechaAlta').value,
    domicilio: {
      id: parseInt(domicilioForm.querySelector('#domicilioId').value),
      calle: domicilioForm.querySelector('#calle').value,
      numero: parseInt(domicilioForm.querySelector('#numero').value),
      localidad: domicilioForm.querySelector('#localidad').value,
      provincia: domicilioForm.querySelector('#provincia').value
    }
  }
}

function insertarDatosFormulario({ id, dni, nombre, apellido, domicilio, fechaAlta }) {
  const { id: idDomicilio, calle, numero, localidad, provincia } = domicilio

  pacienteForm.querySelector('#pacienteId').value = id
  pacienteForm.querySelector('#dni').value = dni
  pacienteForm.querySelector('#nombre').value = nombre
  pacienteForm.querySelector('#apellido').value = apellido
  pacienteForm.querySelector('#fechaAlta').value = fechaAlta

  domicilioForm.querySelector('#domicilioId').value = idDomicilio
  domicilioForm.querySelector('#calle').value = calle
  domicilioForm.querySelector('#numero').value = numero
  domicilioForm.querySelector('#localidad').value = localidad
  domicilioForm.querySelector('#provincia').value = provincia
}

function limpiarFormulario() {
  domicilioForm.reset()
  pacienteForm.reset()
}

function mostrarFormulario(textoBoton) {
  limpiarFormulario()
  domicilioForm.querySelector('button[type="submit"]').textContent = textoBoton
  closeFormButton.classList.remove('hidden')
  domicilioFormContainer.classList.add('hidden')
  pacienteFormContainer.classList.remove('hidden')
}

function ocultarFormulario() {
  limpiarFormulario()
  domicilioFormContainer.classList.add('hidden')
  pacienteFormContainer.classList.add('hidden')
  closeFormButton.classList.add('hidden')
}

// EVENT HANDLERS
function agregarEventListeners() {
  btnAtras.addEventListener('click', handleBtnAtras)
  domicilioFormContainer.addEventListener('submit', handleSubmitDomicilioForm)
  domicilioForm.addEventListener('submit', e => e.preventDefault())
  newPacienteButton.addEventListener('click', handleNuevoPaciente)
  table.addEventListener('click', handleClickTabla)
  pacienteForm.addEventListener('submit', handleSubmitPacienteForm)
  closeFormButton.addEventListener('click', ocultarFormulario)
}

function handleBtnAtras(e) {
  e.preventDefault()
  pacienteFormContainer.classList.remove('hidden')
  domicilioFormContainer.classList.add('hidden')
}

async function handleSubmitDomicilioForm(e) {
  e.preventDefault()

  const { id, dni, nombre, apellido, domicilio, fechaAlta } = obtenerDatosFormulario()

  if (id) {
    await putPaciente({ id, dni, nombre, apellido, domicilio, fechaAlta })
  } else {
    const { id: _, ...nuevoDomicilio } = domicilio
    await postPacientes({ dni, nombre, apellido, domicilio: nuevoDomicilio, fechaAlta })
  }

  ocultarFormulario()
  actualizarLista()
}

function handleNuevoPaciente() {
  mostrarFormulario('Agregar')
}

async function handleClickTabla(e) {
  const btn = e.target.closest('button')?.id
  if (!btn) return

  if (btn.includes('btnDelete')) {
    await handleEliminarPaciente(btn)
  } else if (btn.includes('btnEdit')) {
    await handleEditarPaciente(btn)
  }
}

async function handleEliminarPaciente(btn) {
  const { isConfirmed } = await Swal.fire({
    title: '¿Estas seguro que deseas eliminar el Paciente?',
    showCancelButton: true,
    confirmButtonText: `Eliminar`,
    confirmButtonColor: '#DC2626',
    cancelButtonText: `Cancelar`
  })

  if (!isConfirmed) return

  const id = btn.split('-')[1]
  await deletePaciente(id)
  actualizarLista()
}

async function handleEditarPaciente(btn) {
  const id = btn.split('-')[1]
  const paciente = await getPacienteById(id)
  mostrarFormulario('Actualizar')
  insertarDatosFormulario(paciente)
}

async function handleSubmitPacienteForm(e) {
  e.preventDefault()

  domicilioFormContainer.classList.remove('hidden')
  pacienteFormContainer.classList.add('hidden')
}

// MAIN FUNCTION
function main() {
  actualizarLista()
  agregarEventListeners()
}

main()
