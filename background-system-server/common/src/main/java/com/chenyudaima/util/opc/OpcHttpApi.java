package com.chenyudaima.util.opc;

import com.alibaba.fastjson.JSON;
import com.chenyudaima.constant.CharSet;
import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.model.OpcNode;
import com.chenyudaima.util.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KepServerEX RestFul Api 接口 调用
 * 需要开启RestFul Api接口
 * http://127.0.0.1:57412/config
 * http默认端口57412
 * https默认端口57512
 * @author 沉鱼代码
 * @date 2023/2/10
 */
public class OpcHttpApi {

    /**
     * https://ip:port/config/v1/project/channels/
     */
    private final String url;

    /**
     * 请求头
     */
    private final Map<String,String> headerMap = new HashMap<>();

    public OpcHttpApi(String ip) {
        this(ip, 57412, null, null);
    }

    public OpcHttpApi(String ip, int port) {
        this(ip, port, null, null);
    }

    public OpcHttpApi(String ip, int port, String userName, String password) {
        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append("http://");
        urlBuild.append(ip);
        urlBuild.append(":");
        urlBuild.append(port);
        urlBuild.append("/config/v1/project/channels/");
        this.url = urlBuild.toString();

        if (userName == null) {
            userName = "Administrator";
        }
        if(password == null) {
            password = "";
        }

        //添加权限请求头
        headerMap.put(HttpHeader.K_REQUEST_HEADER_AUTHORIZATION, "Basic " + new Base64().encodeToString((userName + ":" + password).getBytes()));

    }

    /**
     * 查询所有通道信息
     * @return 所有通道
     */
    public List<OpcNode> select() throws Exception {
        List<OpcNode> opcNodes = new ArrayList<>();
        for (HashMap hashMap : JSON.parseArray(HttpClientUtil.get(url, null, headerMap), HashMap.class)) {
            OpcNode opcNode = new OpcNode();
            opcNode.setNodeName(hashMap.get("common.ALLTYPES_NAME").toString());
            opcNode.setNodeDescription(hashMap.get("common.ALLTYPES_DESCRIPTION").toString());
            opcNode.setNodePath(hashMap.get("common.ALLTYPES_NAME").toString());
            opcNode.setParameters(hashMap);
            opcNodes.add(opcNode);
        }
        return opcNodes;
    }

    /**
     * 查询所有设备信息
     * @param channel 通道名称
     * @return 所有设备
     */
    public List<OpcNode> select(String channel) throws Exception {
        List<OpcNode> opcNodes = new ArrayList<>();
        for (HashMap hashMap : JSON.parseArray(HttpClientUtil.get(url +encode(channel) + "/devices",null, headerMap), HashMap.class)) {
            OpcNode opcNode = new OpcNode();
            opcNode.setNodeName(hashMap.get("common.ALLTYPES_NAME").toString());
            opcNode.setNodeDescription(hashMap.get("common.ALLTYPES_DESCRIPTION").toString());
            opcNode.setParameters(hashMap);
            opcNode.setNodePath(channel + "." + hashMap.get("common.ALLTYPES_NAME").toString());
            opcNodes.add(opcNode);
        }
        return opcNodes;
    }


    /**
     * 查询所有标记组
     * @param channel 通道名称
     * @param equipment 设备名称
     * @return 所有标记组
     */
    public List<OpcNode> select(String channel, String equipment) throws Exception {
        List<OpcNode> opcNodes = new ArrayList<>();
        for (HashMap hashMap : JSON.parseArray(HttpClientUtil.get(url + encode(channel) + "/devices/" + encode(equipment) + "/tag_groups",null, headerMap), HashMap.class)) {
            OpcNode opcNode = new OpcNode();
            opcNode.setNodeName(hashMap.get("common.ALLTYPES_NAME").toString());
            opcNode.setNodeDescription(hashMap.get("common.ALLTYPES_DESCRIPTION").toString());
            opcNode.setNodePath(channel + "." + equipment + "." + hashMap.get("common.ALLTYPES_NAME").toString());
            opcNodes.add(opcNode);
        }
        return opcNodes;
    }

    /**
     * 查询所有标记
     * @param channel 通道名称
     * @param equipment 设备名称
     * @param group 标记组名称
     * @return 所有标记
     */
    public List<OpcNode> select(String channel, String equipment , String group) throws Exception {
        List<OpcNode> opcNodes = new ArrayList<>();
        for (HashMap hashMap : JSON.parseArray(HttpClientUtil.get(url + encode(channel) + "/devices/" + encode(equipment) + "/tag_groups/" + encode(group) + "/tags",null, headerMap), HashMap.class)) {
            OpcNode opcNode = new OpcNode();
            opcNode.setNodeName(hashMap.get("common.ALLTYPES_NAME").toString());
            opcNode.setNodeDescription(hashMap.get("common.ALLTYPES_DESCRIPTION").toString());
            opcNode.setParameters(hashMap);
            opcNode.setNodePath(channel + "." + equipment + "." + group + "." + hashMap.get("common.ALLTYPES_NAME").toString());
            opcNodes.add(opcNode);
        }
        return opcNodes;
    }

    /**
     * 查询单个标记
     * @param channel 通道名称
     * @param equipment 设备名称
     * @param group 标记组名称
     * @param tab 标记名称
     * @return 单个标记
     */
    public OpcNode select(String channel, String equipment ,String group, String tab) throws Exception {
        String json = HttpClientUtil.get(url + encode(channel) + "/devices/" + encode(equipment) + "/tag_groups/" + encode(group) + "/tags/" + encode(tab),null, headerMap);

        HashMap hashMap = JSON.parseObject(json, HashMap.class);
        OpcNode opcNode = new OpcNode();
        opcNode.setNodeName(hashMap.get("common.ALLTYPES_NAME").toString());
        opcNode.setNodeDescription(hashMap.get("common.ALLTYPES_DESCRIPTION").toString());
        opcNode.setNodePath(channel + "." + equipment + "." + group + "." + tab);
        return opcNode;
    }


    /**
     * 根据设备编号（名称）查询属于哪个通道
     * @param equipmentCode 设备编号
     * @return  通道
     */
    public OpcNode selectChannelByEquipmentCode(String equipmentCode) throws Exception {
        for (OpcNode opcNode : select()) {
            List<OpcNode> opcNodes = select(opcNode.getNodePath());
            for (OpcNode node : opcNodes) {
                if(equipmentCode.equals(node.getNodePath().split("\\.")[1])) {
                    return opcNode;
                }
            }
        }
        return null;
    }

    /**
     * 删除通道
     * @param channel 通道名称
     */
    public void delete(String channel) throws Exception {
        HttpClientUtil.delete(url + encode(channel), headerMap);
    }

    /**
     * 删除设备
     * @param channel 通道名称
     * @param equipment 设备名称
     */
    public void delete(String channel, String equipment) throws Exception {
        HttpClientUtil.delete(url + encode(channel) + "/devices/" + encode(equipment), headerMap);
    }

    /**
     * 删除标记组
     * @param channel 通道名称
     * @param equipment 设备名称
     * @param group 标记组名称
     */
    public void delete(String channel, String equipment,String group) throws Exception {
        HttpClientUtil.delete(url + encode(channel) + "/devices/" + encode(equipment) + "/tag_groups/" + encode(group), headerMap);
    }

    /**
     * 删除标记
     * @param channel 通道名称
     * @param equipment 设备名称
     * @param group 标记组名称
     * @param tab 标记名称
     */
    public void delete(String channel, String equipment,String group, String tab) throws Exception {
        HttpClientUtil.delete(url + encode(channel) + "/devices/" + encode(equipment) + "/tag_groups/" + encode(group) + "/tags/" + tab, headerMap);
    }

    /**
     * 添加设备
     * @param channel 通道名称
     * @param json json数据格式
     * @return
     */
    public String insertEquipment(String channel, String json) throws Exception {
        return HttpClientUtil.postJson(url + encode(channel) + "/devices",json, headerMap);
    }

    /**
     * 添加标记组
     * @param channel 通道名称
     * @param equipment 设备名称
     * @param json json数据格式
     * @return
     */
    public String insertTabGroup(String channel, String equipment, String json) throws Exception {
        return HttpClientUtil.postJson(url + encode(channel) + "/devices/" + encode(equipment) + "/tag_groups", json, headerMap);
    }

    /**
     * 添加标记
     * @param channel 通道名称
     * @param equipment 设备名称
     * @param group 标记组名称
     * @param json json数据格式
     * @return
     */
    public String insertTab(String channel, String equipment, String group, String json) throws Exception {
        return HttpClientUtil.postJson(url + encode(channel) + "/devices/" + encode(equipment) + "/tag_groups/" + group + "/tags", json, headerMap);
    }


    /**
     * 路径字符串转URL字符串
     */
    private static String encode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, CharSet.UTF8).replaceAll("\\+", "%20");
    }
}
