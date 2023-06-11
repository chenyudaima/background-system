package com.chenyudaima.util.hk;

import com.chenyudaima.config.HkAppConfig;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * 海康摄像头工具
 * @author ZengYong
 * * @date 2022/6/15
 */
public class HkApp {
    private static Logger log = LoggerFactory.getLogger(HkApp.class);


    /**
     * SDK本地接口
     */
    private static HCNetSDK hCNetSDK;

    /**
     * 用户句柄
     */
    private static long userId = -1L;

    /**
     * 设备信息
     */
    private static HCNetSDK.NET_DVR_DEVICEINFO_V30 NET_DVR_DEVICEINFO_V30;


    /**
     * 加载SDK动态库
     */
    public static synchronized void loadLibrary() {
        if (null == hCNetSDK) {
            hCNetSDK = (HCNetSDK) Native.loadLibrary(HkAppConfig.DLL_PATH, HCNetSDK.class);
            log.info("加载SDK动态库成功!");
        }
    }

    /**
     * SDK初始化
     */
    public static boolean init() {
        if (hCNetSDK.NET_DVR_Init()) {
            log.info("SDK初始化成功!");
            return true;
        }

        log.error("SDK初始化失败!");
        return false;
    }

    /**
     * 登录
     */
    public static boolean login() {
        //注册
        NET_DVR_DEVICEINFO_V30 = new HCNetSDK.NET_DVR_DEVICEINFO_V30();

        //登录
        userId = hCNetSDK.NET_DVR_Login_V30(HkAppConfig.IP, HkAppConfig.PORT, HkAppConfig.USERNAME, HkAppConfig.PASSWORD, NET_DVR_DEVICEINFO_V30);

        if(hCNetSDK.NET_DVR_SetLogToFile(3, HkAppConfig.LOG_PATH, false)) {
            log.info("SDK日志路径配置成功!");
        }

        if (userId == -1) {
            log.error("登录失败!");
            return false;
        }

        log.info("登录成功!");
        return true;
    }

    /**
     * 登出
     */
    public void logout() {
        if(hCNetSDK.NET_DVR_Logout_V30((int) userId)) {
            userId = -1;
        }
    }

    /**
     * 运行
     */
    public static void start() {
        if (userId != -1) {
            return;
        }

        loadLibrary();
        if (init()) {
            login();
        }
    }

    /**
     * 实时温度检测
     * @param dwChan 通道号
     * @param byRuleID 规则ID，0代表获取全部规则，具体规则ID从1开始
     * @param byMode 长连接模式：0-保留；1-定时模式；2-温差模式
     * @param wInterval 上传间隔（仅温差模式支持），取值范围：1-3600 秒，填0则默认3600S上传一次
     * @param fRemoteConfigCallBack 实时温度回调方法
     */
    public static void realTimeTemperatureDetection(int dwChan, int byRuleID, int byMode, int wInterval, HCNetSDK.FRemoteConfigCallBack fRemoteConfigCallBack) {
        start();
        HCNetSDK.NET_DVR_REALTIME_THERMOMETRY_COND NET_DVR_REALTIME_THERMOMETRY_COND = new HCNetSDK.NET_DVR_REALTIME_THERMOMETRY_COND();

        NET_DVR_REALTIME_THERMOMETRY_COND.dwSize = NET_DVR_REALTIME_THERMOMETRY_COND.size();
        NET_DVR_REALTIME_THERMOMETRY_COND.dwChan = dwChan;
        NET_DVR_REALTIME_THERMOMETRY_COND.byRuleID = (byte) byRuleID;
        NET_DVR_REALTIME_THERMOMETRY_COND.byMode = (byte) byMode;
        NET_DVR_REALTIME_THERMOMETRY_COND.wInterval = (short) wInterval;

        NET_DVR_REALTIME_THERMOMETRY_COND.write();

        long lHandle = hCNetSDK.NET_DVR_StartRemoteConfig((int) userId, HCNetSDK.NET_DVR_GET_REALTIME_THERMOMETRY, NET_DVR_REALTIME_THERMOMETRY_COND.getPointer(), NET_DVR_REALTIME_THERMOMETRY_COND.dwSize, fRemoteConfigCallBack, null);

        if (lHandle < 0) {
            log.error("建立实时测温规则获取长连接失败，错误号:{}", hCNetSDK.NET_DVR_GetLastError());
        }else {
            log.info("建立实时测温规则获取长连接成功!");
        }

    }


    /**
     * 控制摄像头到指定位置
     * @param x
     * @param y
     * @return 控制是否成功
     */
    public static boolean setPosition(double x, double y) {
        start();
        HCNetSDK.NET_DVR_PTZPOS NET_DVR_PTZPOS = new HCNetSDK.NET_DVR_PTZPOS();

        NET_DVR_PTZPOS.wAction = (short) 1;

        NET_DVR_PTZPOS.wTiltPos = (short) new BigInteger(String.valueOf((int) (y * 10)), 16).intValue();
        NET_DVR_PTZPOS.wPanPos = (short) new BigInteger(String.valueOf((int) (x * 10)), 16).intValue();
        NET_DVR_PTZPOS.wZoomPos = (short) 16.0;
        NET_DVR_PTZPOS.write();

        Pointer lpIpParaConfig = NET_DVR_PTZPOS.getPointer();

        // 获取相关参数配置
        boolean result = hCNetSDK.NET_DVR_SetDVRConfig(
                (int) userId,
                HCNetSDK.NET_DVR_SET_PTZPOS,
                2,
                lpIpParaConfig,
                NET_DVR_PTZPOS.size());

        NET_DVR_PTZPOS.read();

        return result;
    }

    /**
     * 获取摄像头当前位置
     * @return x和 y
     */
    public static Map<String, Double> getPosition() {
        start();

        //读取PTZ
        HCNetSDK.NET_DVR_PTZPOS netDvrPtzpos = new HCNetSDK.NET_DVR_PTZPOS();

        netDvrPtzpos.write();
        Pointer lpIpParaConfig = netDvrPtzpos.getPointer();
        // 获取相关参数配置
        hCNetSDK.NET_DVR_GetDVRConfig((int) userId, HCNetSDK.NET_DVR_GET_PTZPOS, 2,
                lpIpParaConfig, netDvrPtzpos.size(), new IntByReference());
        netDvrPtzpos.read();

        HashMap<String, Double> map = new HashMap<>();
        map.put("x", BigDecimal.valueOf(Integer.parseInt(Integer.toHexString(netDvrPtzpos.wPanPos)) / 10.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
        map.put("y", BigDecimal.valueOf(Integer.parseInt(Integer.toHexString(netDvrPtzpos.wTiltPos)) / 10.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return map;
    }


    /**
     * 将当前位置设为预设点
     * @param dwPresetIndex 预置点序号（存在则覆盖）
     * @return 是否设置成功
     */
    public static boolean presetPointOperation(int dwPresetIndex) {
        start();
        return hCNetSDK.NET_DVR_PTZPreset_Other((int) userId, 2, 8, dwPresetIndex);
    }

    /**
     * 预置点操作
     * @param dwPTZPresetCmd 8:设置预置点  9:清除预置点  39:摄像头转到预置点
     * @param dwPresetIndex 预置点序号（存在则覆盖）
     * @return 是否设置成功
     */
    public static boolean presetPointOperation(int dwPTZPresetCmd, int dwPresetIndex) {
        start();
        return hCNetSDK.NET_DVR_PTZPreset_Other((int) userId, 2, dwPTZPresetCmd, dwPresetIndex);
    }

    /**
     * 云台控制操作 21:上仰 22:下俯 23:左转 24:右转 ... 具体看文档
     * dwPTZPresetCmd
     * dwStop 0 开始 1停止
     * dwSpeed 速度 1~7
     */
    public static boolean control(int dwPTZControlCmd, int dwStop, int dwSpeed) {
        start();

        return hCNetSDK.NET_DVR_PTZControlWithSpeed_Other(
                (int) userId,
                2,
                dwPTZControlCmd,
                dwStop,
                dwSpeed);
    }

    /**
     * 云台巡航操作
     * 先添加预设点加入某个巡航路径，在从这个巡航路径开始巡航
     * @param dwPTZCruiseCmd 巡航命令 30:将预设点加入巡航 31:设置巡航点停顿时间 32:设置巡航速度
     *                               33:将预置点从巡航序列中删除 37:开始巡航 38:停止巡航
     * @param byCruiseRoute  巡航路径，最多支持 32 条路径（序号从 1 开始）
     * @param byCruisePoint  巡航点，最多支持 32 个点（序号从 1 开始）
     * @param wInput         不同巡航命令时的值不同，预置点(最大 255)、时间(最大 255)、速度(最大 40)
     * @return 是否设置成功
     */
    public static boolean cruise(int dwPTZCruiseCmd, int byCruiseRoute, int byCruisePoint, int wInput) {
        start();
        return hCNetSDK.NET_DVR_PTZCruise_Other((int) userId, 2, dwPTZCruiseCmd, (byte) byCruiseRoute, (byte) byCruisePoint, (short) wInput);
    }


    /**
     * 抓拍图片(无需预览)
     * @param fileNamePathIChannel1 可见光图片路径 D:/image/1.jpg
     * @param fileNamePathIChannel2 热成像图片路径 D:/image/2.jpg
     * @return 是否抓拍成功
     */
    public static boolean snapPicture(String fileNamePathIChannel1, String fileNamePathIChannel2) {
        start();

        //图像参数
        HCNetSDK.NET_DVR_JPEGPARA strJpeg = new HCNetSDK.NET_DVR_JPEGPARA();
        strJpeg.wPicQuality = 1;
        strJpeg.wPicSize = 2;

        //通道一 可见光
        //尝试用NET_DVR_CaptureJPEGPicture_NEW方法，但是不是报43就是JDK崩溃....
        boolean b1 = hCNetSDK.NET_DVR_CaptureJPEGPicture((int) userId, 1, strJpeg, fileNamePathIChannel1.getBytes());

        //通道二 热成像
        boolean b2 = hCNetSDK.NET_DVR_CaptureJPEGPicture((int) userId, 2, strJpeg, fileNamePathIChannel2.getBytes());

        return b1 || b2;
    }


    /**
     * H264流回调
     * @param fRealDataCallBackV30
     * @return
     */
    public static boolean realPlay(HCNetSDK.FRealDataCallBack_V30 fRealDataCallBackV30) {
        start();

        HCNetSDK.NET_DVR_PREVIEWINFO netDvrClientinfo = new HCNetSDK.NET_DVR_PREVIEWINFO();

        netDvrClientinfo.lChannel = 1;
        netDvrClientinfo.dwStreamType = 0;
        netDvrClientinfo.dwLinkMode = 0;
        netDvrClientinfo.hPlayWnd = null;
        netDvrClientinfo.bPassbackRecord = 0;
        netDvrClientinfo.bBlocked = 0;
        netDvrClientinfo.byPreviewMode = 0;
        netDvrClientinfo.byProtoType = 0;
        netDvrClientinfo.dwDisplayBufNum = 15;

        netDvrClientinfo.write();

        int realPlay = hCNetSDK.NET_DVR_RealPlay_V40((int)userId, netDvrClientinfo, (lRealHandle, dwDataType, pBuffer, dwBufSize, pUser) -> {
            switch (dwDataType) {
                // 系统头
                case HCNetSDK.NET_DVR_SYSHEAD:

                    // 码流数据
                case HCNetSDK.NET_DVR_STREAMDATA:
                    if(dwBufSize <= 0) {
                        break;
                    }

                    try {
                        writeESH264(pBuffer.getPointer().getByteArray(0, dwBufSize));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        },null);

        if(realPlay < 0) {
            return false;
        }

        return true;
    }

    static byte[] allEsBytes = null;

    /**
     * 提取H264的裸流写入文件
     *
     * @param outputData
     * @throws IOException
     */
    public static void writeESH264(final byte[] outputData) throws IOException {
        if (outputData.length <= 0) {
            return;
        }

        if ((outputData[0] & 0xff) == 0x00//
                && (outputData[1] & 0xff) == 0x00//
                && (outputData[2] & 0xff) == 0x01//
                && (outputData[3] & 0xff) == 0xBA) {// RTP包开头
            try {
                // 一个完整的帧解析完成后将解析的数据放入BlockingQueue,websocket获取后发生给前端
                if (allEsBytes != null && allEsBytes.length > 0) {
                    //推送H264的字节给前端进行播放
                    //WebSocketServer.sendByteBuffer(ByteBuffer.wrap(allEsBytes));
                }
                allEsBytes = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 是00 00 01 eo开头的就是视频的pes包
        if ((outputData[0] & 0xff) == 0x00//
                && (outputData[1] & 0xff) == 0x00//
                && (outputData[2] & 0xff) == 0x01//
                && (outputData[3] & 0xff) == 0xE0) {//
            // 去掉包头后的起始位置
            int from = 9 + outputData[8] & 0xff;
            int len = outputData.length - 9 - (outputData[8] & 0xff);
            // 获取es裸流
            byte[] esBytes = new byte[len];
            System.arraycopy(outputData, from, esBytes, 0, len);

            if (allEsBytes == null) {
                allEsBytes = esBytes;
            } else {
                byte[] newEsBytes = new byte[allEsBytes.length + esBytes.length];
                System.arraycopy(allEsBytes, 0, newEsBytes, 0, allEsBytes.length);
                System.arraycopy(esBytes, 0, newEsBytes, allEsBytes.length, esBytes.length);
                allEsBytes = newEsBytes;
            }
        }
    }

    /**
     * 获取设备参数
     * @param dwPTZCruiseCmd 1100:设备扩展参数 118:设备时间参数 ... 具体看文档
     */
    public static HCNetSDK.NET_DVR_PRESET_NAME[] getEquipmentInfo(int dwPTZCruiseCmd) {
        start();
        HCNetSDK.NET_DVR_PRESET_NAMES preset_name = new HCNetSDK.NET_DVR_PRESET_NAMES();
        preset_name.dwSize = preset_name.size();

        preset_name.write();
        IntByReference lpBytesReturned = new IntByReference(0);

        boolean result = hCNetSDK.NET_DVR_GetDVRConfig((int) userId, dwPTZCruiseCmd, 2, preset_name.getPointer(), preset_name.size(), lpBytesReturned);

        if(!result) {
            log.error("错误号:" + hCNetSDK.NET_DVR_GetLastError());
        }

        return preset_name.names;
    }

}
