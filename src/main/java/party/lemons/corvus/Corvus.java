package party.lemons.corvus;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import party.lemons.corvus.proxy.IProxy;

@Mod(modid = Corvus.MODID, name = Corvus.NAME, version= Corvus.VERSION, dependencies = "required-after:lemonlib")
public class Corvus
{
    public static final String MODID = "corvus";
    public static final String NAME = "Corvus";
    public static final String VERSION = "0.0.3";

    @SidedProxy(clientSide = "party.lemons.corvus.proxy.ClientProxy", serverSide = "party.lemons.corvus.proxy.ServerProxy")
    public static IProxy PROXY;
}
