package com.itranswarp.learnjava;

/**
 * SendMailWithHTML用HTML发送邮件
 */
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailWithHTML {

	public static void main(String[] args) throws Exception {
		final String smtp = "smtp.163.com";
		final String username = "18523399824@163.com";
		final String password = "YEOHQCXIDWYXRUVV";
		final String from = "18523399824@163.com";
		final String to = "2378015086@qq.com";
		SendMail sender = new SendMail(smtp, username, password);
		Session session = sender.createSSLSession();
		Message message = createHtmlMessage(session, from, to, "Java HTML邮件",
				"<h1>Hello</h1></n><img src=\"/network-smtp/target/ht.jpg\"  alt=\"\" /><p>这是一封<u>来自2378015086@qq.com的</u>HTML邮件！</p>");
		Transport.send(message);
	}

	static Message createHtmlMessage(Session session, String from, String to, String subject, String body)
			throws MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject, "UTF-8");
		message.setText(body, "UTF-8", "html");	//和text邮件相比使用html发送邮件需加上html
		return message;
	}

}
