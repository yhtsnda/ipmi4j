/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.ipmi.protocol.packet.ipmi.message;

import java.nio.ByteBuffer;
import org.anarres.ipmi.protocol.packet.common.Code;
import org.anarres.ipmi.protocol.packet.ipmi.IpmiChannelNumber;
import org.anarres.ipmi.protocol.packet.ipmi.IpmiChannelPrivilegeLevel;
import org.anarres.ipmi.protocol.packet.ipmi.IpmiCommand;

/**
 * [IPMI2] Section 22.13, table 22-15, page 283.
 *
 * @author shevek
 */
public class GetChannelAuthenticationCapabilitiesRequest extends AbstractIpmiNonSessionMessage {

    public boolean getExtendedData;
    public IpmiChannelNumber channelNumber;
    public IpmiChannelPrivilegeLevel channelPrivilegeLevel;

    @Override
    public IpmiCommand getCommand() {
        return IpmiCommand.GetChannelAuthenticationCapabilities;
    }

    @Override
    public int getWireLength() {
        return 2;
    }

    @Override
    protected void toWireUnchecked(ByteBuffer buffer) {
        byte b = channelNumber.getCode();
        // If we fail with getExtendedData, we might have to try again without.
        if (getExtendedData)
            b |= 1 << 7;
        buffer.put(b);
        buffer.put(channelPrivilegeLevel.getCode());
    }

    @Override
    protected void fromWireUnchecked(ByteBuffer buffer) {
        byte b = buffer.get();
        channelNumber = Code.fromByte(IpmiChannelNumber.class, (byte) (b & 0xF));
        channelPrivilegeLevel = Code.fromByte(IpmiChannelPrivilegeLevel.class, buffer.get());
    }
}