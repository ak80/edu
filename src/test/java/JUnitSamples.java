import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

public class JUnitSamples {
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void expectException() {
    expectedException.expect(IndexOutOfBoundsException.class);
    expectedException.expectMessage("Index: 0, Size: 0");
    new ArrayList<String>().get(0);
  }

}
