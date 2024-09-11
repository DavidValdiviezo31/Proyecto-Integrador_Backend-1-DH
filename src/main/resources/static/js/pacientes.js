import { fetchConfig, sweetAlert, validarNumeros, validarTexto, validarFecha } from './utils.js'

// VARIABLES DOM
const btnAtras = document.querySelector('#btnAtras')
const newPacienteButton = document.querySelector('#btnAgregarPaciente')
const closeFormButton = document.querySelector('#closeBtn')

const formsContainer = document.querySelector('#formsContainer')
const table = document.querySelector('#pacienteTableBody')

const pacienteFormContainer = document.querySelector('#pacienteFormContainer')
const pacienteForm = document.querySelector('#pacienteForm')
const pacienteFormInputs = {
  id: pacienteForm.querySelector('#pacienteId'),
  dni: pacienteForm.querySelector('#dni'),
  nombre: pacienteForm.querySelector('#nombre'),
  apellido: pacienteForm.querySelector('#apellido'),
  fechaAlta: pacienteForm.querySelector('#fechaAlta'),
  submit: pacienteForm.querySelector('button[type="submit"]')
}
const validacionesFormularioPaciente = {
  dni: false,
  nombre: false,
  apellido: false,
  fechaAlta: false
}

const domicilioFormContainer = document.querySelector('#domicilioFormContainer')
const domicilioForm = document.querySelector('#domicilioForm')
const domicilioFormInputs = {
  id: domicilioForm.querySelector('#domicilioId'),
  calle: domicilioForm.querySelector('#calle'),
  numero: domicilioForm.querySelector('#numero'),
  localidad: domicilioForm.querySelector('#localidad'),
  provincia: domicilioForm.querySelector('#provincia'),
  submit: domicilioForm.querySelector('button[type="submit"]')
}
const validacionesFormularioDomicilio = {
  calle: false,
  numero: false,
  localidad: false,
  provincia: false
}

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
  getAllPacientes().then(renderizarPacientes)
}

// FORM FUNCTIONS
function obtenerDatosFormulario() {
  return {
    id: parseInt(pacienteFormInputs.id?.value),
    dni: parseInt(pacienteFormInputs.dni?.value),
    nombre: pacienteFormInputs.nombre?.value,
    apellido: pacienteFormInputs.apellido?.value,
    fechaAlta: pacienteFormInputs.fechaAlta?.value,
    domicilio: {
      id: parseInt(domicilioFormInputs.id.value),
      calle: domicilioFormInputs.calle.value,
      numero: parseInt(domicilioFormInputs.numero.value),
      localidad: domicilioFormInputs.localidad.value,
      provincia: domicilioFormInputs.provincia.value
    }
  }
}

function insertarDatosFormulario({ id, dni, nombre, apellido, domicilio, fechaAlta }) {
  const { id: idDomicilio, calle, numero, localidad, provincia } = domicilio

  pacienteFormInputs.id.value = id
  pacienteFormInputs.dni.value = dni
  pacienteFormInputs.nombre.value = nombre
  pacienteFormInputs.apellido.value = apellido
  pacienteFormInputs.fechaAlta.value = fechaAlta
  pacienteFormInputs.submit.disabled = false

  Object.keys(validacionesFormularioPaciente).forEach(key => (validacionesFormularioPaciente[key] = true))

  pacienteFormInputs.dni.classList.add('ring-green-500')
  pacienteFormInputs.nombre.classList.add('ring-green-500')
  pacienteFormInputs.apellido.classList.add('ring-green-500')
  pacienteFormInputs.fechaAlta.classList.add('ring-green-500')

  domicilioFormInputs.id.value = idDomicilio
  domicilioFormInputs.calle.value = calle
  domicilioFormInputs.numero.value = numero
  domicilioFormInputs.localidad.value = localidad
  domicilioFormInputs.provincia.value = provincia

  Object.keys(validacionesFormularioDomicilio).forEach(key => (validacionesFormularioDomicilio[key] = true))

  domicilioFormInputs.calle.classList.add('ring-green-500')
  domicilioFormInputs.numero.classList.add('ring-green-500')
  domicilioFormInputs.localidad.classList.add('ring-green-500')
  domicilioFormInputs.provincia.classList.add('ring-green-500')
}

function limpiarFormulario() {
  domicilioForm.reset()
  pacienteForm.reset()
}

function mostrarFormulario(textoBoton) {
  limpiarFormulario()
  domicilioFormInputs.submit.textContent = textoBoton
  closeFormButton.classList.remove('hidden')
  formsContainer.classList.remove('hidden')
  domicilioFormContainer.classList.add('hidden')
  pacienteFormContainer.classList.remove('hidden')
}

function ocultarFormulario() {
  limpiarFormulario()
  formsContainer.classList.add('hidden')
  domicilioFormContainer.classList.add('hidden')
  pacienteFormContainer.classList.add('hidden')
  closeFormButton.classList.add('hidden')

  pacienteFormInputs.dni.classList.remove('ring-red-500', 'ring-green-500')
  pacienteFormInputs.nombre.classList.remove('ring-red-500', 'ring-green-500')
  pacienteFormInputs.apellido.classList.remove('ring-red-500', 'ring-green-500')
  pacienteFormInputs.fechaAlta.classList.remove('ring-red-500', 'ring-green-500')
  domicilioFormInputs.calle.classList.remove('ring-red-500', 'ring-green-500')
  domicilioFormInputs.numero.classList.remove('ring-red-500', 'ring-green-500')
  domicilioFormInputs.localidad.classList.remove('ring-red-500', 'ring-green-500')
  domicilioFormInputs.provincia.classList.remove('ring-red-500', 'ring-green-500')
}

function validarTarget({ funcionValidar, target, text }) {
  if (!funcionValidar(target.value)) {
    target.setCustomValidity(text)
    target.reportValidity()
    target.classList.remove('ring-green-500')
    target.classList.add('ring-red-500')
    return false
  } else {
    target.setCustomValidity('')
    target.classList.remove('ring-red-500')
    target.classList.add('ring-green-500')
  }

  return true
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

  validarFormulario()
}

function validarFormulario() {
  pacienteFormInputs.dni.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarNumeros,
      target: e.target,
      text: 'El DNI debe ser un número'
    })

    validacionesFormularioPaciente.dni = isValid
    pacienteFormInputs.submit.disabled = !Object.values(validacionesFormularioPaciente).every(Boolean)
  })

  pacienteFormInputs.nombre.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarTexto,
      target: e.target,
      text: 'El nombre debe contener solo letras'
    })

    validacionesFormularioPaciente.nombre = isValid
    pacienteFormInputs.submit.disabled = !Object.values(validacionesFormularioPaciente).every(Boolean)
  })

  pacienteFormInputs.apellido.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarTexto,
      target: e.target,
      text: 'El apellido debe contener solo letras'
    })

    validacionesFormularioPaciente.apellido = isValid
    pacienteFormInputs.submit.disabled = !Object.values(validacionesFormularioPaciente).every(Boolean)
  })

  pacienteFormInputs.fechaAlta.addEventListener('focusout', e => {
    setTimeout(() => {
      const isValid = validarTarget({
        funcionValidar: validarFecha,
        target: e.target,
        text: 'La fecha debe ser en formato yyyy-mm-dd'
      })

      validacionesFormularioPaciente.fechaAlta = isValid
      pacienteFormInputs.submit.disabled = !Object.values(validacionesFormularioPaciente).every(Boolean)
    }, 500)
  })

  domicilioFormInputs.calle.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarTexto,
      target: e.target,
      text: 'La calle debe contener solo letras'
    })

    validacionesFormularioDomicilio.calle = isValid
    domicilioFormInputs.submit.disabled = !Object.values(validacionesFormularioDomicilio).every(Boolean)
  })

  domicilioFormInputs.numero.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarNumeros,
      target: e.target,
      text: 'El número debe ser un número'
    })

    validacionesFormularioDomicilio.numero = isValid
    domicilioFormInputs.submit.disabled = !Object.values(validacionesFormularioDomicilio).every(Boolean)
  })

  domicilioFormInputs.localidad.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarTexto,
      target: e.target,
      text: 'La localidad debe contener solo letras'
    })

    validacionesFormularioDomicilio.localidad = isValid
    domicilioFormInputs.submit.disabled = !Object.values(validacionesFormularioDomicilio).every(Boolean)
  })

  domicilioFormInputs.provincia.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarTexto,
      target: e.target,
      text: 'La provincia debe contener solo letras'
    })

    validacionesFormularioDomicilio.provincia = isValid
    domicilioFormInputs.submit.disabled = !Object.values(validacionesFormularioDomicilio).every(Boolean)
  })
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
  mostrarFormulario('Agregar Paciente')
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
  mostrarFormulario('Actualizar Paciente')
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
