import { SERVER } from "./constants";


document.getElementById('formRegister')?.addEventListener('submit', async (event) => {
    event.preventDefault();

    const nombre = (document.getElementById('nombre') as HTMLInputElement).value;
    const correo = (document.getElementById('correo') as HTMLInputElement).value;
    const password = (document.getElementById('password') as HTMLInputElement).value;
    const imgInput = document.getElementById('imagen') as HTMLInputElement;
    const imgPreview = document.getElementById('imgPreview') as HTMLImageElement;

    if (!imgInput.files?.length) {
        alert('Por favor, selecciona una imagen.');
        return;
    }

    const reader = new FileReader();
    reader.readAsDataURL(imgInput.files[0]);
    reader.addEventListener('loadend', async () => {
        imgPreview.classList.remove('d-none');
        imgPreview.src = reader.result as string;

        const usuario = {
            nombre,
            correo,
            password,
            avatar: imgPreview.src
        };
        console.log(JSON.stringify(usuario));
        try {
            const response = await fetch(SERVER + "/auth/registro", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(usuario),
            });

            if (!response.ok) {
                throw new Error('Error en el registro');
            }

            alert('Registro exitoso');
            window.location.href = 'login.html';
        } catch (error) {
            alert('Error al registrar');
        }
    });
});

// Manejo de la previsualización de la imagen
const imgInput = document.getElementById('imagen') as HTMLInputElement;
const imgPreview = document.getElementById('imgPreview') as HTMLImageElement;

imgInput.addEventListener('change', () => {
    if (!imgInput.files?.length) return;

    const reader = new FileReader();
    reader.readAsDataURL(imgInput.files[0]);
    reader.addEventListener('loadend', () => {
        imgPreview.classList.remove('d-none');
        imgPreview.src = reader.result as string;
    });
});