package com.chenyudaima.util;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 串口工具
 * @author 沉鱼代码
 * @date 2023/5/30
 */
public class CommUtil implements SerialPortEventListener {
    private static final Logger log = LoggerFactory.getLogger(CommUtil.class);

    /**
     * 串口端口
     */
    private static final String PORT_NAME = "COM1";

    /**
     * 比特率
     */
    private static final int BIT_RATE = 9600;

    /**
     * 数据位
     */
    public static final int DATA_BITS = SerialPort.DATABITS_8;

    /**
     * 停止位
     */
    public static final int STOP_BIT = SerialPort.STOPBITS_1;

    /**
     * 校验位
     */
    public static final int PARITY_BIT = SerialPort.PARITY_NONE;


    private static SerialPort serialPort;
    private static InputStream in;
    private static OutputStream out;
    private static volatile CommUtil commUtil;

    private CommUtil() {
    }

    public static CommUtil getInstance() {
        if (commUtil == null) {
            synchronized (CommUtil.class) {
                if(commUtil == null) {
                    commUtil = new CommUtil();
                    commUtil.init();
                }
            }
        }
        return commUtil;
    }

    private void init() {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(PORT_NAME);
            if (portIdentifier.isCurrentlyOwned()) {
                log.error("服务器没有{}端口", PORT_NAME);
            } else if (portIdentifier.getPortType() == 1) {

                //打开串口
                serialPort = (SerialPort) portIdentifier.open(PORT_NAME, 2000);

                //设置串口参数
                serialPort.setSerialPortParams(BIT_RATE, DATA_BITS, STOP_BIT, PARITY_BIT);

                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();

                //添加串口事件监听
                serialPort.addEventListener(this);

                // 设置当有数据到达时唤醒监听接收线程
                serialPort.notifyOnDataAvailable(true);
            } else {
                log.error("Error: Only serial ports are handled by this example");
            }
            log.info("CommUtil初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        try {
            out.write(hexStrToByteArray(message));
            out.flush();
            Thread.sleep(1000);
        } catch (Exception e) {
            log.error("CommUtil发送数据失败,{}",e.toString());
        }
    }

    /**
     * 监听回调
     * @param event
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            String result = receive();
            log.info("CommUtil接收到的数据:" + result);
        }
    }

    public String receive() {
        try {
            int lenth = in.available();
            byte[] bytes = null;
            while (lenth != 0) {
                bytes = new byte[lenth];
                in.read(bytes);
                lenth = in.available();
            }

            if(bytes == null) {
                return null;
            }

            return new String(bytes,0, bytes.length).trim();
        }catch (Exception e) {
            return null;
        }
        //byte[] buffer = new byte[128];
        //int data;
        //String result = null;
        //try {
        //    int len = 0;
        //    while ((data = in.read()) > -1) {
        //        buffer[len++] = (byte) data;
        //    }
        //    byte[] copyValue = new byte[len];
        //    System.arraycopy(buffer, 0, copyValue, 0, len);
        //    result = ByteArrayToString(copyValue);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //return result;
    }

    public void close() {
        try {
            in.close();
            out.close();
            serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();
            serialPort.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 16进制转byte数组
     */
    public byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    /**
     * 字节转字符串
     */
    public String ByteArrayToString(byte[] by) {
        String str = "";
        for (int i = 0; i < by.length; i++) {
            String hex = Integer.toHexString(by[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            str += hex.toUpperCase();
        }
        return str;
    }

}
