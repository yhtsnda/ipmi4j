/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.ipmi.protocol.packet.ipmi.payload;

import java.nio.ByteBuffer;
import org.anarres.ipmi.protocol.packet.asf.AsfRsspSessionStatus;

/**
 * [IPMI2] Section 13.22 page 152.
 *
 * @author shevek
 */
public class IpmiRAKPMessage3 extends TempIpmiWireable {

    private byte messageTag;
    private AsfRsspSessionStatus statusCode;
    private int systemSessionId;
    private byte[] keyExchangeAuthenticationCode;

    @Override
    protected void toWireData(ByteBuffer buffer) {
        buffer.put(messageTag);
        buffer.put(statusCode.getCode());
        buffer.putChar((char) 0);    // reserved
        buffer.putInt(systemSessionId);
        buffer.put(keyExchangeAuthenticationCode);
    }
}
