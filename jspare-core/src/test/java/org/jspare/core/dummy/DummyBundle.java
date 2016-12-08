package org.jspare.core.dummy;

import static org.jspare.core.container.Environment.registryResource;

import org.jspare.core.bootstrap.Bundle;
import org.jspare.core.container.Context;

public class DummyBundle implements Bundle {

	@Override
	public void registryComponents() {
		
		Context ctx = new Context();
		ctx.put("test", true);
		
		registryResource(ctx);

	}
}