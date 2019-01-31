package quaternary.youwinbutton;

import net.minecraft.block.BlockButton;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = YouWinButton.MODID)
public class SuperWinnerAwesomeCoolEvents {
	@SubscribeEvent
	public static void rightClick(PlayerInteractEvent.RightClickBlock e) {
		World world = e.getWorld();
		if(world.isRemote) return;
		
		BlockPos clickPos = e.getPos();
		IBlockState clickState = world.getBlockState(clickPos);
		
		if(clickState.getBlock() == Blocks.STONE_BUTTON || clickState.getBlock() == Blocks.WOODEN_BUTTON) {
			EnumFacing buttonFace = clickState.getValue(BlockButton.FACING);
			BlockPos centerPos = clickPos.offset(buttonFace.getOpposite(), YWBConfig.RADIUS + 1);
			
			//center block is wrong
			if(world.getBlockState(centerPos).getBlock() != YWBConfig.MIDDLE) return;
			
			if(YWBConfig.RADIUS >= 1) {
				for(BlockPos pos : BlockPos.getAllInBoxMutable(centerPos.add(-YWBConfig.RADIUS, -YWBConfig.RADIUS, -YWBConfig.RADIUS), centerPos.add(YWBConfig.RADIUS, YWBConfig.RADIUS, YWBConfig.RADIUS))) {
					//skip the center (it's supposed to be different anyways)
					if(pos.equals(centerPos)) continue;
					
					//edge block is wrong
					if(world.getBlockState(pos).getBlock() != YWBConfig.EDGE) return;
				}
			}
			
			//multiblock is correct, so now, the magic happens
			
			MinecraftServer server = world.getMinecraftServer();
			EntityPlayer player = e.getEntityPlayer();
			String name = player.getName();
			
			//You won!!!!
			if(YWBConfig.PRIZE_CREATIVE) {
				player.setGameType(GameType.CREATIVE);
				player.capabilities.allowFlying = true;
				player.capabilities.isFlying = true;
			}
			
			if(YWBConfig.PRIZE_OP) {
				server.getPlayerList().addOp(player.getGameProfile());
			}
			
			if(YWBConfig.PRIZE_ENTITY) {
				//xd
				server.commandManager.executeCommand(server, "/tp @e[type=!player] " + name);
			}
			
			if(YWBConfig.PRIZE_SLIME) {
				for(int i = 0; i < 50; i++) {
					EntitySlime slimoo = new EntitySlime(world);
					slimoo.setPosition(player.posX + world.rand.nextFloat() - .5f, player.posY, player.posZ + world.rand.nextFloat() - .5f);
					//xd
					NBTTagCompound askdjf = slimoo.writeToNBT(new NBTTagCompound());
					askdjf.setInteger("Size", 0);
					slimoo.readFromNBT(askdjf);
					world.spawnEntity(slimoo);
				}
			}
			
			if(YWBConfig.PRIZE_FIREWORKS) {
				for(int i = 0; i < 50; i++) {
					FireworkUtilThatIStoleFromEnderCore.spawnFirework(player.getPosition(), player.world.provider.getDimension(), 6);
				}
			}
			
			if(YWBConfig.PRIZE_ITEM) {
				for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
					ItemStack stack = player.inventory.getStackInSlot(i);
					if(stack.isEmpty()) {
						player.inventory.setInventorySlotContents(i, new ItemStack(YWBConfig.THE_ITEM_U_WIN, YWBConfig.THE_ITEM_U_WIN.getItemStackLimit()));
					}
				}
				player.inventory.markDirty();
			}
			
			if(YWBConfig.PRIZE_POTION) {
				for(Potion effect : ForgeRegistries.POTIONS.getValuesCollection()) {
					player.addPotionEffect(new PotionEffect(effect, 100, 0));
				}
			}
			
			//im too lazy to actually figure out how to do this the right way and this is a shit post mod
			if(YWBConfig.PRIZE_ADVANCEMENT) {
				server.commandManager.executeCommand(server, String.format("/advancement grant %s everything", name));
			}
			//this too
			if(YWBConfig.PRIZE_TITLE) {
				server.commandManager.executeCommand(server, "/title @a clear");
				server.commandManager.executeCommand(server, "/title @a times 20 150 10");
				server.commandManager.executeCommand(server, "/title @a title {\"text\":\"" + name + "\",\"bold\":true}");
				server.commandManager.executeCommand(server, "/title @a subtitle {\"text\":\"Has WON " + YWBConfig.GAME_NAME.toUpperCase(Locale.ROOT) + "!!!!\"}");
			}
			
			if(YWBConfig.PRIZE_SOUND) { 
				server.commandManager.executeCommand(server, "/stopsound @a");
			}
			
			//EVERYONE HAS TO KNOW!!!!!!!!!!!!!!
			for(EntityPlayer asdf : server.getPlayerList().getPlayers()) {
				if(YWBConfig.PRIZE_SOUND) {
					for(int i = 0; i < 5; i++) {
						world.playSound(null, asdf.posX, asdf.posY, asdf.posZ, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.PLAYERS, 10, world.rand.nextFloat() * 1.5f + .5f);
					}
					
					try {
						for(Field f : SoundEvents.class.getDeclaredFields()) {
							if(world.rand.nextInt(10) == 0) {
								SoundEvent s = (SoundEvent) f.get(null);
								world.playSound(null, asdf.posX, asdf.posY, asdf.posZ, s, SoundCategory.PLAYERS, 1, world.rand.nextFloat() * 0.25f + 0.625f);
							}
						}
					} catch(Exception easdsad) {
						;
					}
				}
				
				if(YWBConfig.PRIZE_CHAT) {
					for(int i = 0; i < 30; i++) {
						asdf.sendMessage(new TextComponentString(String.format("%s has WON %s!!!!!!!!!!!!!!!!!!", name, YWBConfig.GAME_NAME.toUpperCase(Locale.ROOT))));
					}
				}
			}
		}
	}
}
