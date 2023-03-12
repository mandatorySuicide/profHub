import com.fasterxml.jackson.databind.ObjectMapper;
import profhunt.ConfigParser;
import profhunt.MailSendExecutor;
import profhunt.MailSender;
import profhunt.MailSenderImpl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    System.out.println("Passed Arguments : ");
    for (String arg : args) {
      System.out.println(arg);
    }
    try {
      String url = getUrlFromArgs(args);
      System.out.println("Config URL " + url);
      ConfigParser configParser = new ConfigParser();
      ObjectMapper objectMapper = new ObjectMapper();
      String sendersEmail = getUserNameFromArgs(args);
      String sendersEmailPassword = getPasswordFromArgs(args);
      System.out.println(
          "Sender's Email [" + sendersEmail + "]\nSender's Email Password [" +
              sendersEmailPassword + "]");
      MailSendExecutor mailSendExecutor =
          new MailSendExecutor(sendersEmail, sendersEmailPassword, getResourceDirectory(args));
      MailSender mailSender = new MailSenderImpl(url, configParser, objectMapper, mailSendExecutor);
      mailSender.run();
    } catch (Exception e) {
      System.out.println(
          "Sorry, Some error occurred = {}. Please talk to the dev at [najim.ju@gmail.com]"
              + e.getMessage());
    }
  }

  private static String getResourceDirectory(String[] args) {
    String path = getValue(args, "-c", "path to your resource folder");
    if (Files.exists(Paths.get(path))) {
      return path;
    }
    throw new RuntimeException(
        "Path "
            + path
            +
            "Does not exist. Please put all your necessary resources in one folder and use it at path");
  }

  private static String getPasswordFromArgs(String[] args) {

    return getValue(args, "-p", "email password");
  }

  private static String getUserNameFromArgs(String[] args) {
    return getValue(args, "-e", "your email");
  }

  private static String getUrlFromArgs(String[] args) {
    return getValue(args, "-u", "configuration url");
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
    System.out.println("Enter " + keyFor + " : ");
    Scanner sc = new Scanner(System.in);
    return sc.nextLine();
    //    throw new RuntimeException(
    //        String.format(
    //            "Could not resolve %s, please use %s=%s flag properly, read the read me file
    // carefully and follow the instruction",
    //            keyFor, key, keyFor));
  }
}
