import { AuthService } from "./clases/auth-service";
import { SongsService } from "./clases/songs-service";
import { Song } from "./interfaces/song";

import { getSpotifyAccessToken , searchSpotifyTracks } from "./clases/spotify.ts";


if(!localStorage.getItem("token")) {
  location.assign("login.html");
}

const eventosService = new SongsService();
const authService = new AuthService();

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
      target.value = target.value.charAt(0).toUpperCase() + target.value.slice(1);
    }
  });

  const tableSongsSpotify = document.getElementById("tableSongsSpotify") as HTMLTableElement;
  
  formulario?.addEventListener("submit", async function (e) {
    e.preventDefault();
    const query = document.getElementById("prompt") as HTMLInputElement | null;
    if (!query) {
      console.error("Query input not found");
      return;
    }
    tableSongsSpotify.innerHTML = "";
    getSpotifyAccessToken().then((token) => {
      console.log("Token guardado:", token);

      // Ejemplo de uso
      (async () => {
        const accessToken = token; // Sustituye con el token válido
        const tracks = await searchSpotifyTracks(query.value, accessToken);
        // Extraer solo los IDs de las canciones

        if (tracks) {
          tracks.forEach((track: any) => {
            const song = track;
            const row = document.createElement("tr");

            console.log(`ID: ${track.id}`);
            console.log(`Canción: ${track.name}`);
            console.log(`Artista: ${track.artist}`);
            console.log(`Álbum: ${track.album}`);
            console.log(`URL: ${track.url}`);
            console.log("---------------------------");

            const likeButton = document.createElement("button");
            likeButton.textContent = "💚";
            likeButton.className = "btn btn-";

            // Añadir event listener al botón
            likeButton.addEventListener("click", function () {
              const row = this.closest("tr"); // Obtener la fila más cercana
              if (row) {
                const cells = row.querySelectorAll("td"); // Obtener todas las celdas de la fila
                const rowData = Array.from(cells).map((cell) => cell.textContent); // Obtener el texto de cada celda
                console.log(rowData);
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

  // formulario.addEventListener('keypress', function (event) {
  //     if (event.key === 'Enter') {
  //         event.preventDefault(); // Evita que se envíe el formulario
  //         document.getElementById('recomendar-btn').click(); // Simula un clic en el botón
  //     }
  // });
}

// const idUser = "yourUserId"; // Define your user ID
// const urlSongs = "https://your-api-endpoint.com/songs"; // Define your API endpoint

async function likeSong(song: string[]) {
  console.log(song);
  const songPost = {
    // userId: 0,
    songName: song[1],
    artist: song[2],
    album: song[3],
  };
  console.log(song[1]);

  //     // const evento: Song = {
//     //     titulo: form.titulo.value,
//     //     descripcion: form.descripcion.value,
//     //     precio: +form.precio.value,
//     //     fecha: form.fecha.value,
//     //     imagen: imgPreview.src
//     // }

    await eventosService.addSong(songPost as Song);
    location.assign("index.html");

  // const response = await fetch(urlSongs, {
  //   method: "POST",
  //   headers: {
  //     "Content-Type": "application/json",
  //   },
  //   body: JSON.stringify(songPost),
  // });

  // if (!response.ok) {
  //   throw new Error(`Error: ${response.status}`);
  // } else {
  //   // cargarUsuario();
  // }

  // const data = await response.json();
  // console.log(data);
}

formularioListener();