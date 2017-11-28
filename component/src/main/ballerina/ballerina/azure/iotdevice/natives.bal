package ballerina.azure.iotdevice;

public struct ConnectionProperties {
	string name;
}

public struct Message {
    string messageId;
    string correlationId;
    string payload;
    int expiryTime;
    map properties;
}

@Description { value:"Azure IoT Device Client Connector."}
@Param { value:"host: Host addresses of Azure IoT Hub" }
@Param { value:"deviceId: Device ID" }
@Param { value:"sharedAccessKey: Shared access key" }
@Param { value:"options: Optional properties for Azure IoT Hub connection" }
public connector ClientConnector (string host, string deviceId, string sharedAccessKey, ConnectionProperties options) {

    map sharedMap = {};

    @Description {value:"The send action implementation which sends a message to Azure IoT Hub."}
    native action send (Message message, function(Message, string) callback);

    @Description {value:"The close action implementation which closes the Azure IoT connection."}
    native action close ();

}
