package com.chenyudaima.util;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 字符串提取工具类
 */
public class StringExtractionUtil {

    /**
     * 从text提取人名
     */
    public static String getStrName(String text){
        String regEx = "(王|李|张|刘|陈|杨|黄|赵|吴|周|徐|孙|马|朱|胡|郭|何|高|林|罗|郑|梁|谢|宋|唐|许|韩|冯|邓|曹|彭|曾" +
                "|肖|田|董|袁|潘|于|蒋|蔡|余|杜|叶|苏|魏|吕|丁|任|沈|姚|卢|姜|崔|钟|谭|陆|汪|范|金|石|廖|贾|夏|韦|傅" +
                "|方|白|邹|孟|熊|秦|邱|江|尹|薛|闫|雷|侯|龙|史|黎|贺|顾|毛|郝|龚|邵|万|钱|覃|武|戴|孔|汤|庞|樊|兰|殷" +
                "|施|陶|洪|翟|安|颜|倪|严|牛|温|芦|季|俞|章|鲁|葛|伍|申|尤|毕|聂|柴|焦|向|柳|邢|岳|齐|沿|梅|莫|庄|辛|管" +
                "|祝|左|涂|谷|祁|舒|耿|牟|卜|路|詹|关|苗|凌|费|纪|靳|盛|童|欧|甄|曲|成|游|阳|裴|席|卫|屈|鲍" +
                "|覃|霍|翁|隋|植|甘|景|薄|单|包|司|柏|宁|柯|阮|桂|闵|欧阳|解|强|丛|华|车|冉|房|边|辜|吉|饶|刁|瞿|戚|丘" +
                "|古|米|池|滕|晋|苑|邬|臧|畅|宫|来|嵺|苟|褚|廉|简|娄|奚|木|穆|党|燕|郎|邸|冀|谈|姬|屠|连|郜|晏" +
                "|栾|郁|商|蒙|喻|揭|窦|迟|宇|敖|糜|鄢|冷|卓|花|艾|蓝|都|巩|稽|井|练|仲|乐|虞|卞|封|竺|冼|原|官|衣|楚" +
                "|佟|栗|匡|宗|应|台|巫|鞠|僧|桑|荆|谌|银|扬|明|沙|薄|伏|岑|习|胥|保|和|蔺|水|云|昌|凤|酆|常|皮|康|元|平" +
                "|萧|湛|禹|无|贝|茅|麻|危|骆|支|咎|经|裘|缪|干|宣|贲|杭|诸|钮|嵇|滑|荣|荀|羊|於|惠|家|芮|羿|储|汲|邴|松" +
                "|富|乌|巴|弓|牧|隗|山|宓|蓬|郗|班|仰|秋|伊|仇|暴|钭|厉|戎|祖|束|幸|韶|蓟|印|宿|怀|蒲|鄂|索|咸|籍|赖|乔" +
                "|阴|能|苍|双|闻|莘|贡|逢|扶|堵|宰|郦|雍|却|璩|濮|寿|通|扈|郏|浦|尚|农|别|阎|充|慕|茹|宦|鱼|慎|戈" +
                "|庚|终|暨|居|衡|步|满|弘|国|文|寇|广|禄|阙|东|殴|殳|沃|利|蔚|越|夔|隆|师" +
                "|养|须|丰|红" +
                "|笪|年|爱|仝|代)[\u4E00-\u9FA5]{1,2}";
        Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(text);
        while(m.find()) {
            return m.group();
        }
        return null;
    }

    /**
     *  从text提取最后一个整数
     */
    public static String getLastNumber(String text){
        String regEx = "(\\d+)$";
        Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(text);
        while(m.find()) {
            return m.group();
        }
        return null;
    }

    /**
     * 从text提取所有整数
     */
    public static String getNumber(String text){
        return Pattern.compile("[^0-9]").matcher(text).replaceAll("");
    }


    /**
     * 从text提取手机号码
     */
    public static String pickUpPhoneNo(String text) {
        //手机号正则
        String regExp = "(1[3-9]\\d{9})";
        Pattern phonePattern = Pattern.compile(regExp);
        Matcher matcher = phonePattern.matcher(text);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从text提取时间 yyyy-MM-dd HH:mm:ss 或 yyyy/mm/dd HH:mm:ss
     */
    public static String extractTime(String text) {
        String s = "";
        String yyyyMMdd = extractTimeyyyymmdd(text);
        String HHmmss = extractTimehhmmss(text);
        HHmmss = HHmmss == null ? extractTimemmss(text) : HHmmss;
        if(yyyyMMdd != null) s = yyyyMMdd;
        if(HHmmss != null) s += " " + HHmmss;
        return s;
    }

    /**
     * 从text提取时分秒  格式 HH:mm:ss
     */
    private static String extractTimehhmmss(String text) {
        String regEx = "([0-1][0-9]|(2[0-3])):([0-5][0-9]):([0-5][0-9])";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从text提取时分  格式 HH:mm
     */
    private static String extractTimemmss(String text) {
        String regEx = "([0-5][0-9]):([0-5][0-9])";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从text提取年月日 格式 yyyy-MM-dd | yyyy/MM/dd
     */
    private static String extractTimeyyyymmdd(String text) {
        String regEx = "(20\\d{2}([\\.\\-/|年月\\s]{1,3}\\d{1,2}){2}日?)";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从text提取身份证
     */
    public static String pickUpCardNo(String text) {
        //身份证号正则
        String reg = "\\d{17}[\\d|x|X]|\\d{15}";
        Pattern pattern=Pattern.compile(reg);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 提取中间text
     * @param text   ra0.8hm
     * @param prefix ra
     * @param suffix hm
     * @return 0.8
     */
    public static String extractTheMiddle(String text,String prefix,String suffix) {
        String regExp = "(?<=" + prefix + ").*?(?=" + suffix + ")";
        Pattern phonePattern = Pattern.compile(regExp);
        Matcher matcher = phonePattern.matcher(text);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}
