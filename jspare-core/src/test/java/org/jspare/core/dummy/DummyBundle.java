package org.jspare.core.dummy;

import org.jspare.core.bootstrap.Bundle;
import org.jspare.core.container.Context;

import static org.jspare.core.container.Environment.*;

public class DummyBundle implements Bundle {

    @Override
    public void registryComponents() {

        Context ctx = new Context();
        ctx.put("test", true);

        registryResource(ctx);

    }
}