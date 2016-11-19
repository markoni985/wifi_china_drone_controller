package org.cyanotic.cx10.net;

/**
 * Created by orfeo.ciano on 18/11/2016.
 */
abstract class Message {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    byte[] token = new byte[16];
    byte[] line1 = new byte[16];
    byte[] line2 = new byte[16];
    byte[] line3 = new byte[16];
    byte[] line4 = new byte[16];
    byte[] line5 = new byte[16];
    byte[] header = new byte[10];

    public Message() {
        header = new byte[]{
                0x49, 0x54, 0x64, 0x00, 0x00, 0x00, getTypeFlag(), 0x00, 0x00, 0x00
        };
    }

    private static String bytesToHex(byte[] bytes) {
        String output = "";
        for (byte aByte : bytes) {
            int v = aByte & 0xFF;
            output += hexArray[v >>> 4];
            output += hexArray[v & 0x0F];
            output += " ";
        }
        return output;
    }

    abstract byte getTypeFlag();

    byte[] getCommand() {
        byte[] command = new byte[106];

        System.arraycopy(header, 0, command, 0, 10);
        System.arraycopy(token, 0, command, 10, 16);
        System.arraycopy(line1, 0, command, 26, 16);
        System.arraycopy(line2, 0, command, 42, 16);
        System.arraycopy(line3, 0, command, 58, 16);
        System.arraycopy(line4, 0, command, 74, 16);
        System.arraycopy(line5, 0, command, 90, 16);

        return command;
    }

    public String toString() {
        return bytesToHex(header) + "\n"
                + bytesToHex(token) + "\n"
                + bytesToHex(line1) + "\n"
                + bytesToHex(line2) + "\n"
                + bytesToHex(line3) + "\n"
                + bytesToHex(line4) + "\n"
                + bytesToHex(line5) + "\n";
    }

    protected static byte[] asUnsigned(int... values){
        byte[] bytes = new byte[values.length];
        for(int i = 0; i < values.length; i++){
            int value = values[i];
            if(value > Byte.MAX_VALUE){
                bytes[i] = (byte) value;
            } else {
                bytes[i] = (byte) (value & 0xff);
            }
        }
        return bytes;
    }

}