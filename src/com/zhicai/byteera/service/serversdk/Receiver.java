package com.zhicai.byteera.service.serversdk;

import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashMap;

import static com.zhicai.byteera.service.serversdk.TiangongClient.bytesToInt;

public class Receiver extends IThread {
    private Connection m_conn;
    public HashMap<String, BaseHandlerClass> m_callBackMapper;
    public BaseHandlerClass m_pushMsgHandler;
    private static Receiver m_instance;

    private Receiver() {
        m_callBackMapper = new HashMap<String, BaseHandlerClass>();
    }

    public static Receiver instance() {
        if (m_instance==null) {
            m_instance = new Receiver();
        }
        return m_instance;
    }

    public void setConnection(Connection conn) {
        m_conn = conn;
    }

    public Connection getConnection() {
        return m_conn;
    }

    public synchronized void setCallBack(String msg_id, BaseHandlerClass callBackObj) {
        m_callBackMapper.put(msg_id, callBackObj);
//        System.out.println("callBack mapper size " +  m_callBackMapper.size());
    }

    public synchronized void delCallBack(String msg_id) {
        m_callBackMapper.remove(msg_id);
    }

    @Override
    int _prepare() {
//        System.out.println("in Receiver::_prepare");
        return 0;
    }

    byte[] recvData(int len) {
        byte[] buf = new byte[len];
        int receivedLen = 0;
        while (receivedLen < len) {
            byte[] _buf = new byte[len];
            int _len = m_conn.recv(_buf, len-receivedLen);
            if (_len < 0)
                return new byte[0];
            System.arraycopy(_buf, 0, buf, receivedLen, _len);
            receivedLen += _len;
        }
        return buf;
    }

    // 一直等到数据接受完全
    int recvHead() {
        byte[] buf = recvData(4);
        if (buf.length == 0)
            return 0;
        return bytesToInt(buf, 0);
    }

    @Override
    int _kernel() {
        if (!m_conn.active) {
//            System.out.println("Receiver::_kernel Connection not active");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!m_conn.reconnect())
                return 0;
        }

        int bodyLength = recvHead();
//        System.out.println("recv head. " + String.valueOf(bodyLength));
        if (bodyLength == 0) {
            // 对方关闭连接
            m_conn.close();
            m_conn.active = false;
            return 0;
        }

        byte[] buf = recvData(bodyLength);

        TransmitProtos.ServerResponse response = null;
        try {
            response = TransmitProtos.ServerResponse.parseFrom(buf);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return 0;
        }
        String msg_id = response.getMsgId();
        String payload = response.getPayload();

        byte[] msg;
        try {
            msg = Mybase64.decodeLines(payload);
        } catch (IllegalArgumentException e) {
            System.out.println("Payload length" + String.valueOf(payload.length()));
            e.printStackTrace();
            return 0;
        }

        if (response.getMsgType() == TransmitProtos.ServerResponse.ServerMsgType.Push) {
//            System.out.println("Server push message: " + String.valueOf(payload.length()));
            if (m_pushMsgHandler == null) {
                System.out.println("Handler for server push not found");
                return 0;
            }
            m_pushMsgHandler.handle(msg);

        } else if (response.getMsgType() == TransmitProtos.ServerResponse.ServerMsgType.NormalResponse) {
            try {
                BaseHandlerClass callObj = m_callBackMapper.get(msg_id);
                callObj.handle(msg);
                delCallBack(msg_id);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    @Override
    void _finish() {
//        System.out.println("in Receiver::_finish");
    }
}
