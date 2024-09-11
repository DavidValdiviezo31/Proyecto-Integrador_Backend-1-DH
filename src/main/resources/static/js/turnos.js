import { fetchConfig, sweetAlert, validarNumeros, validarTexto, validarFecha, validarFechaPosterior } from './utils.js'

// VARIABLES DOM
const divTurnos = document.querySelector('#turnos')
const newTurnoButton = document.querySelector('#btnAgregarTurno')
const updateButton = document.querySelector('#btnActualizarLista')
const closeFormButton = document.querySelector('#closeBtn')

const turnoFormContainer = document.querySelector('#turnoFormContainer')
const turnoForm = document.querySelector('#turnoForm')
const odontologoSearch = document.querySelector('#odontologoSearch')
const pacienteSearchInput = document.querySelector('#pacienteSearch')
const pacienteSearchBtn = document.querySelector('#btnBuscarPaciente')
const pacienteInput = document.querySelector('#pacienteBuscado')
const idInput = turnoForm.querySelector('#turnoId')
const fechaInput = turnoForm.querySelector('#fechaTurno')
const horaInputs = turnoForm.querySelectorAll('input[name="timetable"]')
const listaHoras = turnoForm.querySelector('#timetable')
const submitButton = turnoForm.querySelector('button[type="submit"]')

const filterForm = document.querySelector('#filterForm')
const odontologoFilter = document.querySelector('#odontologoFilter')
const fechaFilter = document.querySelector('#fechaFilter')

const validacionesFormulario = {
  fecha: false,
  hora: false,
  odontologo: false,
  pacienteSearch: false,
  paciente: false
}

// CRUD METHODS
async function postTurnos({ fecha, hora, odontologo, paciente }) {
  const config = fetchConfig({ method: 'POST', data: { fecha, hora, odontologo, paciente } })

  try {
    const res = await fetch('/turnos', config)
    if (!res.ok) throw new Error('Error al crear turno.')

    sweetAlert({ type: 'success', title: 'Turno creado' })

    return await res.json()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function getTurnoById(id) {
  const config = fetchConfig({ method: 'GET' })

  try {
    const res = await fetch(`/turnos/${id}`, config)
    if (!res.ok) throw new Error('Error al buscar turno')

    return await res.json()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function getAllTurnos() {
  const config = fetchConfig({ method: 'GET' })

  try {
    const res = await fetch('/turnos', config)
    if (!res.ok) throw new Error('Error al cargar la lista de turnos')

    return await res.json()
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function putTurno({ id, fecha, hora, odontologo, paciente }) {
  const config = fetchConfig({ method: 'PUT', data: { fecha, hora, odontologo, paciente } })

  try {
    const res = await fetch(`/turnos/${id}`, config)
    if (!res.ok) throw new Error('Error al actualizar turno')

    sweetAlert({ type: 'success', title: 'Turno actualizado' })
  } catch ({ message }) {
    sweetAlert({ type: 'error', text: message })
  }
}

async function deleteTurno(id) {
  const config = fetchConfig({ method: 'DELETE' })

  try {
    const res = await fetch(`/turnos/${id}`, config)
    if (!res.ok) throw new Error('Error al eliminar turno')

    sweetAlert({ type: 'success', title: 'Turno eliminado' })
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

// UTILS FUNCTIONS
function renderizarTurnos(turnos) {
  divTurnos.innerHTML = ''
  odontologoFilter.innerHTML = '<option selected disabled>Selecciona un Odontólogo</option>'

  const odontologos = Array.from(new Set(turnos.map(({ odontologo }) => odontologo).map(JSON.stringify))).map(
    JSON.parse
  )

  odontologos.forEach(({ id, nombre, apellido }) => {
    const elementHTML = `
      <option value="${id}">Dr. ${nombre} ${apellido}</option>
    `

    odontologoFilter.insertAdjacentHTML('beforeend', elementHTML)
  })

  turnos.forEach(({ id, hora, fecha, paciente, odontologo }) => {
    const pte = `${paciente.nombre} ${paciente.apellido}`
    const od = `${odontologo.nombre} ${odontologo.apellido}`

    const elementHTML = `
      <article class="w-72 bg-gradient-to-br from-sky-200 via-sky-300 to-sky-400 shadow-md shadow-sky-500/40 rounded-xl p-4">
        <header>
          <h3 class="font-bold text-2xl text-center mb-4 italic">Turno #${id}</h3>
        </header>

        <section>
          <div class="flex gap-2 mb-4">
            <p class="flex gap-1 text-base items-center bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 shadow shadow-purple-500/50 text-white w-fit px-3 py-1 rounded-full">
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
                class="icon icon-tabler icons-tabler-outline icon-tabler-calendar"
              >
                <path
                  stroke="none"
                  d="M0 0h24v24H0z"
                  fill="none"
                />
                <path d="M4 7a2 2 0 0 1 2 -2h12a2 2 0 0 1 2 2v12a2 2 0 0 1 -2 2h-12a2 2 0 0 1 -2 -2v-12z" />
                <path d="M16 3v4" />
                <path d="M8 3v4" />
                <path d="M4 11h16" />
                <path d="M11 15h1" />
                <path d="M12 15v3" />
              </svg>
              ${fecha}
            </p>
            <p class="flex gap-1 text-base items-center bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 shadow shadow-purple-500/50 text-white w-fit px-3 py-1 rounded-full">
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
                class="icon icon-tabler icons-tabler-outline icon-tabler-clock"
              >
                <path
                  stroke="none"
                  d="M0 0h24v24H0z"
                  fill="none"
                />
                <path d="M3 12a9 9 0 1 0 18 0a9 9 0 0 0 -18 0" />
                <path d="M12 7v5l3 3" />
              </svg>
              ${hora.split(':', 2).join(':')}
            </p>
          </div>

          <p class="flex gap-2 text-lg items-center italic justify-center w-fit my-4">
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
              class="icon icon-tabler icons-tabler-outline icon-tabler-user"
            >
              <path
                stroke="none"
                d="M0 0h24v24H0z"
                fill="none"
              />
              <path d="M8 7a4 4 0 1 0 8 0a4 4 0 0 0 -8 0" />
              <path d="M6 21v-2a4 4 0 0 1 4 -4h4a4 4 0 0 1 4 4v2" />
            </svg>
            <span class="font-bold">Paciente:</span> ${pte}
          </p>
          <p class="flex gap-2 text-lg items-center italic justify-center w-fit my-4">
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
              class="icon icon-tabler icons-tabler-outline icon-tabler-dental"
            >
              <path
                stroke="none"
                d="M0 0h24v24H0z"
                fill="none"
              />
              <path
                d="M12 5.5c-1.074 -.586 -2.583 -1.5 -4 -1.5c-2.1 0 -4 1.247 -4 5c0 4.899 1.056 8.41 2.671 10.537c.573 .756 1.97 .521 2.567 -.236c.398 -.505 .819 -1.439 1.262 -2.801c.292 -.771 .892 -1.504 1.5 -1.5c.602 0 1.21 .737 1.5 1.5c.443 1.362 .864 2.295 1.262 2.8c.597 .759 2 .993 2.567 .237c1.615 -2.127 2.671 -5.637 2.671 -10.537c0 -3.74 -1.908 -5 -4 -5c-1.423 0 -2.92 .911 -4 1.5z"
              />
              <path d="M12 5.5l3 1.5" />
            </svg>
            <span class="font-bold">Od:</span>Dr. ${od}
          </p>
        </section>

        <footer class="flex justify-center gap-4 mt-6">
          <button
            type="button"
            id="btnEdit-${id}"
            class="bg-blue-600 hover:bg-blue-800 text-white font-bold py-2 px-4 rounded transition-all"
          >
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
          <button
            type="button"
            id="btnDelete-${id}"
            class="bg-red-600 hover:bg-red-800 text-white font-bold py-2 px-4 rounded transition-all"
          >
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
        </footer>
      </article>
    `

    divTurnos.insertAdjacentHTML('beforeend', elementHTML)
  })
}

function actualizarLista() {
  getAllTurnos().then(renderizarTurnos)
  mostrarOdontologos()
}

async function mostrarOdontologos() {
  const listaOdontologos = await getAllOdontologos()

  odontologoSearch.innerHTML = '<option selected disabled>Selecciona un Odontólogo</option>'

  listaOdontologos.forEach(({ id, nombre, apellido }) => {
    const elementHTML = `
      <option value="${id}">Dr. ${nombre} ${apellido}</option>
    `

    odontologoSearch.insertAdjacentHTML('beforeend', elementHTML)
  })
}

function filtrarTurnos(turnos, odontologoId, fecha) {
  odontologoId = parseInt(odontologoId) || ''

  if (odontologoId && fecha)
    return turnos.filter(({ odontologo: { id }, fecha: f }) => id === odontologoId && f === fecha)

  if (odontologoId) return turnos.filter(({ odontologo: { id } }) => id === odontologoId)

  if (fecha) return turnos.filter(({ fecha: f }) => f === fecha)

  return turnos
}

async function buscarPaciente() {
  const dni = parseInt(pacienteSearchInput.value)

  if (!dni) {
    sweetAlert({ type: 'error', text: 'Ingrese un DNI válido' })
    return
  }

  const listaPacientes = await getAllPacientes()

  const pacienteEncontrado = listaPacientes.find(({ dni: d }) => d === dni)

  if (!pacienteEncontrado) {
    sweetAlert({ type: 'error', text: 'No existe un paciente con ese DNI' })
    pacienteInput.value = ''
    return
  }

  return pacienteEncontrado
}

async function mostrarPaciente() {
  const paciente = await buscarPaciente()
  if (!paciente) return
  pacienteInput.dataset.id = paciente?.id
  pacienteInput.value = `${paciente?.nombre} ${paciente?.apellido}`
}

// FORM FUNCTIONS
function obtenerDatosFormulario() {
  const id = parseInt(idInput.value)
  const fecha = fechaInput.value
  const hora = turnoForm.querySelector('input[name="timetable"]:checked').value
  const odontologoId = odontologoSearch?.value
  const pacienteId = pacienteInput?.dataset?.id

  return { id, fecha, hora, odontologoId, pacienteId }
}

function insertarDatosFormulario({ id, fecha, hora, odontologo, paciente }) {
  idInput.value = id
  fechaInput.value = fecha
  turnoForm.querySelector(`input[value="${hora.split(':', 2).join(':')}"]`).checked = true
  odontologoSearch.value = odontologo.id
  pacienteSearchInput.value = paciente.dni
  pacienteInput.dataset.id = paciente.id
  pacienteInput.value = `${paciente.nombre} ${paciente.apellido}`
  submitButton.disabled = false

  Object.keys(validacionesFormulario).forEach(key => (validacionesFormulario[key] = true))

  fechaInput.classList.add('ring-green-500')
  odontologoSearch.classList.add('ring-green-500')
  pacienteSearchInput.classList.add('ring-green-500')
}

function limpiarFormulario() {
  turnoForm.reset()
}

function limpiarFiltros() {
  filterForm.reset()
  actualizarLista()
}

function mostrarFormulario(textoBoton) {
  limpiarFormulario()
  submitButton.textContent = textoBoton
  closeFormButton.classList.remove('hidden')
  turnoFormContainer.classList.remove('hidden')
}

function ocultarFormulario() {
  limpiarFormulario()
  turnoFormContainer.classList.add('hidden')
  closeFormButton.classList.add('hidden')

  fechaInput.classList.remove('ring-green-500', 'ring-red-500')
  odontologoSearch.classList.remove('ring-green-500', 'ring-red-500')
  pacienteSearchInput.classList.remove('ring-green-500', 'ring-red-500')
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
  updateButton.addEventListener('click', actualizarLista)

  newTurnoButton.addEventListener('click', () => {
    mostrarFormulario('Agendar Turno')
  })

  divTurnos.addEventListener('click', handleClickTurnos)

  turnoForm.addEventListener('submit', handleSubmitFormulario)

  pacienteSearchBtn.addEventListener('click', mostrarPaciente)

  closeFormButton.addEventListener('click', ocultarFormulario)

  filterForm.addEventListener('submit', handleSubmitFilterForm)

  filterForm.querySelector('button[type="reset"]').addEventListener('click', limpiarFiltros)

  validarFormulario()
}

function validarFormulario() {
  fechaInput.addEventListener('focusout', e => {
    setTimeout(() => {
      const isValidDate = validarTarget({
        funcionValidar: validarFecha,
        target: e.target,
        text: 'La fecha debe ser en formato yyyy-mm-dd'
      })

      const isLaterDate = validarTarget({
        funcionValidar: validarFechaPosterior,
        target: e.target,
        text: 'La fecha no puede ser anterior a la actual'
      })

      validacionesFormulario.fecha = isValidDate && isLaterDate
      submitButton.disabled = !Object.values(validacionesFormulario).every(Boolean)
    }, 100)
  })

  listaHoras.addEventListener('click', () => {
    setTimeout(() => {
      const isChecked = Array.from(horaInputs).some(hora => hora.checked)

      if (!isChecked) {
        sweetAlert({ type: 'warning', text: 'Seleccione una hora' })
        validacionesFormulario.hora = false
        submitButton.disabled = !Object.values(validacionesFormulario).every(Boolean)
      }

      validacionesFormulario.hora = true
      submitButton.disabled = !Object.values(validacionesFormulario).every(Boolean)
    }, 500)
  })

  odontologoSearch.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarNumeros,
      target: e.target,
      text: 'Seleccione un odontólogo'
    })

    validacionesFormulario.odontologo = isValid
    submitButton.disabled = !Object.values(validacionesFormulario).every(Boolean)
  })

  pacienteSearchInput.addEventListener('change', e => {
    const isValid = validarTarget({
      funcionValidar: validarNumeros,
      target: e.target,
      text: 'Ingrese un DNI válido'
    })

    validacionesFormulario.pacienteSearch = isValid
    submitButton.disabled = !Object.values(validacionesFormulario).every(Boolean)
  })

  pacienteSearchBtn.addEventListener('click', () => {
    setTimeout(() => {
      const isValid = validarTarget({
        funcionValidar: validarTexto,
        target: pacienteInput,
        text: 'No existe un paciente con ese DNI'
      })

      validacionesFormulario.paciente = isValid
      submitButton.disabled = !Object.values(validacionesFormulario).every(Boolean)
    }, 250)
  })
}

async function handleClickTurnos(e) {
  const btn = e.target.closest('button')?.id
  if (!btn) return

  if (btn.includes('btnDelete')) {
    await handleEliminarTurno(btn)
  } else if (btn.includes('btnEdit')) {
    await handleEditarTurno(btn)
  }
}

async function handleEliminarTurno(btn) {
  const { isConfirmed } = await Swal.fire({
    title: '¿Estas seguro que deseas eliminar el Odontólogo?',
    showCancelButton: true,
    confirmButtonText: `Eliminar`,
    confirmButtonColor: '#DC2626',
    cancelButtonText: `Cancelar`
  })

  if (!isConfirmed) return

  const id = btn.split('-')[1]
  await deleteTurno(id)
  actualizarLista()
}

async function handleEditarTurno(btn) {
  const id = btn.split('-')[1]
  const turno = await getTurnoById(id)
  mostrarFormulario('Actualizar Turno')
  insertarDatosFormulario(turno)
}

async function handleSubmitFormulario(e) {
  e.preventDefault()

  const { id, fecha, hora, odontologoId, pacienteId } = obtenerDatosFormulario()

  if (id) {
    await putTurno({ id, fecha, hora, odontologo: { id: odontologoId }, paciente: { id: pacienteId } })
  } else {
    await postTurnos({ fecha, hora, odontologo: { id: odontologoId }, paciente: { id: pacienteId } })
  }

  ocultarFormulario()
  actualizarLista()
}

async function handleSubmitFilterForm(e) {
  e.preventDefault()

  const turnos = await getAllTurnos()

  const filteredTurnos = filtrarTurnos(turnos, odontologoFilter.value, fechaFilter.value)

  renderizarTurnos(filteredTurnos)
}

// MAIN FUNCTION
function main() {
  mostrarOdontologos()
  actualizarLista()
  agregarEventListeners()
}

main()
