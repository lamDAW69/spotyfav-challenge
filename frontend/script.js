const urlUsers = 'http://localhost:8080/users/1';


const tableSongsDatabase = document.querySelector('#tableSongsDatabase tbody');
const tableSongsSpotify = document.querySelector('#tableSongsSpotify tbody');

let limit = 50;
let offset = 0;

function siguientePagina(){
    if(offset <= 1950){
        offset += 50;
        obtenerCancionesFavoritas();
    }
}

function anteriorPagina(){
    if(offset >= 50){
        offset -= 50;
        obtenerCancionesFavoritas();
    }
}

// // Función para redirigir al usuario a la autorización de Spotify
// function authorizeSpotify() {
//     const authUrl = `${authEndpoint}?client_id=${clientId}&redirect_uri=${encodeURIComponent(redirectUri)}&scope=${scopes.join('%20')}&response_type=token&show_dialog=true`;
//     window.location = authUrl;
// }

// Funciones para Usuarios
async function cargarUsuario() {
    let response = await fetch(urlUsers);
    response = await response.json();
    document.getElementById('name').innerText = response.name;
    document.getElementById('mail').innerText = response.mail;

    tableSongsDatabase.innerHTML = '';

    response.favoriteSongs.forEach(song => { 
        const row = document.createElement('tr');

        const idCell = document.createElement('td'); 
        idCell.textContent = song.id;
        const userIdCell = document.createElement('td');
        userIdCell.textContent = song.userId;
        const songIdCell = document.createElement('td');
        songIdCell.textContent = song.songId;
        const songNameCell = document.createElement('td');
        songNameCell.textContent = song.songName;

        row.appendChild(idCell);
        row.appendChild(userIdCell);
        row.appendChild(songIdCell);
        row.appendChild(songNameCell);

        tableSongsDatabase.appendChild(row);
    });
}




async function getSpotifyAccessToken() {
    const clientId = '83b7adb206ed4316ba776cbb53874527';
    const clientSecret = '4e3990842cd14fbf9dfc740a7335f23f'; // Sustituye con tu Client Secret
    const tokenUrl = 'https://accounts.spotify.com/api/token';

    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(clientId + ':' + clientSecret));
    headers.append('Content-Type', 'application/x-www-form-urlencoded');

    const body = new URLSearchParams();
    body.append('grant_type', 'client_credentials');

    try {
        const response = await fetch(tokenUrl, {
            method: 'POST',
            headers: headers,
            body: body
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
        console.error('Failed to fetch access token:', error);
    }
}

async function searchSpotifyTracks(query, accessToken) {
    const searchUrl = `https://api.spotify.com/v1/search?q=${encodeURIComponent(query)}&type=track&limit=10`;

    const headers = new Headers();
    headers.append('Authorization', `Bearer ${accessToken}`);

    try {
        const response = await fetch(searchUrl, {
            method: 'GET',
            headers: headers
        });

        if (!response.ok) {
            throw new Error(`Error en la búsqueda: ${response.status}`);
        }

        const data = await response.json();

        // Procesar resultados
        console.log('Resultados de búsqueda:', data.tracks.items);

        // Retornar la lista de canciones
        return data.tracks.items.map(track => ({
            id: track.id,
            name: track.name,
            artist: track.artists.map(artist => artist.name).join(', '),
            album: track.album.name,
            url: track.external_urls.spotify
        }));
    } catch (error) {
        console.error('Error al buscar canciones en Spotify:', error);
    }
}


const objetoCancion = {
    id: 0,
    userId: 1,
    songId: '',
    songName: ''
}




cargarUsuario();
// Llamar la función para obtener el token
getSpotifyAccessToken().then(token => {
    console.log('Token guardado:', token);

    // Ejemplo de uso
(async () => {
    const query = 'hello'; // Cambia por la canción que desees buscar
    const accessToken = token; // Sustituye con el token válido

    const tracks = await searchSpotifyTracks(query, accessToken);
   // Extraer solo los IDs de las canciones
  
    if (tracks) {

        tracks.forEach(track => {
            const song = track;
            const row = document.createElement('tr');
    
            // const idCell = document.createElement('td'); 
            // idCell.textContent = song.id;
            const songNameCell = document.createElement('td');
            songNameCell.textContent = song.name;
            const artistCell = document.createElement('td');
            artistCell.textContent = song.artist;
            const albumCell = document.createElement('td');
            albumCell.textContent = song.album;
    
            // row.appendChild(idCell);
            row.appendChild(songNameCell);
            row.appendChild(artistCell);
            row.appendChild(albumCell);
    
            tableSongsSpotify.appendChild(row);
            console.log(`ID: ${track.id}`);
            console.log(`Canción: ${track.name}`);
            console.log(`Artista: ${track.artist}`);
            console.log(`Álbum: ${track.album}`);
            console.log(`URL: ${track.url}`);
            console.log('---------------------------');
        });
    }
})();
});



document.getElementById('toggleTheme').addEventListener('click', () => {
    const body = document.body;
    const themeIcon = document.getElementById('themeIcon');
    
    if (body.getAttribute('data-bs-theme') === 'dark') {
        body.setAttribute('data-bs-theme', 'light');
        themeIcon.classList.replace('bi-moon-fill', 'bi-sun-fill'); 
    } else {
        body.setAttribute('data-bs-theme', 'dark');
        themeIcon.classList.replace('bi-sun-fill', 'bi-moon-fill');
    }
});