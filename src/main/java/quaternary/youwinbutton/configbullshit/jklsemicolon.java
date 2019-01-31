package quaternary.youwinbutton.configbullshit;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import quaternary.youwinbutton.YWBConfig;
import quaternary.youwinbutton.YouWinButton;

import java.util.stream.Collectors;

public class jklsemicolon extends GuiConfig {
	public jklsemicolon(GuiScreen parentScreen) {
		super(parentScreen, YWBConfig.config.getCategoryNames().stream().filter(name -> !YWBConfig.config.getCategory(name).isChild()).map(name -> new ConfigElement(YWBConfig.config.getCategory(name).setLanguageKey(YouWinButton.MODID + ".config." + name))).collect(Collectors.toList()),
		YouWinButton.MODID, false, false, YouWinButton.NAME);
	}
}
