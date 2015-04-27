package org.ak80.edu.testing;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

public class JUnitSamples {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();


  @Rule
  public TestName name = new TestName();

  @BeforeClass
  public static void executedBeforeFirstTests() {
  }

  @Before
  public void executedBeforeEachTest() {
  }

  @After
  public void executedAfterEachTest() {
  }

  @AfterClass
  public static void executedAfterLastTest() {
  }


  @Test
  public void expectExceptionWithRule() {
    expectedException.expect(IndexOutOfBoundsException.class);
    expectedException.expectMessage("Index: 0, Size: 0");
    new ArrayList<String>().get(0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void expectExceptionWithAnnotation() {
    new ArrayList<String>().get(0);
  }

  @Test(timeout = 1000)
  public void doATestAndTimeoutAfter1000Milliseconds() {
  }

  @Test
  public void failedAssumptionAbortsLikeIgnoreButDoesNotFail() {
    assumeThat("foo", is("bar"));
    fail();
  }

  @Ignore
  @Test
  public void thisTestWouldFailButIsIgnored() {
    fail();
  }

  @Test
  public void filesAndFoldersTemporaryFolderAreDeletedAfterTest() throws IOException {
    File file = folder.newFile("file");
    File newFolder = folder.newFolder("folder");
  }

  @Test
  public void getMethodName() {
    assertThat(name.getMethodName(), is("getMethodName"));
  }


}

