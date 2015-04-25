import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticFoo.class})
public class PowerMockitoSamples {


  @Test
  public void mockStaticMethod() {
    PowerMockito.mockStatic(StaticFoo.class);
    Mockito.when(StaticFoo.getString(any(String.class))).thenReturn("bar");

    assertThat(StaticFoo.getString("foo"), is("bar"));
  }

  @Test
  public void mockNew() throws Exception {
    StaticFoo mockedFoo = Mockito.mock(StaticFoo.class);
    Mockito.when(mockedFoo.getString()).thenReturn("bar");

    PowerMockito.whenNew(StaticFoo.class).withNoArguments().thenReturn(mockedFoo);
    assertThat(StaticFoo.getInstance().getString(), is("bar"));

    PowerMockito.whenNew(StaticFoo.class).withAnyArguments().thenReturn(mockedFoo);
    assertThat(StaticFoo.getInstance("foo").getString(), is("bar"));

    PowerMockito.whenNew(StaticFoo.class).withArguments("foo").thenReturn(mockedFoo);
    assertThat(StaticFoo.getInstance("foo").getString(), is("bar"));
  }

}

class StaticFoo {
  private String _string;

  public StaticFoo() {
    new StaticFoo("foo");
  }

  public StaticFoo(String string) {
    _string = string;
  }

  public static String getString(String string) {
    return string;
  }

  public static StaticFoo getInstance() {
    return new StaticFoo();
  }

  public static StaticFoo getInstance(String string) {
    return new StaticFoo(string);
  }

  public String getString() {
    return "_string";
  }



}
