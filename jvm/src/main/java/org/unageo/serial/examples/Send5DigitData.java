package org.unageo.serial.examples;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.List;

public class Send5DigitData {
    public static void pr(Object o) {
        System.out.println(o);
    }

    private static String getPadded(final String str) {
        return switch (str.length()) {
            case 4 -> "\0" + str + "x"; //most likely
            case 5 -> str + "x"; //second most likely
            case 3 -> "\0\0" + str + "x";
            case 0 -> "\0\0\0\0\0x";
            case 1 -> "\0\0\0\0" + str + "x";
            case 2 -> "\0\0\0" + str + "x";
            default -> "-bAd-x";
        };
    }

    public static void main(String[] args) throws Exception {

        List<SerialPort> ports = Arrays.asList(SerialPort.getCommPorts());


        //SerialPort myPort = SerialPort.getCommPort("COM3");
        SerialPort myPort = ports.stream().findFirst().get();
        myPort.setComPortParameters(9600, 8, 1, 0);
        myPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written
        pr(myPort);
        pr(myPort.isOpen());
        pr("Opened : " + myPort.openPort());
        pr(myPort.isOpen());

        for (int i = 0; i < 40000; i++) {
            Thread.sleep(5);
            final String message = getPadded(String.valueOf(i));

            myPort.getOutputStream().write(message.getBytes());
            myPort.getOutputStream().flush();
        }
        myPort.closePort();

    }
}
