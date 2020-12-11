package com.itranswarp.learnjava;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	public static void main(String[] args) throws IOException, InterruptedException {
		//Socket插口意思是数据通过Socket发送到网络中，就像电通过插座将电传入设备供电
		DatagramSocket ds = new DatagramSocket();	//数据包插口socket 
		ds.setSoTimeout(1000);		//超时设置1000毫秒=1秒
		
		//connect连接
		//inetaddress地址通过getByName(localhost)获取本机IP地址
		//这里应该是创建了一个Socket127.0.0.1:6666
		ds.connect(InetAddress.getByName("localhost"), 6666); // 连接指定服务器和端口
		DatagramPacket packet = null;	//数据包=null
		for (int i = 0; i < 5; i++) {	//for循环从0循环到4共循环5此
			// 发送:
			//String数组通过i选定值并将其赋值给cmd变量
			String cmd = new String[] { "date", "time", "datetime", "weather", "hello" }[i];
			byte[] data = cmd.getBytes();	//将每次循环的cmd变量赋值给byte字节数组
			packet = new DatagramPacket(data, data.length);	//数据包，获取byte数组，以及byte长度
			ds.send(packet);	//发送数据包
			// 接收:
			byte[] buffer = new byte[1024];		//创建字节长度为1024的byte字节数组
			packet = new DatagramPacket(buffer, buffer.length);		//将buffer字节数组，以及数组长度，放入数据包
			ds.receive(packet);		//接收		
			String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());	//data方法 offset偏移量	length长度
			System.out.println(cmd + " >>> " + resp);	//将cmd和数据包信息进行输出
			Thread.sleep(1500);		//睡眠1.5秒
		}
		ds.disconnect();	//disconnect断开Socket连接
		System.out.println("disconnected.");	//输出断开连接提示
	}
}
