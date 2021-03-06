package org.jspare.unit.ext.junit;

import org.apache.commons.lang.StringUtils;
import org.jspare.unit.mock.InjectMocks;
import org.jspare.unit.mock.Mock;
import org.jspare.unit.mock.Mocker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by paulo.ferreira on 23/06/2017.
 */
@RunWith(JspareUnitRunner.class)
public class MockerTest {

  @Mock
  @InjectMocks
  FooComponent fooComponentMocked;

  @Test
  public void mockedInterfaceTest() {

    Mocker.whenReturn(fooComponentMocked, "get", "teste");
    Assert.assertNotNull(fooComponentMocked);
    Assert.assertEquals("teste", fooComponentMocked.get());
    Assert.assertTrue(StringUtils.isNotEmpty(fooComponentMocked.get()));
  }

  @Test
  public void mockedInterfaceInlineTest() {

    FooComponent fooComponentMocked = Mocker.createProxy(FooComponent.class);
    Mocker.whenReturn(fooComponentMocked, "get", "teste_inline");
    Assert.assertNotNull(fooComponentMocked);
    Assert.assertEquals("teste_inline", fooComponentMocked.get());
  }

  @Test
  public void mockedExecution(){

    FooComponent fooComponentMocked = Mocker.createProxy(FooComponent.class);
    Mocker.whenReturn(fooComponentMocked, "get", (args) -> "supplied");
    Assert.assertNotNull(fooComponentMocked);
    Assert.assertEquals("supplied", fooComponentMocked.get());
  }

  interface FooComponent {
    String get();
  }
}
