const urlUsers = 'http://localhost:8080/users/1';

const tableSongsDatabase = document.querySelector('#tableSongsDatabase tbody');
const tableSongsSpotify = document.querySelector('#tableSongsSpotify tbody');

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

function guardarToken() {
    // Extrae el token del fragmento de la URL (después del '#')
    const getTokenFromUrl = () => {
        const hash = window.location.hash.substring(1);
        const params = new URLSearchParams(hash);
        return params.get('access_token');
    };

    const accessToken = getTokenFromUrl();

    if (accessToken) {
        console.log('Token de acceso:', accessToken);
        // Guarda el token en localStorage
        localStorage.setItem('spotifyAccessToken', accessToken);
    }
}

async function obtenerCancionesFavoritas() {
    const accessToken = localStorage.getItem('spotifyAccessToken');

    if (!accessToken) {
        console.error('Token de acceso no encontrado');
        return;
    }

    try {
        const response = await fetch('https://api.spotify.com/v1/me/tracks', {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });

        if (response.ok) {
            const data = await response.json();
            console.log('Canciones favoritas:', data);

            // Actualiza la tabla con las canciones favoritas de Spotify
            tableSongsSpotify.innerHTML = '';

            data.items.forEach(item => { 
                const song = item.track;
                const row = document.createElement('tr');

                const idCell = document.createElement('td'); 
                idCell.textContent = song.id;
                const songNameCell = document.createElement('td');
                songNameCell.textContent = song.name;
                const artistCell = document.createElement('td');
                artistCell.textContent = song.artists.map(artist => artist.name).join(', ');
                const albumCell = document.createElement('td');
                albumCell.textContent = song.album.name;

                row.appendChild(idCell);
                row.appendChild(songNameCell);
                row.appendChild(artistCell);
                row.appendChild(albumCell);

                tableSongsSpotify.appendChild(row);
            });
        } else {
            console.error('Error al obtener las canciones favoritas:', response.status);
        }
    } catch (error) {
        console.error('Error al conectar con la API de Spotify:', error);
    }
}

cargarUsuario();
guardarToken();

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

