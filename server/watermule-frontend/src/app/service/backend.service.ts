import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ThingNode} from "../entity/thing-node";
import {Observable} from "rxjs";
import {Channel} from "../entity/channel";
import {Device} from "../entity/device";

@Injectable({
  providedIn: 'root'
})
export class BackendService {
  protected readonly backendUrl: string;
  protected readonly apiPath: string;

  constructor(protected http: HttpClient) {
    this.backendUrl = ''; // environment.apiEndpoint;
    this.apiPath = '/api/v1';
  }

  fetchNodes(): Observable<ThingNode[]> {
    return this.http.get<ThingNode[]>(`${this.backendUrl}${this.apiPath}/nodes`);
  }

  fetchDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(`${this.backendUrl}${this.apiPath}/devices`);
  }

  fetchChannels(): Observable<Channel[]> {
    return this.http.get<Channel[]>(`${this.backendUrl}${this.apiPath}/channels`);
  }
}
