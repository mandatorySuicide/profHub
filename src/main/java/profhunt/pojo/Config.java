package profhunt.pojo;

import java.util.List;

public class Config {

  private String sendersName;
  private String defaultSalutation;
  private List<Prof> profs;
  private String defaultSubject;
  private List<EmailTemplate> emailTemplates;
  private String defaultEmailTemplate;

  @Override
  public String toString() {
    return "Config{"
        + "sendersName='"
        + sendersName
        + '\''
        + ", defaultSalutation='"
        + defaultSalutation
        + '\''
        + ", profs="
        + profs
        + ", defaultSubject='"
        + defaultSubject
        + '\''
        + ", emailTemplates="
        + emailTemplates
        + ", defaultEmailTemplate='"
        + defaultEmailTemplate
        + '\''
        + '}';
  }

  public String getSendersName() {
    return sendersName;
  }

  public void setSendersName(String sendersName) {
    this.sendersName = sendersName;
  }

  public String getDefaultSalutation() {
    return defaultSalutation;
  }

  public void setDefaultSalutation(String defaultSalutation) {
    this.defaultSalutation = defaultSalutation;
  }

  public List<Prof> getProfs() {
    return profs;
  }

  public void setProfs(List<Prof> profs) {
    this.profs = profs;
  }

  public String getDefaultSubject() {
    return defaultSubject;
  }

  public void setDefaultSubject(String defaultSubject) {
    this.defaultSubject = defaultSubject;
  }

  public List<EmailTemplate> getEmailTemplates() {
    return emailTemplates;
  }

  public void setEmailTemplates(List<EmailTemplate> emailTemplates) {
    this.emailTemplates = emailTemplates;
  }

  public String getDefaultEmailTemplate() {
    return defaultEmailTemplate;
  }

  public void setDefaultEmailTemplate(String defaultEmailTemplate) {
    this.defaultEmailTemplate = defaultEmailTemplate;
  }
}
