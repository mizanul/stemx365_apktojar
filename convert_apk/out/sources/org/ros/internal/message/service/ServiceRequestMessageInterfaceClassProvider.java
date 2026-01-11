package org.ros.internal.message.service;

import org.ros.internal.message.MessageInterfaceClassProvider;
import org.ros.internal.message.RawMessage;

public class ServiceRequestMessageInterfaceClassProvider implements MessageInterfaceClassProvider {
    public <T> Class<T> get(String messageType) {
        try {
            return getClass().getClassLoader().loadClass(messageType.replace("/", ".") + "$Request");
        } catch (ClassNotFoundException e) {
            return RawMessage.class;
        }
    }
}
