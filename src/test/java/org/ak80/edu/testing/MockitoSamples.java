package org.ak80.edu.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSamples {

  @Mock
  Foo fooMock;
  Foo fooMockWithoutAnnotation = Mockito.mock(Foo.class);

  @Mock(answer = RETURNS_DEEP_STUBS)
  Foo fooWithDeepStubbing;

  @Spy
  Foo fooSpy;
  Foo fooSpyWithoutAnnotation = Mockito.spy(new Foo());

  @Captor
  ArgumentCaptor<String> captor;
  ArgumentCaptor<String> captorWithoutAnnotation = ArgumentCaptor.forClass(String.class);

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void usingMockito() {

    // mock
    Mockito.when(fooMock.getString("foo")).thenReturn("bar");
    assertThat(fooMock.getString("foo"), is("bar"));

    Mockito.when(fooMock.getString(any(String.class))).thenReturn("bar");
    assertThat(fooMock.getString("anything"), is("bar"));

    Mockito.when(fooMock.getString(anyString())).thenReturn("foo");
    assertThat(fooMock.getString("whatever"), is("foo"));

    Mockito.when(fooMock.getString(eq("foo"))).thenReturn("bar");
    assertThat(fooMock.getString("foo"), is("bar"));

    // return values for multiple calls
    Mockito.when(fooMock.getString(anyString())).thenReturn("foo").thenReturn("bar");
    assertThat(fooMock.getString("whatever"), is("foo"));
    assertThat(fooMock.getString("whatever"), is("bar"));

    // call real methods
    Mockito.doCallRealMethod().when(fooMock).setString(any(String.class));
    Mockito.when(fooMock.getString()).thenCallRealMethod();
    fooMock.setString("hello");
    assertThat(fooMock.getString(), is("hello"));

    // spy
    fooSpy.getString("foo");

    Mockito.verify(fooSpy).getString("foo");
    Mockito.verify(fooSpy, times(1)).getString("foo");
    Mockito.verify(fooSpy, atLeastOnce()).getString("foo");


    // mock a method from a spy
    Mockito.doReturn("foo").when(fooSpy).getString("foo");
    assertThat(fooSpy.getString("foo"), is("foo"));

    // reset calls and verify zero interactions
    Mockito.reset(fooSpy);
    Mockito.verifyZeroInteractions(fooSpy);
   
    // assert that all interactions that happened have been verified
    Mockito.verifyNoMoreInteractions(fooSpy);

    // throw exception
    Mockito.when(fooMock.getString(anyString())).thenThrow(IllegalStateException.class);
  }

  @Test
  public void captureArgument() {
    fooSpy.getString("string");

    Mockito.verify(fooSpy).getString(captor.capture());
    assertThat(captor.getValue(), is("string"));
  }

  @Test
  public void testCustomAnswer() {

    Foo fooMock = Mockito.mock(Foo.class);
    doAnswer(new Answer<String>() {
      @Override
      public String answer(InvocationOnMock invocation) {
        return "string " + invocation.getArguments()[0];
      }
    }).when(fooMock).getString(anyString());

    assertThat(fooMock.getString("foo"), is("string foo"));

    Foo fooMockWithLambda = Mockito.mock(Foo.class);
    doAnswer(invocation -> "string " + invocation.getArguments()[0]).when(fooMockWithLambda).getString(anyString());

    assertThat(fooMockWithLambda.getString("foo"), is("string foo"));
  }

  class Foo {
    private String _string;

    public String getString(String string) {
      return string;
    }

    public void setString(String string) {
      _string = string;
    }

    public String getString() {
      return _string;
    }

  }
}

