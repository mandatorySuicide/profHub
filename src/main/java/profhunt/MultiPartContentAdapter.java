package profhunt;

import profhunt.pojo.FileToAttach;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class MultiPartContentAdapter {
    public static MultiPartContentAdapterBuilder getBuilder(Multipart multipart) {
        return new MultiPartContentAdapterBuilder(multipart);
    }

    public static class MultiPartContentAdapterBuilder {
        private final Multipart multipart;

        public MultiPartContentAdapterBuilder(Multipart multipart) {
            this.multipart = multipart;
        }

        public MultiPartContentAdapterBuilder withMessageBodyPart(String emailBody) {
            BodyPart messageBodyPart = new MimeBodyPart();
            try {
                messageBodyPart.setText(emailBody);
                multipart.addBodyPart(messageBodyPart);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public MultiPartContentAdapterBuilder withAttachments(
                List<FileToAttach> attachments, String resourceDirectory) {
            attachments.stream()
                    .forEach(
                            attachment -> {
                                try {
                                    System.out.println("Going to attach a file from " + resourceDirectory + "...");
                                    MimeBodyPart mimeBodyPart =
                                            buildMimeBodyPart(
                                                    attachment.getName(), resourceDirectory, attachment.getRenameTo());
                                    multipart.addBodyPart(mimeBodyPart);
                                } catch (Exception e) {
                                    throw new RuntimeException("MimeBodyPart failed");
                                }
                            });
            return this;
        }

        public Multipart getContent() {
            return multipart;
        }

        private MimeBodyPart buildMimeBodyPart(
                String fileName, String resourceDirecotry, String renameTo) throws MessagingException {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            System.out.println(
                    "Trying to attach CV from " + resourceDirecotry + ", fileName=" + fileName);
            if (!resourceDirecotry.endsWith("\\")) {
                resourceDirecotry += java.io.File.separator;
                //java.io.File.separator
            }
            if (!Files.exists(Paths.get(resourceDirecotry + fileName))) {
                System.out.println("File does not exist" + (resourceDirecotry + fileName));
                throw new RuntimeException("File does not exist" + (resourceDirecotry + fileName));
            }

            DataSource source = new FileDataSource(resourceDirecotry + fileName);
            mimeBodyPart.setDataHandler(new DataHandler(source));
            mimeBodyPart.setFileName(renameTo);
            return mimeBodyPart;
        }
    }
}
