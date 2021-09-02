import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
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
    this.backendUrl = '';
    this.apiPath = '/api/v1';
  }

  fetchNodes(): Observable<ThingNode[]> {
    return this.http.get<ThingNode[]>(`${this.backendUrl}${this.apiPath}/nodes`);
  }

  saveNode(dto: ThingNode): Observable<ThingNode> {
    return this.http.post<ThingNode>(`${this.backendUrl}${this.apiPath}/nodes`, dto);
  }

  fetchDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(`${this.backendUrl}${this.apiPath}/devices`);
  }

  saveDevice(dto: Device): Observable<Device> {
    return this.http.post<Device>(`${this.backendUrl}${this.apiPath}/devices`, dto);
  }

  fetchChannels(): Observable<Channel[]> {
    return this.http.get<Channel[]>(`${this.backendUrl}${this.apiPath}/channels`);
  }

  saveChannel(dto: Channel): Observable<Channel> {
    return this.http.post<Channel>(`${this.backendUrl}${this.apiPath}/channels`, dto);
  }

  deleteNode(id: string): Observable<any> {
    const params = new HttpParams().set('id', id);
    return this.http.delete(`${this.backendUrl}${this.apiPath}/nodes`, { params: params } );
  }

  deleteDevice(id: string): Observable<any> {
    const params = new HttpParams().set('id', id);
    return this.http.delete(`${this.backendUrl}${this.apiPath}/devices`, { params: params } );
  }

  deleteChannel(id: string): Observable<any> {
    const params = new HttpParams().set('id', id);
    return this.http.delete(`${this.backendUrl}${this.apiPath}/channels`, { params: params } );
  }


}
