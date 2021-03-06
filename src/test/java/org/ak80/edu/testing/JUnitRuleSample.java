package org.ak80.edu.testing;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JUnitRuleSample {

  private String _string;

  @Rule
  public CustomRule rule = new CustomRule();

  @Test
  public void test() {
    _string = "passed";
  }

  private class CustomRule implements TestRule {

    public Statement apply(final Statement statement, Description description) {

      return new Statement() {
        @Override
        public void evaluate() throws Throwable {
          System.out.println("before each test");
          statement.evaluate();
          System.out.println("after each test that did not fail");
          assertThat(_string, is("passed"));
        }
      };
    }
  }

}
