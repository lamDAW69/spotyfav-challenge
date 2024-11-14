const urlUsers = 'http://localhost:8080/users/1';

// Funciones para Usuarios
async function cargarUsuario() {
    let response = await fetch(urlUsers);
    response = await response.json();
    document.getElementById('name').innerText = response.name;
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

