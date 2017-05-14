package coffee;

import javax.inject.Inject;

public class CoffeeMaker {

  @Inject
  Heater heater; // Don't want to create a possibly costly heater until we need it.
  @Inject
  Pump pump;

  public void brew() {
    heater.on();
    pump.pump();
    System.out.println(" [_]P coffee! [_]P ");
    heater.off();
  }
}
