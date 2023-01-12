//package com.chenyudaima.util.file;
//
//import org.ghost4j.document.Document;
//
//import java.net.URL;
//
///**
// * xml文件工具类
// */
//public class XMLUtil {
//    private static final URL RESOURCE = XMLUtil.class.getClassLoader().getResource("xConfig.xml");
//    private static final String APP_NAME = "xConfig";
//    private static Document document = null;
//    private static Element rootElement = null;
//    private static Element rootName = null;
//
//    private XmlUtil() {
//    }
//
//    /**
//     * 初始化SAXReader
//     *
//     * @param root 查询节点name
//     * @return true
//     */
//    private static boolean init(String root) {
//        if (document == null) {
//            try {
//                document = new SAXReader().read(RESOURCE);
//                rootElement = document.getRootElement();
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            }
//        }
//        if (APP_NAME.equals(rootElement.getName())) {
//            rootName = rootElement.element(root);
//        }
//        return true;
//    }
//
//    /**
//     * 设置查询节点
//     *
//     * @param root
//     * @return 返回设置结果
//     */
//    public static boolean setRoot(String root) {
//        if (root == null) {
//            return false;
//        }
//        return init(root);
//    }
//
//    /**
//     * 获取对应name的元素信息
//     *
//     * @param elementName 查询name
//     * @return 节点信息
//     */
//    public static String getElementByName(String elementName) {
//        if (elementName == null) {
//            return null;
//        }
//        // 迭代获取节点信息
//        return getElementText(rootName.elementIterator(), elementName);
//
//    }
//
//    private static String getElementText(Iterator<Element> it, String elementName) {
//        // 迭代节点
//        while (it.hasNext()) {
//            Element el = it.next();
//            // 如果节点name和传入的查询节点name相同，返回该节点信息
//            if (elementName.equals(el.getName())) {
//                return el.getText();
//            }
//        }
//        return null;
//    }
//
//}
