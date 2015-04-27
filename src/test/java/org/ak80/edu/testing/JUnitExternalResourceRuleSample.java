package org.ak80.edu.testing;

import org.junit.Rule;
import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.net.ServerSocket;

public class JUnitExternalResourceRuleSample {
  private ServerSocket _socket;

  @Rule
  public ExternalResource resource = new ExternalResource() {

    @Override
    protected void before() throws Throwable {
      _socket = new ServerSocket(8080);
    }

    ;

    @Override
    protected void after() {
      try {
        _socket.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    ;

  };

}
