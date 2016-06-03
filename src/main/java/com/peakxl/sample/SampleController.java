package com.peakxl.sample;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@RestController
public class SampleController {

    @RequestMapping("/")
    public String index(HttpServletRequest request) throws IOException {
        InetAddress serverIp = null;
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        String hostname = "";
        String clientIp = "";
        String userAgent = "";
        StringBuffer sb = new StringBuffer();
        try {
            serverIp = InetAddress.getLocalHost();
            hostname = serverIp.getHostName();
            clientIp = request.getHeader("X-FORWARDED-FOR");
            if (clientIp == null) {
                clientIp = request.getRemoteAddr();
            }
            userAgent = request.getHeader("user-agent");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        List<String> serverAddresses = new ArrayList<>();
        while(interfaces.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) interfaces.nextElement();
            Enumeration inetAddresses = n.getInetAddresses();
            while(inetAddresses.hasMoreElements()) {
                InetAddress address = (InetAddress) inetAddresses.nextElement();
                serverAddresses.add(address.getHostAddress());
            }
        }
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Sample SpringBoot app</title>");
        sb.append("</head>");
        sb.append("<body style=\"background-color:#fff\">");
        sb.append("<table border=\"0\">");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<h1>Sample SpringBoot app</h1>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("Server IP addresses: <strong>");
        for(String address : serverAddresses) {
            sb.append(address);
            sb.append(" ");
        }
        sb.append("</strong>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("Server Hostname: <strong>" + hostname + "</strong>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("Your current IP address: <strong>" + clientIp + "</strong>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("Your current User-Agent: <strong>" + userAgent + "</strong>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();
    }

}