package com.lifestyle.customer.utility;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author maanifannu
 */
//@Slf4j(topic = "Utility")
public class Utility {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Utility.class);
    
    private static String getIPAddress() {
        try {
            InetAddress inetaddress = InetAddress.getLocalHost();  //Get LocalHost refrence
            String ip = inetaddress.getHostAddress();  // Get Host IP Address
            return ip;   // return IP Address
        } catch (UnknownHostException ex) {
            log.error(ex.getMessage());  //print Exception StackTrace
            return null;
        }

    }

    private static String getSystemName() {
        try {
            InetAddress inetaddress = InetAddress.getLocalHost(); //Get LocalHost refrence
            String name = inetaddress.getHostName();   //Get Host Name
            return name;   //return Host Name
        } catch (UnknownHostException Ex) {
            log.error(Ex.getMessage());
            return null;
        }
    }

    private static String getMAC() {
        try {
            InetAddress inetaddress = InetAddress.getLocalHost(); //Get LocalHost refrence

            //get Network interface Refrence by InetAddress Refrence
            NetworkInterface network = NetworkInterface.getByInetAddress(inetaddress);
            byte[] macArray = network.getHardwareAddress();  //get Harware address Array
            StringBuilder str = new StringBuilder();

            // Convert Array to String 
            for (int i = 0; i < macArray.length; i++) {
                str.append(String.format("%02X%s", macArray[i], (i < macArray.length - 1) ? "-" : ""));
            }
            String macAddress = str.toString();

            return macAddress; //return MAc Address
        } catch (SocketException | UnknownHostException Ex) {
            log.error(Ex.getMessage());
            return null;
        }
    }

    public static Date toDate(String dateString, String format) {
		try {
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.parse(dateString);
		} catch (ParseException ex) {
	   }
		return null;
	}


    	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {

		if (date == null) {
			return null;
		} else {
			XMLGregorianCalendar res = null;
			try {
				GregorianCalendar dateCal = new GregorianCalendar();
				dateCal.setTime(date);

				res = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateCal);
			} catch (DatatypeConfigurationException ex) {
				ex.printStackTrace();
				
			}
			return res;
		}
	}
     
}
