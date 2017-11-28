# Ballerina Azure IoT Device Connector

Ballerina Azure IoT Device Connector is used to connect Ballerina with Azure IoT Hub. With the Azure IoT Device Connector Ballerina can act as an IoT Device.

Steps to configure,
1. Extract `ballerina-azure-iotdevice-connector-<version>.zip` and copy containing jars in to `<BRE_HOME>/bre/lib/`

Ballerina as an IoT Device

```ballerina
import ballerina.azure.iotdevice;
import ballerina.log;

function main(string[] args) {
  endpoint<iotdevice:ClientConnector> device {
    create iotdevice:ClientConnector("<HostName>","<DeviceId>","<SharedAccessKey>", {});
    }

  iotdevice:Message msg = {
    messageId: "261fc077-fc1b-4cec-a290-fca55ee6ab3f",
    payload: "message payload",
    expiryTime: 2000,
    properties: {}
  };

  device.send(msg, callback);
  sleep(2000);
  device.close();
}

function callback(iotdevice:Message msg, string status) {
  log:printInfo(">> message " + msg.messageId + " received with status " + status);
}
```

For more IoT Device Connector configurations please refer to the samples directory.
