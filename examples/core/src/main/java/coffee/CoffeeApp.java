package coffee;

import org.jspare.core.Application;
import org.jspare.core.Modules;

import javax.inject.Inject;

@Modules(PumpModule.class)
public class CoffeeApp extends Application {
  @Inject
  CoffeeMaker coffeeMaker;

  @Override
  public void start() {
    coffeeMaker.brew();
  }

  public static void main(String[] args) {
    Application.run(CoffeeApp.class);
  }
}
