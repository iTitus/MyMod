package io.github.ititus.mymod.compat.hwyla;

import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class MyModHwylaPlugin implements IWailaPlugin {

    @Override
    public void register(IWailaRegistrar registrar) {
        System.out.println("Loading MyModHwylaPlugin");
    }
}
