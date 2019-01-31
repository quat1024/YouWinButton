package quaternary.youwinbutton;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
	modid = YouWinButton.MODID,
	name = YouWinButton.NAME,
	version = YouWinButton.VERSION,
	guiFactory = "quaternary.youwinbutton.configbullshit.asdf"
)
public class YouWinButton {
	public static final String MODID = "youwinbutton";
	public static final String NAME = "You Win Button";
	public static final String VERSION = "GRADLE:VERSION";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		YWBConfig.preinit(e);
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent e) {
		YWBConfig.init(e);
	}
}
