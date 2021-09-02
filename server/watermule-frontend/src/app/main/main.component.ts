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
    this.channel.deviceId = this.devices[0].id;
    this.showChannelDlg = true;
  }

  onNodeNew(): void {
    this.node = new ThingNode();
    this.showThingNodeDlg = true;
  }

  onDeviceNew(): void {
    this.device = new Device();
    this.device.nodeId = this.nodes[0].id;
    this.showDeviceDlg = true;
  }

  onSaveNode() {
    if(this.node.id.length>0 && this.node.name.length) {
      this.service.saveNode(this.node).subscribe(() => {
        this.service.fetchNodes().subscribe(n => {
          this.nodes = n;
        });
        this.showThingNodeDlg = false;
      });
    } else {
      this.messageService.add({severity:'error', summary: 'Fehler', detail: 'Das Formular muss komplett ausgefüllt sein.'});
    }
  }

  onSaveDevice() {
    if(this.device.id.length > 0 && this.device.name.length > 0 && this.device.nodeId.length > 0) {
      this.device.id = this.device.nodeId + "/" + this.device.id;
      this.service.saveDevice(this.device).subscribe(() => {
        this.service.fetchDevices().subscribe(n => {
          this.devices = n;
        });
        this.showDeviceDlg = false;
      });
    } else {
      this.messageService.add({severity:'error', summary: 'Fehler', detail: 'Das Formular muss komplett ausgefüllt sein.'});
    }
  }

  onSaveChannel() {
    if(this.channel.id.length > 0 && this.channel.name.length > 0 && this.channel.deviceId.length > 0) {
      this.channel.id = this.channel.deviceId + "/" + this.channel.id;
      this.service.saveChannel(this.channel).subscribe(() => {
        this.service.fetchChannels().subscribe(n => {
          this.channels = n;
        });
        this.showChannelDlg = false;
      });
    } else {
      this.messageService.add({severity:'error', summary: 'Fehler', detail: 'Das Formular muss komplett ausgefüllt sein.'});
    }
  }

  onDeleteChannel(channel: Channel): void {
    this.service.deleteChannel(channel.id).subscribe(x=>{
      this.service.fetchChannels().subscribe(n => {
        this.channels = n;
      });
    });
  }

  onDeleteDevice(device: Device): void {
    this.service.deleteDevice(device.id).subscribe(x=>{
      this.service.fetchDevices().subscribe(n => {
        this.devices = n;
      });
    });
  }

  onDeleteNode(node: ThingNode) {
    this.service.deleteNode(node.id).subscribe(x=>{
      this.service.fetchNodes().subscribe(n => {
        this.nodes = n;
      });
    });
  }
}
