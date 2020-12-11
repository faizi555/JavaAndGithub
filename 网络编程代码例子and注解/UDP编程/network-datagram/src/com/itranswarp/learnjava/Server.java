package com.itranswarp.learnjava;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Server {
	public static void main(String[] args) throws IOException {
		DatagramSocket ds = new DatagramSocket(6666); // 监听指定端口
		System.out.println("server is running...");
		for (;;) {
			// 接收:
			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);	//数据包buffer 以及buffer的length长度
			ds.receive(packet);		//接收
			//获得方法 offset偏移量	length长度	标准数据集utf-8
			String cmd = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);	
			// 发送:
			String resp = "bad command";
			
			 switch (cmd) { case "date": resp = LocalDate.now().toString(); break; case
			 "time": resp = LocalTime.now().withNano(0).toString(); break; case
			 "datetime": resp = LocalDateTime.now().withNano(0).toString(); break; case
			 "weather": resp = "sunny, 10~15 C."; break; }
			 
			//判断cmd也就是监听到客户端的packet数据进行比较
			/*
			 * if(cmd.equals("date")) { resp = LocalDate.now().toString();
			 * //localdate.now.tostring当前日期转换成string类型 break; }else if(cmd.equals("time")) {
			 * resp = LocalTime.now().withNano(0).toString();
			 * //localtime当前时间witnano毫(到毫秒)转换为string类型 break; }else
			 * if(cmd.equals("datetime")) { resp =
			 * LocalDateTime.now().withNano(0).toString(); //当前日期时间精确到微秒转换为string类型 break;
			 * }else if(cmd.equals("weather")) { resp = "sunny, 10~15 C."; break; }
			 */
			
			System.out.println(cmd + " >>> " + resp);	//输出date日期or time时间 or datetime日期时间
			packet.setData(resp.getBytes(StandardCharsets.UTF_8));	//从客户端监听到的数据包获取字符串并将标准数据集转换为utf-8
			ds.send(packet);	//将数据包packet发送send回客户端
		}
	}
}
