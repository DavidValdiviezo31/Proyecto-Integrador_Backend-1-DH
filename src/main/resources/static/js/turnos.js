import { fetchConfig, sweetAlert } from './utils.js'

// VARIABLES DOM
const divTurnos = document.querySelector('#turnos')
const updateButton = document.querySelector('#btnActualizarLista')
const newTurnoButton = document.querySelector('#btnAgregarTurno')
const turnoFormContainer = document.querySelector('#turnoFormContainer')
const turnoForm = document.querySelector('#turnoForm')
const odontologoSearchInput = document.querySelector('#odontologoSearch')
const pacienteSearchInput = document.querySelector('#pacienteSearch')
const odontologoSearchBtn = document.querySelector('#btnBuscarOdontologo')
const pacienteSearchBtn = document.querySelector('#btnBuscarPaciente')
const odontologoInput = document.querySelector('#odontologoBuscado')
const pacienteInput = document.querySelector('#pacienteBuscado')
const closeFormButton = document.querySelector('#closeBtn')

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

  turnos.forEach(({ id, hora, fecha, paciente, odontologo }) => {
    const pte = `${paciente.nombre} ${paciente.apellido}`
    const od = `${odontologo.nombre} ${odontologo.apellido}`

    const elementHTML = `
      <article class="max-w-80 bg-slate-200 rounded-xl p-4 shadow shadow-gray-800">
        <header>
          <h3 class="font-bold text-2xl text-center mb-4">Turno #${id}</h3>
        </header>

        <section>
          <div class="flex gap-2 mb-4">
            <p class="flex gap-1 text-sm items-center bg-blue-900 text-white w-fit px-2 py-1 rounded-full">
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
            <p class="flex gap-1 text-sm items-center bg-blue-900 text-white w-fit px-2 py-1 rounded-full">
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

          <p class="flex gap-2 text-lg items-center italic justify-center w-fit my-2">
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
          <p class="flex gap-2 text-lg items-center italic justify-center w-fit my-2">
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

        <footer class="flex justify-center gap-4 mt-4">
          <button
            type="button"
            id="btnEdit-${id}"
            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
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
            class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
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
}

async function buscarOdontologo(e) {
  const matricula = parseInt(odontologoSearchInput.value)

  if (!matricula) {
    sweetAlert({ type: 'error', text: 'Ingrese una matrícula válida' })
    return
  }

  const listaOdontologos = await getAllOdontologos()

  const odontologoEncontrado = listaOdontologos.find(({ matricula: mat }) => mat === matricula)

  if (!odontologoEncontrado) {
    sweetAlert({ type: 'error', text: 'Odontólogo no encontrado' })
    return
  }

  return odontologoEncontrado
}

async function mostrarOdontologo() {
  const { id, nombre, apellido } = await buscarOdontologo()
  odontologoInput.dataset.id = id
  odontologoInput.value = `Dr. ${nombre} ${apellido}`
}

async function buscarPaciente(e) {
  const dni = parseInt(pacienteSearchInput.value)

  if (!dni) {
    sweetAlert({ type: 'error', text: 'Ingrese un DNI válido' })
    return
  }

  const listaPacientes = await getAllPacientes()

  const pacienteEncontrado = listaPacientes.find(({ dni: d }) => d === dni)

  if (!pacienteEncontrado) {
    sweetAlert({ type: 'error', text: 'Paciente no encontrado' })
    return
  }

  return pacienteEncontrado
}

async function mostrarPaciente() {
  const { id, nombre, apellido } = await buscarPaciente()
  pacienteInput.dataset.id = id
  pacienteInput.value = `${nombre} ${apellido}`
}

// FORM FUNCTIONS
function obtenerDatosFormulario() {
  const id = parseInt(turnoForm.querySelector('#turnoId').value)
  const fecha = document.querySelector('#fechaTurno').value
  const hora = document.querySelector('input[name="timetable"]:checked').value
  const odontologoId = odontologoInput?.dataset?.id
  const pacienteId = pacienteInput?.dataset?.id

  return { id, fecha, hora, odontologoId, pacienteId }
}

function insertarDatosFormulario({ id, fecha, hora, odontologo, paciente }) {
  turnoForm.querySelector('#turnoId').value = id
  turnoForm.querySelector('#fechaTurno').value = fecha
  turnoForm.querySelector(`input[value="${hora.split(':', 2).join(':')}"]`).checked = true
  odontologoSearchInput.value = odontologo.matricula
  pacienteSearchInput.value = paciente.dni
  odontologoInput.dataset.id = odontologo.id
  odontologoInput.value = `Dr. ${odontologo.nombre} ${odontologo.apellido}`
  pacienteInput.dataset.id = paciente.id
  pacienteInput.value = `${paciente.nombre} ${paciente.apellido}`
}

function limpiarFormulario() {
  turnoForm.reset()
}

function mostrarFormulario(textoBoton) {
  limpiarFormulario()
  turnoForm.querySelector('button[type="submit"]').textContent = textoBoton
  closeFormButton.classList.remove('hidden')
  turnoFormContainer.classList.remove('hidden')
}

function ocultarFormulario() {
  limpiarFormulario()
  turnoFormContainer.classList.add('hidden')
  closeFormButton.classList.add('hidden')
}

// EVENT HANDLERS
function agregarEventListeners() {
  updateButton.addEventListener('click', actualizarLista)

  newTurnoButton.addEventListener('click', () => {
    mostrarFormulario('Agendar Turno')
  })

  divTurnos.addEventListener('click', handleClickTurnos)

  turnoForm.addEventListener('submit', handleSubmitFormulario)

  odontologoSearchBtn.addEventListener('click', mostrarOdontologo)
  pacienteSearchBtn.addEventListener('click', mostrarPaciente)

  closeFormButton.addEventListener('click', ocultarFormulario)
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

// MAIN FUNCTION
function main() {
  actualizarLista()
  agregarEventListeners()
}

main()
