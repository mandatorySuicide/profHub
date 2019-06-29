package profhunt;

import profhunt.pojo.FileToAttach;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class MessageDecorator {
  private final Message message;
  private String userName;
  private String sendersName;

  public MessageDecorator(Message message) {
    this.message = message;
  }

  public MessageDecorator withSendersEmail(String userName) {
    this.userName = userName;
    return this;
  }

  public MessageDecorator withSendersName(String sendersName) {
    this.sendersName = sendersName;
    return this;
  }

  public MessageDecorator withReceiversEmail(String email) {
    try {
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return this;
  }

  public MessageDecorator withSubject(String subject) {
    try {
      message.setSubject(subject);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return this;
  }

  public Message getMessage() {
    try {
      message.setFrom(new InternetAddress(userName, sendersName));
    } catch (MessagingException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return message;
  }


  public MessageDecorator withEmailBodyContent(Multipart content) {
    try {
      message.setContent(content);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return this;
  }
}
