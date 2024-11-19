import { AuthService } from "./clases/auth-service";
import { SongsService } from "./clases/songs-service";
import { Song } from "./interfaces/song";

if(!localStorage.getItem("token")) {
  location.assign("login.html");
}

const songsService = new SongsService();
const authService = new AuthService();

const eventoTemplate = document.getElementById(
  "eventoTemplate"
) as HTMLTemplateElement;
const cardContainer = document.getElementById(
  "cardContainer"
) as HTMLDivElement;

async function cargarSongs() {
  const songs = await songsService.getSongs();
  songs.forEach(addEvento);
}

function addEvento(song: Song) {
  const clone = eventoTemplate.content.cloneNode(true) as DocumentFragment;
  // const img = clone.querySelector(".card-img-top") as HTMLImageElement;
  // img.src = evento.imagen;
  clone.querySelector(".card-title")!.textContent = song.songName;
  clone.querySelector(".card-text")!.textContent = song.album;

  // clone.querySelector(".fecha")!.textContent = new Intl.DateTimeFormat(
  //   "es-ES",
  //   { dateStyle: "medium" }
  // ).format(new Date(evento.fecha));

  // clone.querySelector(".precio")!.textContent = new Intl.NumberFormat("es-ES", {
  //   style: "currency", currency: "EUR"
  // }).format(evento.precio);

  const col = clone.firstElementChild!; // Obtenemos la referencia al div.col antes de insertar el template
  clone.querySelector("button.delete")!.addEventListener("click", async () => {
    await songsService.deleteSong(song.id!);
    col.remove(); // Borramos el div.col
  });

  cardContainer.append(clone);
}

/* MAIN */
cargarSongs();

document.getElementById("logout")?.addEventListener("click", () => {
  authService.logout(); // Borra el token
  location.assign("login.html");
});
