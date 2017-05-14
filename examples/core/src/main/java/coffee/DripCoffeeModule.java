package coffee;

import org.jspare.core.AbstractModule;
import org.jspare.core.Provides;

class DripCoffeeModule extends AbstractModule {

  @Provides
  Heater provideHeater() {
    return new ElectricHeater();
  }
}
