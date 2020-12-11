package com.itranswarp.learnjava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(666); // 监听指定端口
		System.out.println("server is running...");
		for (;;) {
			Socket sock = ss.accept();		//accept接收
			System.out.println("connected from " + sock.getRemoteSocketAddress());
			Thread t = new Handler(sock);
			t.start();		//启动
		}
	}
}

class Handler extends Thread {
	Socket sock;	//Socket流IP地址+端口号

	public Handler(Socket sock) {
		this.sock = sock;
	}

	@Override
	public void run() {
		try (InputStream input = this.sock.getInputStream()) {		//输入流
			try (OutputStream output = this.sock.getOutputStream()) {		//输出流
				handle(input, output);
			}
		} catch (Exception e) {
			try {
				this.sock.close();		//如果出异常就结束Socket流
			} catch (IOException ioe) {
			}
			System.out.println("client disconnected.");		//异常提示语句
		}
	}

	private void handle(InputStream input, OutputStream output) throws IOException {
		var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
		var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		writer.write("hello\n");	//输出语句
		writer.flush();				//刷新
		for (;;) {					//无限循环
			String s = reader.readLine();	//使用String接收逐行读取字段
			if (s.equals("bye")) {			//if比较如果相同则进入if
				writer.write("bye\n");		//输出
				writer.flush();				//刷新
				break;						//跳出循环，if后语句继续运行
			}
			writer.write("ok: " + s + "\n");	//输出
			writer.flush();						//刷新输出流
		}
	}
}
