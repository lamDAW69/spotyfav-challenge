const urlUsers = 'http://localhost:8080/users/1';

const tableSongs = document.querySelector('#tableSongs tbody');

// Funciones para Usuarios
async function cargarUsuario() {
    let response = await fetch(urlUsers);
    response = await response.json();
    document.getElementById('name').innerText = response.name;
    document.getElementById('mail').innerText = response.mail;

    /*
      "id": 1,
      "userId": 1,
      "songId": 101,
      "songName": "Song 1 by User 1" 
    */

    tableSongs.innerHTML = '';

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

        tableSongs.appendChild(row);
    });
}

cargarUsuario();

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

