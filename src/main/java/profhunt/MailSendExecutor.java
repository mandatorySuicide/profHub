package profhunt;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import profhunt.pojo.Config;
import profhunt.pojo.EmailTemplate;
import profhunt.pojo.Prof;

public class MailSendExecutor {
  private final String userName;
  private final String password;
  private final String cvPath;

  public MailSendExecutor(String userName, String password, String cvPath) {
    this.userName = userName;
    this.password = password;
    this.cvPath = cvPath;
  }

  public void sendMail(Config cfg) throws InterruptedException {
    StringBuilder builder = new StringBuilder();
    final int count[] = new int[] {0};
    cfg.getProfs()
        .forEach(
            prof -> {
              count[0]++;
              if (count[0] % 5 == 0) {
                clearScreen();
              }
              try {
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

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(userName, cfg.getSendersName()));
                message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(prof.getEmail()));
                String subject = getSubject(prof.getSubject(), cfg.getDefaultSubject());
                message.setSubject(subject);
                /** ******************************************** */
                Multipart multipart = new MimeMultipart();
                BodyPart messageBodyPart = new MimeBodyPart();
                String bodyText =
                    String.format(
                        findEmailTemplate(prof.getEmailTemplate(), cfg.getEmailTemplates()),
                        makeProfNameWithSalutation(prof),
                        cfg.getSendersName(),
                        cfg.getSendersName());
                messageBodyPart.setText(bodyText);
                multipart.addBodyPart(messageBodyPart);

                Arrays.asList(prof.getFileNames().split(",")).stream()
                    .map(f -> f.trim())
                    .forEach(
                        fileName -> {
                          try {
                            System.out.println("Going to attach a file from " + cvPath + "...");
                            MimeBodyPart mimeBodyPart = buildMimeBodyPart(fileName, cvPath);
                            multipart.addBodyPart(mimeBodyPart);
                          } catch (Exception e) {
                            throw new RuntimeException("MimeBodyPart failed");
                          }
                        });

                message.setContent(multipart);
                if (consentForProf(prof, bodyText, subject)) {
                  Transport.send(message);
                  if (builder.length() > 0) builder.append(",");
                  builder.append(prof.getEmail());
                  System.out.println("Sent message successfully....");
                } else {
                  System.out.println("Ok skipped!");
                }
              } catch (MessagingException e) {
                System.out.println(
                    "Failed to send mail to prof " + prof + "due to = MessagingException = " + e);
              } catch (UnsupportedEncodingException e) {
                System.out.println(
                    "Failed to send mail to prof "
                        + prof
                        + "due to = UnsupportedEncodingException = "
                        + e);
              } catch (Exception e) {
                System.out.println(
                    "Failed to send mail to prof " + prof + "due to = Exception = " + e);
              }
            });
    System.out.println("Email successfully sent to " + builder.toString());
  }

  private void clearScreen() {
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

  private boolean consentForProf(Prof prof, String bodyText, String subject) {
    System.out.println(
        "Do you want to send email to professor "
            + makeProfNameWithSalutation(prof)
            + "\nProf Details : \n"
            + prof
            + "\nWith Subject : "
            + subject
            + "\nPress(y|Y) to continue...");
    return takeConsent();
  }

  private String findEmailTemplate(String emailTemplateName, List<EmailTemplate> emailTemplates) {
    if (emailTemplateName == null || emailTemplateName.equalsIgnoreCase("")) {
      throw new RuntimeException("Email template must not be empty or null");
    }
    EmailTemplate emailTemplate =
        emailTemplates.stream()
            .filter(e -> emailTemplateName.equalsIgnoreCase(e.getName()))
            .findFirst()
            .orElse(null);
    if (emailTemplate == null) {
      throw new RuntimeException("NO email template found with name " + emailTemplateName);
    }
    return emailTemplate.getText();
  }

  private String getSubject(String subject, String defaultSubject) {
    if (subject == null || subject.equalsIgnoreCase("")) {
      if (defaultSubject == null || defaultSubject.equalsIgnoreCase("")) {
        throw new RuntimeException("Subject must not be null, at least define a default subject");
      }
      return defaultSubject;
    }
    return subject;
  }

  private String makeProfNameWithSalutation(Prof prof) {
    if (prof.getSalutation() == null || "".equalsIgnoreCase(prof.getSalutation())) {
      return prof.getName();
    }
    return String.format("%s %s", prof.getSalutation(), prof.getName());
  }

  private boolean takeConsent() {
    try {
      Scanner sc = new Scanner(System.in);
      String s = sc.nextLine();
      if ("y".equalsIgnoreCase(s)) return true;
      return false;
    } catch (Exception e) {
      return false;
    }
  }

  private MimeBodyPart buildMimeBodyPart(String fileName, String cvPath) throws MessagingException {
    MimeBodyPart mimeBodyPart = new MimeBodyPart();
    System.out.println("Trying to attach CV from " + cvPath + ", fileName=" + fileName);
    if (!cvPath.endsWith("\\")) {
      cvPath += "\\";
    }
    DataSource source = new FileDataSource(cvPath + fileName);
    mimeBodyPart.setDataHandler(new DataHandler(source));
    //    mimeBodyPart.setFileName(fileName);
    return mimeBodyPart;
  }
}
