import { AuthService } from "./clases/auth-service";
import { SongsService } from "./clases/songs-service";
import { Song } from "./interfaces/song";

import {
  getSpotifyAccessToken,
  searchSpotifyTracks,
} from "./clases/spotify.ts";
import { UserLoggedService } from "./clases/user-service.ts";
import { SERVER } from "./constants.ts";

if (!localStorage.getItem("token")) {
  location.assign("login.html");
}

const eventosService = new SongsService();
const authService = new AuthService();
const userLoggedService = new UserLoggedService();
// const imgPreview = document.getElementById("imgPreview") as HTMLImageElement;
// const form = document.getElementById("formEvento") as HTMLFormElement;

document.getElementById("logout")?.addEventListener("click", () => {
  authService.logout(); // Borra el token
  location.assign("login.html");
});

function formularioListener() {
  const formulario = document.getElementById("specs");

  const promptInput = document.getElementById("prompt");
  promptInput?.addEventListener("input", function (event) {
    const target = event.target as HTMLInputElement | null;
    if (target && target.value.length > 0) {
      target.value =
        target.value.charAt(0).toUpperCase() + target.value.slice(1);
    }
  });

  const tableSongsSpotify = document.getElementById(
    "tableSongsSpotify"
  ) as HTMLTableElement;

  formulario?.addEventListener("submit", async function (e) {
    e.preventDefault();
    const query = document.getElementById("prompt") as HTMLInputElement | null;
    if (!query) {
      console.error("Query input not found");
      return;
    }
    tableSongsSpotify.innerHTML = "";
    getSpotifyAccessToken().then((token) => {
      // Ejemplo de uso
      (async () => {
        const accessToken = token; // Sustituye con el token válido
        const tracks = await searchSpotifyTracks(query.value, accessToken);
        // Extraer solo los IDs de las canciones

        if (tracks) {
          tracks.forEach((track: any) => {
            const song = track;
            const row = document.createElement("tr");

            const likeButton = document.createElement("button");
            likeButton.textContent = "💚";
            likeButton.className = "btn btn-";

            // Añadir event listener al botón
            likeButton.addEventListener("click", function () {
              const row = this.closest("tr"); // Obtener la fila más cercana
              if (row) {
                const cells = row.querySelectorAll("td"); // Obtener todas las celdas de la fila
                const rowData = Array.from(cells).map(
                  (cell) => cell.textContent
                ); // Obtener el texto de cada celda
                likeSong(rowData as string[]);
              }
            });

            const likeCell = document.createElement("td");
            likeCell.appendChild(likeButton);
            const songNameCell = document.createElement("td");
            songNameCell.textContent = song.name;
            const artistCell = document.createElement("td");
            artistCell.textContent = song.artist;
            const albumCell = document.createElement("td");
            albumCell.textContent = song.album;

            row.appendChild(likeCell);
            row.appendChild(songNameCell);
            row.appendChild(artistCell);
            row.appendChild(albumCell);

            tableSongsSpotify.appendChild(row);
          });
        }
      })();
    });
  });
}

async function likeSong(song: string[]) {
  const songPost = {
    songName: song[1],
    artist: song[2],
    album: song[3],
  };

  await eventosService.addSong(songPost as Song);
  location.assign("index.html");
}

const profilePicNav = document.getElementById("profilePic") as HTMLImageElement;
const correoNav = document.getElementById("correo") as HTMLSpanElement;

const resp = await userLoggedService.getUserLogged();

if (resp.avatar == SERVER + "/") {
  profilePicNav.src = "./src/img/gata.webp";
} else {
  profilePicNav.src = resp.avatar;
}
correoNav.textContent = resp.correo;

document.getElementById("correo")?.addEventListener("click", () => {
  location.assign("profile.html");
});

formularioListener();
