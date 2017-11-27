package ballerina.azure.iotdevice;

public struct ConnectionProperties {
	string name;
}

@Description { value:"Azure IoT client connector."}
@Param { value:"host: Host addresses of Azure IoT Hub" }
@Param { value:"deviceId: Device ID" }
@Param { value:"sharedAccessKey: Shared access key" }
@Param { value:"options: Optional properties for Azure IoT connection" }
public connector ClientConnector (string host, string deviceId, string sharedAccessKey, ConnectionProperties options) {

    map sharedMap = {};

    @Description {value:"The send action implementation which sends a message to Azure IoT Hub."}
    native action send (json document, function() callbackFunction);

	@Description {value:"The close action implementation which closes the Azure IoT connection."}
    native action close ();

}
