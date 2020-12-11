package com.itranswarp.learnjava;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * Send Mail With Attachment发送带附件的邮箱
 * @author super
 *
 */
public class SendMailWithAttachment {
	/*
	 * main方法中先指定STMP和用户名(发送者邮箱)
	 * 	登录指令(邮箱软件设置中打开STMP所获得的)
	 * 	from是发送者邮箱地址
	 * 	to是接收者邮箱地址
	 * 	创建SendMail(发送邮箱)对象，构造方法中传入STMP、用户名、登录指令
	 * 	创建session会话对象接收sendmail对象中的createTLSSession方法(创建TLS会话)
	 * 	
	 */
	public static void main(String[] args) throws Exception {
		final String smtp = "smtp.163.com";
		final String username = "18523399824@163.com";
		final String password = "YEOHQCXIDWYXRUVV";
		final String from = "18523399824@163.com";
		final String to = "2378015086@qq.com";	
		SendMail sender = new SendMail(smtp, username, password);
		Session session = sender.createSSLSession();
		//通过SendMailWithAttachment对象.class.getResourceAsStream获取资源 流(stream)获取文件，使用InputStream(输入流接收)
		try (InputStream input = SendMailWithAttachment.class.getResourceAsStream("/ht.jpg")) {
			Message message = createMessageWithAttachment(session, from, to, "Hello Java邮件带附件",
					"<h1>Hello</h1><p>这是一封带附件的<u>javamail</u>邮件！</p>", "javamail.jpg", input);
			Transport.send(message);
		}
	}

	static Message createMessageWithAttachment(Session session, String from, String to, String subject, String body,
			String fileName, InputStream input) throws MessagingException, IOException {
		MimeMessage message = new MimeMessage(session);		//创建message消息对象中添加session会话信息
		message.setFrom(new InternetAddress(from));			//InternetAddress网络地址
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));	//RecipientType接收者类型为to也就是接收方，加上InternetAddress接收方地址
		message.setSubject(subject, "UTF-8");	//主题utf-8
		Multipart multipart = new MimeMultipart();	//MimeMultipart消息体开始也就是邮件扩展
		// 添加text:
		BodyPart textpart = new MimeBodyPart();		//MimeBodypart邮件正文部分
		textpart.setContent(body, "text/html;charset=utf-8");	//内容格式utf-8
		multipart.addBodyPart(textpart);
		// 添加image影像或图片:
		BodyPart imagepart = new MimeBodyPart();
		imagepart.setFileName(fileName);	//设置文件名
		//DataHandler设置数据处理器、ByteArrayDataSource字节类型数据源
		//发送二进制文件使用application/octet-stream
		imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(input, "application/octet-stream")));
		multipart.addBodyPart(imagepart);	//添加正文部分BodyPart
		message.setContent(multipart);		//设置内容Content
		return message;						//返回message消息
	}

}
