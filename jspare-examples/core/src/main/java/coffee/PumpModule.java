package coffee;

import org.jspare.core.AbstractModule;
import org.jspare.core.internal.Bind;

public class PumpModule extends AbstractModule {

  public PumpModule() {
  }

  @Override
  public void load() {
    context.registry(Bind.bind(Heater.class).to(ElectricHeater.class));
    context.registry(Bind.bind(Pump.class).to(Thermosiphon.class));
  }
}
