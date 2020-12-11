package com.itranswarp.learnjava;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	final String smtpHost;	//STMP主机
	final String username;	//用户名
	final String password;	//密码
	final boolean debug;	//调试
	
	//发送邮件信息构造方法：主机、用户名、密码
	public SendMail(String smtpHost, String username, String password) {
		this.smtpHost = smtpHost;
		this.username = username;
		this.password = password;
		this.debug = true;
	}

	public static void main(String[] args) throws Exception {
		final String smtp = "smtp.163.com";
		//final String username = "jxsmtp101@outlook.com";
		//final String password = "java-12345678";
		final String username = "18523399824@163.com";
		final String password = "YEOHQCXIDWYXRUVV";
		final String from = "18523399824@163.com";
		final String to = "2378015086@qq.com";
		SendMail sender = new SendMail(smtp, username, password);	//发送邮件对象
		Session session = sender.createSSLSession();		//create TLS Session 创建 TLS 会话
		
		//创建文本消息
		Message message = createTextMessage(session, from, to, "JavaMail邮件", "Hello, 这是一封来自18523399824@163.com的javamail邮件！");
		Transport.send(message);	//发送
	}
	
	//创建STL会话
	Session createSSLSession() {
		Properties props = new Properties();	//属性
		props.put("mail.smtp.host", this.smtpHost); // SMTP主机名
		props.put("mail.smtp.port", "465"); // 主机端口号
		props.put("mail.smtp.auth", "true"); // 是否需要用户认证
		// 启动SSL:
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.port", "465");
		Session session = Session.getInstance(props, new Authenticator() {
			// 用户名+口令认证:
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SendMail.this.username, SendMail.this.password);
			}
		});
		session.setDebug(this.debug); // 显示调试信息
		return session;
	}

	Session createTLSSession() {
		Properties props = new Properties();	//属性
		props.put("mail.smtp.host", this.smtpHost); // SMTP主机名
		props.put("mail.smtp.port", "587"); // 主机端口号
		props.put("mail.smtp.auth", "true"); // 是否需要用户认证
		props.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密
		//getInstance获得实例
		Session session = Session.getInstance(props, new Authenticator() {
			//PasswordAuthentication密码身份验证
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SendMail.this.username, SendMail.this.password);
			}
		});
		session.setDebug(this.debug); // 显示调试信息
		return session;
	}
	
	//create Insecure Session 创建 不安全 的会话
	Session createInsecureSession(String host, String username, String password) {
		Properties props = new Properties();	//属性
		props.put("mail.smtp.host", this.smtpHost); // SMTP主机名
		props.put("mail.smtp.port", "25"); // 主机端口号
		props.put("mail.smtp.auth", "true"); // 是否需要用户认证
		Session session = Session.getInstance(props, new Authenticator() {
			//密码身份验证
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SendMail.this.username, SendMail.this.password);
			}
		});
		session.setDebug(this.debug); // 显示调试信息
		return session;
	}
	
	//创建文本消息
	//session会话、from发送邮件的账号、to收到邮件的账号、subject主题、body主要部位
	static Message createTextMessage(Session session, String from, String to, String subject, String body)
			throws MessagingException {
		MimeMessage message = new MimeMessage(session);	//MIME会话
		message.setFrom(new InternetAddress(from));		//设置发送邮件账号	InternetAddress互联网地址
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));	//setRecipient设置接收方，Recipienttype接收类型
		message.setSubject(subject, "UTF-8");	//主题和主要部分格式utf-8
		message.setText(body, "UTF-8");
		return message;		//将message会话数据返回
	}

}
