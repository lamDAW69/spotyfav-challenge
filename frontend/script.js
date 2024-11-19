const idUser = 1;
const urlUsers = `http://localhost:8080/users/${idUser}`;
const urlSongs = `http://localhost:8080/favoritesongs`;

const tableSongsDatabase = document.querySelector("#tableSongsDatabase tbody");
const tableSongsSpotify = document.querySelector("#tableSongsSpotify tbody");

let limit = 50;
let offset = 0;

// Funciones para Usuarios
async function cargarUsuario() {
  let response = await fetch(urlUsers);
  response = await response.json();
     document.getElementById("name").innerText = response.name;
     document.getElementById("mail").innerText = response.mail;

  tableSongsDatabase.innerHTML = "";

  response.favoriteSongs.forEach((song) => {
    const row = document.createElement("tr");
    const likeButton = document.createElement("button");
    likeButton.textContent = "💔";
    likeButton.className = "btn btn-outline-dark";

    // Añadir event listener al botón
    likeButton.addEventListener("click", function () {
      const row = this.closest("tr"); // Obtener la fila más cercana
      const cells = row.querySelectorAll("td"); // Obtener todas las celdas de la fila
      const rowData = Array.from(cells).map((cell) => cell.textContent); // Obtener el texto de cada celda
      console.log(rowData);
      unlikeSong(rowData[1]);
    });

    const buttonCell = document.createElement("td");
    buttonCell.appendChild(likeButton);
    const idCell = document.createElement("td");
    idCell.textContent = song.id;
    const userIdCell = document.createElement("td");
    userIdCell.textContent = song.songName;
    const songIdCell = document.createElement("td");
    songIdCell.textContent = song.artist;
    const songNameCell = document.createElement("td");
    songNameCell.textContent = song.album;

    row.appendChild(buttonCell);
    row.appendChild(idCell);
    row.appendChild(userIdCell);
    row.appendChild(songIdCell);
    row.appendChild(songNameCell);

    tableSongsDatabase.appendChild(row);
  });
}

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

async function searchSpotifyTracks(query, accessToken) {
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
    return data.tracks.items.map((track) => ({
      id: track.id,
      name: track.name,
      artist: track.artists.map((artist) => artist.name).join(", "),
      album: track.album.name,
      url: track.external_urls.spotify,
    }));
  } catch (error) {
    console.error("Error al buscar canciones en Spotify:", error);
  }
}

function formularioListener() {
  const formulario = document.getElementById("specs");

  document.getElementById("prompt").addEventListener("input", function (event) {
    let value = event.target.value;
    if (value.length > 0) {
      event.target.value = value.charAt(0).toUpperCase() + value.slice(1);
    }
  });

  formulario.addEventListener("submit", async function (e) {
    e.preventDefault();
    const query = document.getElementById("prompt");
    tableSongsSpotify.innerHTML = "";
    getSpotifyAccessToken().then((token) => {
      console.log("Token guardado:", token);

      // Ejemplo de uso
      (async () => {
        const accessToken = token; // Sustituye con el token válido
        const tracks = await searchSpotifyTracks(query.value, accessToken);
        // Extraer solo los IDs de las canciones

        if (tracks) {
          tracks.forEach((track) => {
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
              const cells = row.querySelectorAll("td"); // Obtener todas las celdas de la fila
              const rowData = Array.from(cells).map((cell) => cell.textContent); // Obtener el texto de cada celda
              console.log(rowData);
              likeSong(rowData);
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

async function likeSong(song) {
  const songPost = {
    userId: idUser,
    songName: song[1],
    artist: song[2],
    album: song[3],
  };
  console.log(song[1]);

  const response = await fetch(urlSongs, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(songPost),
  });

  if (!response.ok) {
    throw new Error(`Error: ${response.status}`);
  } else {
    cargarUsuario();
  }

  const data = await response.json();
  console.log(data);
}

async function unlikeSong(idSong) {
  const urlDeleteSong = `http://localhost:8080/favoritesongs/id/${idSong}`; // Asegúrate de que esta URL sea correcta

  const response = await fetch(urlDeleteSong, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    }
  });

  if (!response.ok) {
    throw new Error(`Error: ${response.status}`);
  } else {
    console.log(`Song with ID ${idSong} deleted successfully.`);
    cargarUsuario(); // Recargar la lista de canciones favoritas
  }
}

cargarUsuario();
formularioListener();

document.getElementById("toggleTheme").addEventListener("click", () => {
  const body = document.body;
  const themeIcon = document.getElementById("themeIcon");

  if (body.getAttribute("data-bs-theme") === "dark") {
    body.setAttribute("data-bs-theme", "light");
    themeIcon.classList.replace("bi-moon-fill", "bi-sun-fill");
  } else {
    body.setAttribute("data-bs-theme", "dark");
    themeIcon.classList.replace("bi-sun-fill", "bi-moon-fill");
  }
});
