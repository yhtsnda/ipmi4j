/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.ipmi.protocol.packet.ipmi.payload;

import com.google.common.base.Charsets;
import com.google.common.primitives.Chars;
import com.google.common.primitives.UnsignedBytes;
import java.nio.ByteBuffer;
import javax.annotation.Nonnull;
import org.anarres.ipmi.protocol.packet.common.Bits;
import org.anarres.ipmi.protocol.packet.common.Code;

/**
 * [IPMI2] Section 13.20 page 150.
 *
 * @author shevek
 */
public class IpmiRAKPMessage1 extends TempIpmiWireable {

    public static enum RequestedMaximumPrivilegeLevel implements Bits.Wrapper {

        CALLBACK(1),
        USER(2),
        OPERATOR(3),
        ADMINISTRATOR(4),
        OEM(5);
        private final Bits bits;

        private RequestedMaximumPrivilegeLevel(int code) {
            this.bits = new Bits(0, UnsignedBytes.checkedCast(code), 0x0F);
        }

        @Override
        public Bits getBits() {
            return bits;
        }
    }

    public static enum PrivilegeLookupMode implements Bits.Wrapper {

        USERNAME_PRIVILEGE(new Bits(0, 0, 1 << 4)),
        NAME_ONLY(new Bits(0, 1 << 4, 1 << 4));
        private final Bits bits;

        private PrivilegeLookupMode(@Nonnull Bits bits) {
            this.bits = bits;
        }

        @Override
        public Bits getBits() {
            return bits;
        }
    }
    private byte messageTag;
    private int systemSessionId;
    private byte[] consoleRandom;   // length = 16
    private RequestedMaximumPrivilegeLevel requestedMaximumPrivilegeLevel;
    private PrivilegeLookupMode privilegeLookupMode;
    private String username;

    @Override
    protected void toWireData(ByteBuffer buffer) {
        buffer.put(messageTag);
        buffer.put(new byte[3]);    // reserved
        buffer.putInt(systemSessionId);
        buffer.put(consoleRandom);
        buffer.put(Bits.toByte(requestedMaximumPrivilegeLevel, privilegeLookupMode));
        buffer.putChar((char) 0); // reserved
        byte[] usernameBytes = username == null ? new byte[0] : username.getBytes(Charsets.ISO_8859_1);
        buffer.put(UnsignedBytes.checkedCast(usernameBytes.length));    // Max is 0x10.
        buffer.put(usernameBytes);
    }
}
