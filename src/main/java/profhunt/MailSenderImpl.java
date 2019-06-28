package profhunt;

import com.fasterxml.jackson.databind.ObjectMapper;
import profhunt.pojo.Config;

import java.io.IOException;

public class MailSenderImpl extends MailSender {
  private final ConfigParser configParser;
  private final ObjectMapper objectMapper;

  public MailSenderImpl(
      String url,
      ConfigParser configParser,
      ObjectMapper objectMapper,
      MailSendExecutor mailSendExecutor) {
    super(url);
    this.configParser = configParser;
    this.objectMapper = objectMapper;
    this.mailSendExecutor = mailSendExecutor;
  }

  private final MailSendExecutor mailSendExecutor;

  @Override
  protected void executeMailSend(Config cfg) throws InterruptedException {
    mailSendExecutor.sendMail(cfg);
  }

  @Override
  protected Config parseConfig(String json) throws IOException {
    return objectMapper.readValue(json, Config.class);
  }

  @Override
  protected String downloadJson(String url) throws IOException {
    return configParser.parse(url);
  }
}
