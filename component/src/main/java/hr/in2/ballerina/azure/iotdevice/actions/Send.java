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
package hr.in2.ballerina.azure.iotdevice.actions;

import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;

import hr.in2.ballerina.azure.iotdevice.AzureIoTDataSource;
import hr.in2.ballerina.azure.iotdevice.Constants;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.WorkerContext;
import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BFunctionPointer;
import org.ballerinalang.model.values.BJSON;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.util.codegen.ProgramFile;
import org.ballerinalang.util.program.BLangFunctions;

/**
 * {@code Send} action to send a number of event messages to an Azure IoT Hub.
 *
 * @since 1.0.0
 */
@BallerinaAction(
            packageName = "ballerina.azure.iotdevice",
            actionName = "send",
            connectorName = Constants.CONNECTOR_NAME,
            args = {
                @Argument(name = "c", type = TypeKind.CONNECTOR),
                @Argument(name = "document", type = TypeKind.JSON)
            },
            returnType = { @ReturnType(type = TypeKind.JSON) }
         )
public class Send extends AbstractIoTAction {

    @Override
    public ConnectorFuture execute(Context context) {
        BConnector bConnector = (BConnector) getRefArgument(context, 0);
        BJSON document = (BJSON) getRefArgument(context, 1);
        BFunctionPointer pointer = (BFunctionPointer) getRefArgument(context, 2);

        AzureIoTDataSource dataSource = getDataSource(bConnector);

        Message deviceMessage = new Message(document.getMessageAsString());
        deviceMessage.setMessageId(java.util.UUID.randomUUID().toString());

        dataSource.getDeviceClient().sendEventAsync(deviceMessage, new IotHubEventCallback() {
            @Override
            public void execute(IotHubStatusCode responseStatus, Object callbackContext) {
                ProgramFile programFile = context.getProgramFile();
                Context newContext = new WorkerContext(programFile, context);

                BValue[] results = BLangFunctions.invokeFunction(programFile, pointer.value().getFunctionInfo(), null,
                        newContext);
                // TODO: handle invocation results
            }
        }, deviceMessage);

        return getConnectorFuture();
    }

}
