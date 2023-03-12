package profhunt;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Scanner;

import profhunt.pojo.Config;
import profhunt.pojo.EmailTemplate;
import profhunt.pojo.Prof;

public class MailSendExecutor {
    private final String userName;
    private final String password;
    private final String resourceDirectory;

    public MailSendExecutor(String userName, String password, String resourceDirecotry) {
        this.userName = userName;
        this.password = password;
        this.resourceDirectory = resourceDirecotry;
    }

    public void sendEmails(Config cfg) {
        StringBuilder builder = new StringBuilder();
        final int count[] = new int[]{0};
        cfg.getProfs()
                .forEach(
                        prof -> {
                            try {
                                count[0]++;
                                if (count[0] % 5 == 0) {
                                    Utility.clearScreen();
                                }
                                Session session = Utility.createSession(userName, password);
                                String emailTemplate =
                                        findEmailTemplate(
                                                prof.getEmailTemplate(),
                                                cfg.getEmailTemplates(),
                                                cfg.getDefaultEmailTemplate());
                                String profNameWithSalutation =
                                        makeProfNameWithSalutation(prof, cfg.getDefaultSalutation());
                                String emailBodyText = String.format(emailTemplate, profNameWithSalutation);
                                String subjectOfEmail = getSubject(prof.getSubject(), cfg.getDefaultSubject());
                                Multipart content =
                                        MultiPartContentAdapter.getBuilder(new MimeMultipart())
                                                .withMessageBodyPart(emailBodyText)
                                                .withAttachments(prof.getAttachments(), resourceDirectory)
                                                .getContent();

                                Message message =
                                        new MessageDecorator(new MimeMessage(session))
                                                .withSendersEmail(userName)
                                                .withSendersName(cfg.getSendersName())
                                                .withReceiversEmail(prof.getEmail())
                                                .withSubject(subjectOfEmail)
                                                .withEmailBodyContent(content)
                                                .getMessage();

                                if (consentForProf(profNameWithSalutation, prof, emailBodyText, subjectOfEmail)) {
                                    try {
                                        Transport.send(message);
                                        if (builder.length() > 0) builder.append(",");
                                        builder.append(prof.getEmail());
                                        System.out.println("Sent message successfully....");
                                    } catch (MessagingException e) {
                                        System.out.println("Message sending failed :(");
                                        e.printStackTrace();
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    System.out.println("Ok skipped!");
                                }

                            } catch (Exception e) {
                                System.out.println("Message sending failed :(" + " " + e.getStackTrace());
                            }
                        });

    }

    private boolean consentForProf(
            String profNameWithSalutation, Prof prof, String bodyText, String subject) {
        System.out.println(
                "Do you want to send email to professor "
                        + profNameWithSalutation
                        + "\nProf Details : \n"
                        + prof
                        + "\nWith Subject : "
                        + subject
                        + "\nWith body text :\n"
                        + bodyText
                        + "\nPress(y|Y) to continue...");
        return takeConsent();
    }

    private String findEmailTemplate(
            String emailTemplateName, List<EmailTemplate> emailTemplates, String defaultEmailTemplate) {
        if (emailTemplateName == null || emailTemplateName.equalsIgnoreCase("")) {
            if (defaultEmailTemplate == null || defaultEmailTemplate.equalsIgnoreCase(""))
                throw new RuntimeException("Email template must not be empty or null");
            System.out.println("Trying to use " + defaultEmailTemplate + " as default email template");
            emailTemplateName = defaultEmailTemplate;
        }
        final String finalEmailTemplateName = emailTemplateName;
        EmailTemplate emailTemplate =
                emailTemplates.stream()
                        .filter(e -> finalEmailTemplateName.equalsIgnoreCase(e.getName()))
                        .findFirst()
                        .orElse(null);
        if (emailTemplate == null) {
            System.out.println("No email template found with name " + emailTemplateName);
            throw new RuntimeException("No email template found with name " + emailTemplateName);
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

    private String makeProfNameWithSalutation(Prof prof, String defaultSalutation) {
        if (prof.getSalutation() == null || "".equalsIgnoreCase(prof.getSalutation())) {
            if (defaultSalutation == null && "".equalsIgnoreCase(defaultSalutation)) {
                return prof.getName();
            }
            return String.format("%s %s", defaultSalutation, prof.getName());
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
}
