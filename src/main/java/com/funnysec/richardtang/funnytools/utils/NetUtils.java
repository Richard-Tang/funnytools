package com.funnysec.richardtang.funnytools.utils;

import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.constant.Protocol;

import javax.net.ssl.HttpsURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * 网络工具
 *
 * @author RichardTang
 * @date 2020年3月15日22:03:58
 */
public class NetUtils {

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
}
