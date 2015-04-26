import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JUnitRulesSample {
  private ServerSocket _socket;

  @Rule
  public ExternalResource resource = new ExternalResource() {

    @Override
    protected void before() throws Throwable {
      _socket = new ServerSocket(8080);
    };

    @Override
    protected void after() {
      try {
        _socket.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    };

  };

}
