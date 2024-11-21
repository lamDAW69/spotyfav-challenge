export async function getSpotifyAccessToken() {
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

      // Asignar el token a una variable
      return accessToken;
    } catch (error) {
      console.error("Failed to fetch access token:", error);
    }
  }
  
  export async function searchSpotifyTracks(query: string, accessToken: string) {
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

      // Retornar la lista de canciones
      return data.tracks.items.map((track: any) => ({
        id: track.id,
        name: track.name,
        artist: track.artists.map((artist: any) => artist.name).join(", "),
        album: track.album.name,
        url: track.album.images[0].url,
      }));
    } catch (error) {
      console.error("Error al buscar canciones en Spotify:", error);
    }
  }