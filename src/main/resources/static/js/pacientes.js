const pacienteModel = {
  dni: 123123123,
  nombre: 'Juan',
  apellido: 'Perez',
  domicilio: {
    calle: 'Avenida Siempreviva',
    numero: 742,
    localidad: 'Springfield',
    provincia: 'Springfield'
  },
  fechaAlta: '2020-01-01'
}

const pacienteFormContainer = document.querySelector('#pacienteFormContainer')
const domicilioFormContainer = document.querySelector('#domicilioFormContainer')

pacienteFormContainer.addEventListener('submit', async (e) => {
  e.preventDefault()

  pacienteFormContainer.classList.add('hidden')
  domicilioFormContainer.classList.remove('hidden')
})