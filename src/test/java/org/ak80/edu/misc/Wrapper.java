package org.ak80.edu.misc;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Demonstrates wrapping with dynamic proxy
 */
public class Wrapper {

  class Holder<T> {
    private T object;

    public Holder(T object) {
      this.object = object;
    }

    public void set(T object) {
      this.object = object;
    }

    public T get() {
      return object;
    }

  }

  @Test
  public void demonstrateWrapper() {
    // Given
    Holder<String> holder = new Holder<>("");
    Service service = new ServiceImpl("test");
    Service serviceWithCallback = new CallbackWrapper<Service,String>(service, (s) -> holder.set(s)).wrap();

    // When
    serviceWithCallback.getData();

    // Then
    assertThat(holder.get(), is("test"));
  }

}
  class CallbackWrapper<T,R> implements InvocationHandler {
    interface Callback<R> {
      void callback(R r);
    }
    private final T service;
    private final Callback<R> callback;

    public CallbackWrapper(T service, Callback<R> callback) {
      this.service = service;
      this.callback = callback;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Object returnValue = method.invoke(service, args);
      callback.callback( (R) returnValue);
      return returnValue;
    }

    public T wrap() {
      return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), service.getClass().getInterfaces(), this);
    }

  }

  interface Service {

    String getData();

  }

  class ServiceImpl implements Service {
    private String data;

    public ServiceImpl(String data) {
      this.data = data;
    }

    public String getData() {
      return data;
    }

}
