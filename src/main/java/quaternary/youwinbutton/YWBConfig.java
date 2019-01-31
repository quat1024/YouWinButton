package quaternary.youwinbutton;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class YWBConfig {
	public static Configuration config;
	
	public static Block MIDDLE;
	public static Block EDGE;
	public static int RADIUS;
	
	public static String GAME_NAME;
	
	public static boolean PRIZE_CREATIVE;
	public static boolean PRIZE_POTION;
	public static boolean PRIZE_DIAMONDS;
	public static boolean PRIZE_ADVANCEMENT;
	public static boolean PRIZE_SOUND;
	public static boolean PRIZE_TITLE;
	public static boolean PRIZE_CHAT;
	public static boolean PRIZE_OP;
	public static boolean PRIZE_ENTITY;
	public static boolean PRIZE_SLIME;
	public static boolean PRIZE_FIREWORKS;
	
	public static void preinit(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile(), "1");
		
		MinecraftForge.EVENT_BUS.register(YWBConfig.class);
	}
	
	public static void init(FMLInitializationEvent e) {
		//Do this after preinit so blocks can be registered first
		readConfig();
	}
	
	private static void readConfig() {
		MIDDLE = getBlock(config, "middleBlock", "general", Blocks.BEACON, "The block in the center of the multiblock");
		EDGE = getBlock(config, "edgeBlock", "general", Blocks.DIAMOND_BLOCK, "The block on the edge of the multiblock");
		RADIUS = config.getInt("radius", "general", 4, 0, 127, "How many layers of the edge block you need for the multiblock. 0 will ignore it, so all you need is a button on the middle block");
		
		GAME_NAME = config.getString("gameName", "general", "Minecraft", "The name of the game. Used in a few prizes, like the title and chat prizes");
		
		PRIZE_ADVANCEMENT = config.getBoolean("advancements", "prizes", true, "Winning Minecraft gets you every advancement!!!!!!!!!!!!!!!!!!!");
		PRIZE_CHAT = config.getBoolean("chat", "prizes", true, "Everyone will know in chat that you won!!!!!!!!!!");
		PRIZE_CREATIVE = config.getBoolean("creative", "prizes", true, "You will win the BEST BalanCED OP EXPERIENCE!!!! it's balanced because the multiblock is really big and expensive!!!");
		PRIZE_DIAMONDS = config.getBoolean("diamonds", "prizes", true, "You will win an ass load of diamonds!!!!!!!!!!!!!!!!!!!!!");
		PRIZE_ENTITY = config.getBoolean("entity", "prizes", true, "You will win every entity in the world in Minecraft!!!!!!!!!!!!!!!!!!!!!!!!!");
		PRIZE_FIREWORKS = config.getBoolean("fireworks", "prizes", true, "You will win lots of FIRE WORKS!!!!!!!!!!!!!!!!!!!!!!! (uses code I took from EnderCore)");
		PRIZE_OP = config.getBoolean("op", "prizes", true, "You will win OP on the server!!!!!!!!!!!!!!!!!!!!!!");
		PRIZE_POTION = config.getBoolean("potion", "prizes", true, "You will win every potion!!!!!!!!!!!!!!!!");
		PRIZE_SLIME = config.getBoolean("slime", "prizes", true, "You will win lots of slimes!!!!!!!!!!!!!!!!!");
		PRIZE_SOUND = config.getBoolean("sound", "prizes", true, "Everyones ears will die on the server when you win!!!!!!!!!!!!");
		PRIZE_TITLE = config.getBoolean("title", "prizes", true, "Everyone will know who won Minecraft on their screen!!!!!!!!!!!!");
		
		if(config.hasChanged()) config.save();
	}
	
	private static Block getBlock(Configuration config, String name, String category, Block def, String comment) {
		String blockName = config.getString(name, category, def.getRegistryName().toString(), comment);
		ResourceLocation res = new ResourceLocation(blockName);
		
		if(ForgeRegistries.BLOCKS.containsKey(res)) {
			return ForgeRegistries.BLOCKS.getValue(res);
		} else {
			YouWinButton.LOGGER.error("[config] Can't find any block of name '{}' for config option '{{}'", res.toString(), name);
			return def;
		}
	}
	
	@SubscribeEvent
	public static void configChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equals(YouWinButton.MODID)) {
			readConfig();
		}
	}
}
