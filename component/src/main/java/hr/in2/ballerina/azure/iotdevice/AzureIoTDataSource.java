/*
 * Copyright (c) 2017, IN2 Ltd. (http://www.in2.hr) All Rights Reserved.
 *
 * IN2 Ltd. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package hr.in2.ballerina.azure.iotdevice;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;

import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.model.types.BType;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.nativeimpl.actions.ClientConnectorFuture;
import org.ballerinalang.util.exceptions.BallerinaException;

/**
 * {@code AzureIoTDataSource} util class for Azure IoT connector initialization.
 *
 * @since 1.0.0
 */
public class AzureIoTDataSource implements BValue {

    private DeviceClient client;

    public AzureIoTDataSource() {
    }

    public boolean init(String host, String deviceId, String sharedAccessKey, BStruct options) {
        String connString = String.format("HostName=%s;DeviceId=%s;SharedAccessKey=%s", host, deviceId,
                sharedAccessKey);
        IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;

        // TODO: allow setting device client properties

        try {
            this.client = new DeviceClient(connString, protocol);
            this.client.open();
        } catch (Throwable e) {
            throw new BallerinaException("Cannnot open connection to " + host, e);
        }
        return true;
    }

    protected ConnectorFuture getConnectorFuture() {
        ClientConnectorFuture future = new ClientConnectorFuture();
        future.notifySuccess();
        return future;
    }

    public DeviceClient getDeviceClient() {
        return client;
    }

    @Override
    public String stringValue() {
        return null;
    }

    @Override
    public BType getType() {
        return null;
    }

    @Override
    public BValue copy() {
        return null;
    }

}
