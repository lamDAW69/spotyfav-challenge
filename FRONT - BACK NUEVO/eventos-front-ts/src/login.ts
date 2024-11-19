import { AuthService } from "./clases/auth-service";
import { LoginData } from "./interfaces/login";

if(localStorage.getItem("token")) {
    location.assign("index.html");
}

const authService = new AuthService();

const formLogin = document.getElementById("formLogin") as HTMLFormElement;

formLogin.addEventListener("submit", async (e) => {
    e.preventDefault();

    const data: LoginData = {
        correo: formLogin.correo.value,
        password: formLogin.password.value
    }

    try {
        await authService.login(data);
        location.assign("index.html");
    } catch(e) {
        alert("Usuario y/o contraseña incorrectos");
    }
});