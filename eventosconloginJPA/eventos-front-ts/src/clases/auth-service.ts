import { SERVER } from "../constants";
import { LoginData } from "../interfaces/login";
import { LoginResponse } from "../interfaces/responses";
import { Http } from "./http";

export class AuthService {
  #http: Http = new Http();

  async login(data: LoginData): Promise<void> {
    const resp = await this.#http.post<LoginResponse, LoginData>(
      `${SERVER}/auth/login`,
      data
    );
    localStorage.setItem("token", resp.accessToken);
  }

  logout() {
    localStorage.removeItem("token");
  }
}
