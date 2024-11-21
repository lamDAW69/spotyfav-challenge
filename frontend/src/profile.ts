import { UserLoggedService } from "./clases/user-service.ts";
import { RegisterData } from "./interfaces/user.ts";
import { AuthService } from "./clases/auth-service.ts";

const userLoggedService = new UserLoggedService();
const authService = new AuthService();

document
  .getElementById("formRegister")
  ?.addEventListener("submit", async (song) => {
    song.preventDefault();

    // const resp = await userLoggedService.getUserLogged();

   

    const nombre = (
      document.getElementById("nombre-modificado") as HTMLInputElement
    ).value;
    const correo = (
      document.getElementById("correo-modificado") as HTMLInputElement
    ).value;
    const password = (
      document.getElementById("password-modificado") as HTMLInputElement
    ).value;
    const imgInput = document.getElementById(
      "imagen-modificado"
    ) as HTMLInputElement;
    const imgPreview = document.getElementById(
      "imgPreview-modificado"
    ) as HTMLImageElement;

    const reader = new FileReader();
    if (!imgInput.files?.length) {
      reader.EMPTY;
    } else {
      reader.readAsDataURL(imgInput.files[0]);
    }

    reader.addEventListener("loadend", async () => {
      imgPreview.classList.remove("d-none");
      imgPreview.src = reader.result as string;

      const registerData: RegisterData = {
        nombre: nombre,
        correo: correo,
        password: password,
        avatar: imgPreview.src,
      };

      const userUpdated = await userLoggedService.updateUserLogged(
        registerData
      );

      console.log("Usuario actualizado", userUpdated);
      //   if (!userUpdated) {
      //     alert("Error al registrar");
      //   } else {
      //     alert("Registro exitoso");
      //   }

      //   window.location.reload();
    });
  });

// Manejo de la previsualización de la imagen
const imgInput = document.getElementById(
  "imagen-modificado"
) as HTMLInputElement;
const imgPreview = document.getElementById(
  "imgPreview-modificado"
) as HTMLImageElement;

imgInput.addEventListener("change", () => {
  if (!imgInput.files?.length) return;

  const reader = new FileReader();
  reader.readAsDataURL(imgInput.files[0]);
  reader.addEventListener("loadend", () => {
    imgPreview.classList.remove("d-none");
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

  (document.getElementById("nombre-modificado") as HTMLInputElement).value =
    resp.nombre;
  (document.getElementById("correo-modificado") as HTMLInputElement).value =
    resp.correo;
  // (document.getElementById("nombre-modificado") as HTMLInputElement).value = resp.nombre;
  // (document.getElementById("nombre-modificado") as HTMLInputElement).value = resp.nombre;
});

document.getElementById("correo")?.addEventListener("click", () => {
  location.assign("profile.html");
});

document.getElementById("logout")?.addEventListener("click", () => {
  authService.logout(); // Borra el token
  location.assign("login.html");
});

document.getElementById("delete-btn")?.addEventListener("click", async() => {
  const confirmDelete = confirm(
    "¿Estás seguro de que deseas eliminar tu cuenta?"
  );
  if (confirmDelete) {
    await userLoggedService.deleteUser();
    authService.logout();
    location.assign("login.html");
  }
});
