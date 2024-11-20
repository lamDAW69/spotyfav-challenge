import { UserLoggedService } from "./clases/user-service.ts";
import { SERVER } from "./constants";
import { RegisterData } from "./interfaces/user.ts";
import { Http } from "./clases/http.ts";


const userLoggedService = new UserLoggedService();
const http = new Http();

document.getElementById('formRegister')?.addEventListener('submit', async (event) => {
    event.preventDefault();

    const nombre = (document.getElementById('nombre-modificado') as HTMLInputElement).value;
    const correo = (document.getElementById('correo-modificado') as HTMLInputElement).value;
    const password = (document.getElementById('password-modificado') as HTMLInputElement).value;
    const imgInput = document.getElementById('imagen-modificado') as HTMLInputElement;
    const imgPreview = document.getElementById('imgPreview-modificado') as HTMLImageElement;

    if (!imgInput.files?.length) {
        alert('Por favor, selecciona una imagen.');
        return;
    }

    const resp = await userLoggedService.getUserLogged();


    const reader = new FileReader();
    reader.readAsDataURL(imgInput.files[0]);
    reader.addEventListener('loadend', async () => {
        imgPreview.classList.remove('d-none');
        imgPreview.src = reader.result as string;

        // const usuario = {
        //     nombre,
        //     correo,
        //     password,
        //     avatar: imgPreview.src
        // };
        
        const registerData: RegisterData = {
            nombre : nombre,
            correo : correo,
            avatar : imgPreview.src,
            password : password, 
            cancionesFavoritas : resp.cancionesFavoritas 
        }



        const userUpdated = userLoggedService.updateUserLogged(registerData)

        console.log('Usuario actualizado', registerData);
        if (!userUpdated) {
            alert('Error al registrar');
        } else {
            alert('Registro exitoso');

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

document.addEventListener("DOMContentLoaded", async () => {
    const profilePic = document.getElementById("profilePic") as HTMLImageElement;
    const correo = document.getElementById("correo") as HTMLSpanElement;
  
    // Simulación de obtener datos del usuario (puedes reemplazar esto con una llamada a tu API)
    const resp = await userLoggedService.getUserLogged();
    console.log("Usuario log", resp.correo);
  
    // Actualizar el DOM con los datos del usuario
    profilePic.src = resp.avatar;
    correo.textContent = resp.correo;
  });
  

  

