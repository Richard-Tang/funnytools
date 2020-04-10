package com.funnysec.richardtang.funnytools.utils;

import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.constant.Protocol;

import javax.net.ssl.HttpsURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;

/**
 * 网络工具
 *
 * @author RichardTang
 * @date 2020年3月15日22:03:58
 */
public class NetUtils {

    /**
     * 需要排除监控的ip范围
     * 36.56.0.0-36.63.255.255
     * 61.232.0.0-61.237.255.255
     * 106.80.0.0-106.95.255.255
     * 121.76.0.0-121.77.255.255
     * 123.232.0.0-123.235.255.255
     * 139.196.0.0-139.215.255.255
     * 171.8.0.0-171.15.255.255
     * 182.80.0.0-182.92.255.255
     * 210.25.0.0-210.47.255.255
     * 222.16.0.0-222.95.255.255
     */
    private static final int[][] range = {
            {607649792, 608174079}, {1038614528, 1039007743},
            {1783627776, 1784676351}, {2035023872, 2035154943},
            {2078801920, 2079064063}, {-1950089216, -1948778497},
            {-1425539072, -1425014785}, {-1236271104, -1235419137},
            {-770113536, -768606209}, {-569376768, -564133889}
    };

    /**
     * 根据域名从A记录中获取IP地址
     *
     * @param domain 域名
     * @return ip地址 或 空字符串
     * @see <a href='https://github.com/Chora10/FuzzDomain' target='_blabk'>Chora10</>
     */
    public static String getIp(String domain) {
        StringBuilder ip = new StringBuilder();
        try {
            InetAddress[] ias = InetAddress.getAllByName(domain);
            for (InetAddress ia : ias) {
                ip.append(ia.toString().split(Character.BACKSLASH)[1]).append(Character.COMMA);
            }
            if (ip.length() > 0 && ip.substring(0, ip.length()).endsWith(Character.COMMA)) {
                return ip.substring(0, ip.length() - 1);
            }
        } catch (UnknownHostException e) {
            return null;
        }
        return ip.toString();
    }

    /**
     * 获取目标域名所使用的http协议类型
     *
     * @param domain 域名
     * @return String https or http
     */
    public static String getHttpProtocol(String domain) {
        try {
            URL url = new URL(Protocol.HTTPS + domain);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.connect();
            connection.disconnect();
            return Protocol.HTTPS;
        } catch (Exception e) {
            return Protocol.HTTP;
        }
    }

    /**
     * 判断该域名是否为泛域名的方式解析
     * 原理: 随机生成一个不可能存在的域名,如果也能解析出ip证明为泛域名解析
     *
     * @param domain 域名
     * @return boolean 是否为泛域名
     */
    public static boolean isPanDomain(String domain) {
        domain = String.format("%s.%s", UUID.randomUUID(), domain);
        return StrUtil.isNotEmpty(NetUtils.getIp(domain));
    }

    /**
     * 获取一个随机的IP地址
     * @return ip
     */
    public static String getRandomIp() {
        Random rdint = new Random();
        int index = rdint.nextInt(10);
        return num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
    }

    /**
     * 将十进制转换成IP地址
     * @param ip 十进制Ip地址
     * @return ip
     */
    public static String num2ip(int ip) {
        int[] b = new int[4];
        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        return Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);
    }
}