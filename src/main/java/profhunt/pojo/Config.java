package profhunt.pojo;


import java.util.List;

public class Config {
  private String from;
  private String sendersName;
  private List<Prof> profs;
  private String defaultSubject;
  private List<EmailTemplate> emailTemplates;

  @Override
  public String toString() {
    return "Config{"
        + "from='"
        + from
        + '\''
        + ", sendersName='"
        + sendersName
        + '\''
        + ", profs="
        + profs
        + ", defaultSubject='"
        + defaultSubject
        + '\''
        + ", emailTemplates="
        + emailTemplates
        + '}';
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getSendersName() {
    return sendersName;
  }

  public void setSendersName(String sendersName) {
    this.sendersName = sendersName;
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
}
