package com.zhicai.byteera.service.serversdk;

import java.util.Date;

public class TiangongClient {
    private String m_ip;
    private int m_port;
    private Connection m_conn;
    private static TiangongClient m_instance;

    public TiangongClient() {
        m_conn = new Connection();
    }

    public static TiangongClient instance() {
        if (m_instance==null)
            m_instance = new TiangongClient();
        return m_instance;
    }

    public boolean init(String ip, int port) {
        m_ip = ip;
        m_port = port;
        m_conn.connect(ip, port);
        Receiver.instance().setConnection(m_conn);
        if (Receiver.instance().start() == 0)
            return true;
        return false;
    }

//    public boolean connect() {
//        if (m_conn.connect(m_ip, m_port)) {
//            return true;
//        }
//        return false;
//    }

//    public boolean reconnect() {
//        return m_conn.reconnect();
//    }

    public boolean close() {
        return m_conn.close();
    }

    public static byte[] intToBytes( int value ) {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }


    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24);
        return value;
    }

    private String gen_id(int n) {
        String id;
        Date now = new Date();
        id = String.valueOf(now.getTime()) + String.valueOf(n) + "a";
        return id;
    }

    /**
     * send message to server.
     * @cluster Which service cluster this message send to.
     * @optype Which operation you want do.
     * @data Custom data. generate by A instance of "protocol buffer" class.
     *            Like:
     *                     User user = User.newBuilder().setUsername("xx").build();
     *                     byte[] data = user.toByteArray();
     *                     send("cluster_play", "optype_football", data);
     * @callBackObj Callback class instance
     * @callBackMethod  Callback method in Callback class
     */
//    public void send(String cluster, String optype, byte[] data, Object callBackObj, String callBackMethod) {
    public void send(String cluster, String optype, byte[] data, BaseHandlerClass callBackObj) {
        String ndata = Mybase64.encodeLines(data);
        String msg_id = gen_id(data.length);
        System.out.println(String.format("optype:%s,msg_id:%s",optype,msg_id));
        TransmitProtos.ClientRequest request = TransmitProtos.ClientRequest.newBuilder()
                .setMsgId(msg_id)
                .setCluster(cluster)
                .setOpType(optype)
                .setPayload(ndata)
                .setInterfaceVersion("1.0")
                .setProtocolVersion("1.0")
                .build();
        byte[] s = request.toByteArray();

        if (!m_conn.active){
            return;
        }

        Receiver.instance().setCallBack(msg_id, callBackObj);

        int bodyLength = s.length;

        try {
            m_conn.send(intToBytes(bodyLength));
            m_conn.send(s);
        } catch (Exception e) {
           // ToastUtil.showNetConnectionError(UIUtils.getApplicationContext());
            e.printStackTrace();
            m_conn.active = false;
        }

    }

    public void setPushMsgHandler(BaseHandlerClass handler) {
        Receiver.instance().m_pushMsgHandler = handler;
    }
}
