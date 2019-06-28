package profhunt.pojo;

public class Prof {
  @Override
  public String toString() {
    return "Prof{"
        + "email='"
        + email
        + '\''
        + ", salutation='"
        + salutation
        + '\''
        + ", subject='"
        + subject
        + '\''
        + ", fileNames='"
        + fileNames
        + '\''
        + ", emailTemplateName='"
        + emailTemplateName
        + '\''
        + ", name='"
        + name
        + '\''
        + '}';
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSalutation() {
    return salutation;
  }

  public void setSalutation(String salutation) {
    this.salutation = salutation;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getFileNames() {
    return fileNames;
  }

  public void setFileNames(String fileNames) {
    this.fileNames = fileNames;
  }

  public String getEmailTemplateName() {
    return emailTemplateName;
  }

  public void setEmailTemplateName(String emailTemplateName) {
    this.emailTemplateName = emailTemplateName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String email;
  private String salutation;
  private String subject;
  private String fileNames;
  private String emailTemplateName;
  private String name;
}
