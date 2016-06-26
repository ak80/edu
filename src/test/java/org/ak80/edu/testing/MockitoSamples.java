package org.ak80.edu.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSamples {

  // see also https://dzone.com/refcardz/mockito

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
    // not needed if you use MockitoJUnit4Runner
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
    Mockito.verify(fooSpy,times(1)).getString("foo");
    Mockito.verify(fooSpy,atLeast(1)).getString("foo");
    Mockito.verify(fooSpy,atLeastOnce()).getString("foo");
    Mockito.verify(fooSpy,atMost(1)).getString("foo");
    Mockito.verify(fooSpy,times(0)).getString("bar");
    Mockito.verify(fooSpy,never()).getString("bar");
    Mockito.verify(fooSpy,only()).getString(anyString()); // no other method called
    Mockito.verify(fooSpy,timeout(100)).getString(anyString()); // timeout in millis

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

    // mock with more interfaces to support instance of
    Foo isFooAndBar = mock(Foo.class, withSettings().extraInterfaces(Bar.class));
    assertThat(isFooAndBar, instanceOf(Bar.class));
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

  @Test
  public void testCustomAnswerForAll() {

    Foo fooMock = Mockito.mock(Foo.class,new Answer<String>() {
      @Override
      public String answer(InvocationOnMock invocation) {
        return invocation.getMethod().getName()+"(" + invocation.getArguments()[0]+")";
      }
    });

    assertThat(fooMock.getString("foo"), is("getString(foo)"));

  }

  @Test
  public void testExecutionInOrder() {
    Foo fooMock1 = Mockito.mock(Foo.class);
    Foo fooMock2 = Mockito.mock(Foo.class);

    fooMock1.getString();
    fooMock2.getString();
    fooMock1.getString("foo");

    InOrder inOrder = inOrder(fooMock1,fooMock2);
    inOrder.verify(fooMock1).getString();
    inOrder.verify(fooMock2).getString();
    inOrder.verify(fooMock1).getString("foo");
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

  interface Bar {

  }

}

