package profhunt;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class Utility {
  static Session createSession(String userName, String password) {
    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "465");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.socketFactory.port", "465");
    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    Session session =
        Session.getInstance(
            prop,
            new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
              }
            });
    return session;
  }

  static void clearScreen() {
    try {
      final String os = System.getProperty("os.name");

      if (os.contains("Windows")) {
        String[] cls = new String[] {"cmd.exe", "/c", "cls"};
        Runtime.getRuntime().exec(cls);
        System.out.print("\033[H\033[2J");
        System.out.flush();
      } else {
        Runtime.getRuntime().exec("clear");
      }
    } catch (final Exception e) {
    }
  }
}
