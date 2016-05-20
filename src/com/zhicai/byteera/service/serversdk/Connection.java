package com.zhicai.byteera.service.serversdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
    public String m_ip;
    public int m_port;
    private Socket m_sock;
    public boolean active = false;

    public boolean connect(String ip, int port) {
        try {
            m_ip = ip;
            m_port = port;
            InetAddress addr = InetAddress.getByName(m_ip);
            m_sock = new Socket(addr, m_port);
            active = true;
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean reconnect() {
        try {
            m_sock.close();
        } catch (Exception e) {
        }

        try {
            InetAddress addr = InetAddress.getByName(m_ip);
            Socket s = new Socket(addr, m_port);
            m_sock = s;
            active = true;
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public void send(byte[] data) {
        try {
            OutputStream ops = this.m_sock.getOutputStream();
            ops.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int recv(byte[] buf, int len) {
        try {
            InputStream ips = this.m_sock.getInputStream();
            return ips.read(buf, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean close() {
        try {
            m_sock.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
