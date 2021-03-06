/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.ipmi.protocol.packet.rmcp;

import java.nio.ByteBuffer;
import org.anarres.ipmi.protocol.client.visitor.IpmiClientRmcpMessageHandler;
import org.anarres.ipmi.protocol.packet.common.AbstractWireable;
import org.anarres.ipmi.protocol.client.session.IpmiPacketContext;
import org.anarres.ipmi.protocol.client.visitor.IpmiHandlerContext;

/**
 *
 * @author shevek
 */
public class OEMRmcpMessage extends AbstractWireable implements RmcpData {

    private byte[] data;

    @Override
    public RmcpMessageClass getMessageClass() {
        return RmcpMessageClass.OEM;
    }

    @Override
    public void apply(IpmiClientRmcpMessageHandler handler, IpmiHandlerContext context) {
        handler.handleOemRmcpData(context, this);
    }

    @Override
    public int getWireLength(IpmiPacketContext context) {
        return data.length;
    }

    @Override
    protected void toWireUnchecked(IpmiPacketContext context, ByteBuffer buffer) {
        buffer.put(data);
    }

    @Override
    protected void fromWireUnchecked(IpmiPacketContext context, ByteBuffer buffer) {
        data = readBytes(buffer, buffer.remaining());
    }
}
