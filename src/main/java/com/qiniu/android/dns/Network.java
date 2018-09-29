package com.qiniu.android.dns;

import android.os.Build;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by bailong on 15/7/3.
 */
public final class Network {
    private static String previousIp = "";

    //    use udp socket connect, tcp socket would connect when new.
    public static String getIp(android.net.Network net) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            if (net != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                net.bindSocket(socket);
            }
            InetAddress addr = InetAddress.getByName("114.114.114.114");
            socket.connect(addr, 53);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        InetAddress local = socket.getLocalAddress();
        return local.getHostAddress();
    }


    public static synchronized boolean isNetworkChanged(android.net.Network net) {
        String nowIp = getIp(net);
        if (nowIp.equals(previousIp)) {
            return false;
        }
        previousIp = nowIp;
        return true;
    }
}
