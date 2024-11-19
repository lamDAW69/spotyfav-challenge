import { AuthService } from "./clases/auth-service";
import { SongsService } from "./clases/songs-service";
import { Song } from "./interfaces/song";

if(!localStorage.getItem("token")) {
  location.assign("login.html");
}

const eventosService = new SongsService();
const authService = new AuthService();

// const imgPreview = document.getElementById("imgPreview") as HTMLImageElement;
const form = document.getElementById("formEvento") as HTMLFormElement;

// const imgInput = form.imagen as HTMLInputElement;
// imgInput.addEventListener("change", () => {
//   if (!imgInput.files?.length) return;

//   const reader = new FileReader();
//   reader.readAsDataURL(imgInput.files[0]);
//   reader.addEventListener(
//     "loadend",
//     () => {
//         imgPreview.classList.remove("d-none");
//         imgPreview.src = reader.result as string;
//     }
//   );
// });

// form.addEventListener("submit", async e => {
//     e.preventDefault();

//     // const evento: Song = {
//     //     titulo: form.titulo.value,
//     //     descripcion: form.descripcion.value,
//     //     precio: +form.precio.value,
//     //     fecha: form.fecha.value,
//     //     imagen: imgPreview.src
//     // }

//     // await eventosService.addEvento(evento);
//     // location.assign("index.html");
// });

document.getElementById("logout")?.addEventListener("click", () => {
  authService.logout(); // Borra el token
  location.assign("login.html");
});



async function getSpotifyAccessToken() {
  const clientId = "83b7adb206ed4316ba776cbb53874527";
  const clientSecret = "4e3990842cd14fbf9dfc740a7335f23f"; // Sustituye con tu Client Secret
  const tokenUrl = "https://accounts.spotify.com/api/token";

  const headers = new Headers();
  headers.append(
    "Authorization",
    "Basic " + btoa(clientId + ":" + clientSecret)
  );
  headers.append("Content-Type", "application/x-www-form-urlencoded");

  const body = new URLSearchParams();
  body.append("grant_type", "client_credentials");

  try {
    const response = await fetch(tokenUrl, {
      method: "POST",
      headers: headers,
      body: body,
    });

    if (!response.ok) {
      throw new Error(`Error: ${response.status}`);
    }

    const data = await response.json();
    const accessToken = data.access_token;
    // console.log('Access Token:', accessToken);

    // Asignar el token a una variable
    return accessToken;
  } catch (error) {
    console.error("Failed to fetch access token:", error);
  }
}

async function searchSpotifyTracks(query: string, accessToken: string) {
  const searchUrl = `https://api.spotify.com/v1/search?q=${encodeURIComponent(
    query
  )}&type=track&limit=10`;

  const headers = new Headers();
  headers.append("Authorization", `Bearer ${accessToken}`);

  try {
    const response = await fetch(searchUrl, {
      method: "GET",
      headers: headers,
    });

    if (!response.ok) {
      throw new Error(`Error en la búsqueda: ${response.status}`);
    }

    const data = await response.json();

    // Procesar resultados
    console.log("Resultados de búsqueda:", data.tracks.items);

    // Retornar la lista de canciones
    return data.tracks.items.map((track: any) => ({
      id: track.id,
      name: track.name,
      artist: track.artists.map((artist: any) => artist.name).join(", "),
      album: track.album.name,
      url: track.external_urls.spotify,
    }));
  } catch (error) {
    console.error("Error al buscar canciones en Spotify:", error);
  }
}

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
            likeButton.textContent = "💜";
            likeButton.className = "btn btn-outline-dark";

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