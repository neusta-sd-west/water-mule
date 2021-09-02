import { Component, OnInit } from '@angular/core';
import {Device} from "../entity/device";
import {Channel} from "../entity/channel";
import {ThingNode} from "../entity/thing-node";
import {BackendService} from "../service/backend.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  nodes: ThingNode[] = [];
  devices: Device[] = [];
  channels: Channel[] = [];

  node: ThingNode = new ThingNode();
  device: Device = new Device();
  channel: Channel = new Channel();

  showThingNodeDlg = false;
  showDeviceDlg = false;
  showChannelDlg = false;

  constructor(private service: BackendService,
              private messageService: MessageService) {

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

  onChannelNew(): void {
    this.channel = new Channel();
    this.showChannelDlg = true;
  }

  onNodeNew(): void {
    this.node = new ThingNode();
    this.showThingNodeDlg = true;
  }

  onDeviceNew(): void {
    this.device = new Device();
    this.showDeviceDlg = true;
  }

  onSaveNode() {
    if(this.node.id.length>0 && this.node.name.length && this.node.description.length>0) {
      this.service.saveNode(this.node).subscribe(() => this.showThingNodeDlg = false);
    } else {
      this.messageService.add({severity:'error', summary: 'Fehler', detail: 'Das Formular muss komplett ausgefüllt sein.'});
    }
  }
}
