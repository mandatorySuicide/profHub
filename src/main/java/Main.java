import com.fasterxml.jackson.databind.ObjectMapper;
import profhunt.ConfigParser;
import profhunt.MailSendExecutor;
import profhunt.MailSender;
import profhunt.MailSenderImpl;

public class Main {

  public static void main(String[] args) {
    for (String arg : args) {
      System.out.println(arg);
    }
    //
    //    System.out.println(" I am working");
    //    Scanner sc = new Scanner(System.in);
    //    int next = Integer.parseInt(sc.next());
    //    System.out.println("You entered " + next);
    try {
      String url = getUrlFromArgs(args);
      System.out.println("Config URL " + url);
      ConfigParser configParser = new ConfigParser();
      ObjectMapper objectMapper = new ObjectMapper();
      String sendersEmail = getUserNameFromArgs(args);
      String sendersEmailPassword = getPasswordFromArgs(args);
      System.out.println(
          "Sender's Email " + sendersEmail + "\nSender's Email Password " + sendersEmailPassword);
      MailSendExecutor mailSendExecutor =
          new MailSendExecutor(sendersEmail, sendersEmailPassword, getCvPath(args));
      MailSender mailSender = new MailSenderImpl(url, configParser, objectMapper, mailSendExecutor);
      mailSender.run();
    } catch (Exception e) {
      System.out.println(
          "Sorry, Some error occurred = {}. Please talk to the dev at [najim.ju@gmail.com]"
              + e.getMessage());
    }
  }

  private static String getCvPath(String[] args) {
    return getValue(args, "-c", "path_to_your_cv");
  }

  private static String getPasswordFromArgs(String[] args) {

    return getValue(args, "-p", "password");
  }

  private static String getUserNameFromArgs(String[] args) {
    return getValue(args, "-e", "your_email");
  }

  private static String getUrlFromArgs(String[] args) {
    return getValue(args, "-u", "config_url");
  }

  private static String getValue(String[] args, String key, String keyFor) {
    for (String arg : args) {
      if (arg.startsWith(key)) {
        int pos = arg.indexOf("=");
        if (pos != -1) {
          String password = arg.substring(pos + 1);
          if (password != null && !password.equalsIgnoreCase("")) {
            return password;
          }
        }
      }
    }
    throw new RuntimeException(
        String.format(
            "Could not resolve %s, please use %s=%s flag properly, read the read me file carefully and follow the instruction",
            keyFor, key, keyFor));
  }
}