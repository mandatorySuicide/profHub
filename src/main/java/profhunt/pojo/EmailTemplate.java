package profhunt.pojo;



public class EmailTemplate {
  private String name;
  private String text;

  @Override
  public String toString() {
    return "EmailTemplate{" + "name='" + name + '\'' + ", text='" + text + '\'' + '}';
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
