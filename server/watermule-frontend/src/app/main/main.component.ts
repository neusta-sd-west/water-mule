import { Component, OnInit } from '@angular/core';
import {Device} from "../entity/device";
import {Channel} from "../entity/channel";
import {ThingNode} from "../entity/thing-node";
import {BackendService} from "../service/backend.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  nodes: ThingNode[] = [];
  devices: Device[] = [];
  channels: Channel[] = [];

  constructor(private service: BackendService) {

    this.service.fetchNodes().subscribe(n => {
      this.nodes = n;
    });

    this.service.fetchDevices().subscribe(n => {
      this.devices = n;
    });

    this.service.fetchChannels().subscribe(n => {
      this.channels = n;
    });

  }

  ngOnInit(): void {
  }

}
