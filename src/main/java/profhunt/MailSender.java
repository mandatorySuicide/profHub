package profhunt;

import profhunt.pojo.Config;

import java.io.IOException;

public abstract class MailSender {

  private String url;

  public MailSender(String url) {
    this.url = url;
  }

  public void run() throws IOException, InterruptedException {
    String json = downloadJson(url);
    Config cfg = parseConfig(json);
    executeMailSend(cfg);
    System.out.println("Done Sending Mails");
  }

  protected abstract void executeMailSend(Config cfg) throws InterruptedException;

  protected abstract Config parseConfig(String json) throws IOException;

  protected abstract String downloadJson(String url) throws IOException;
}
