package com.iTitus.MyMod.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;
import com.iTitus.MyMod.helper.NBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDebug extends MyItem {

	public ItemDebug() {
		super(EnumItemType.debug);
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean b) {
		list.add(StatCollector.translateToLocal("lore.debug"));
		super.addInformation(stack, player, list, b);
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		return true;
	}

	@Override
	public float func_150893_a(ItemStack stack, Block block) {
		return 10F;
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage
				.getAttributeUnlocalizedName(), new AttributeModifier(
				field_111210_e, "Weapon modifier", 1000D, 0));
		return multimap;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		player.swingItem();

		if (!world.isRemote)
			return stack;

		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;

		switch (mop.typeOfHit) {
		case ENTITY: {

			if (mop.entityHit == null)
				return stack;

			Entity e = mop.entityHit;

			NBTTagCompound nbt = new NBTTagCompound();
			e.writeToNBT(nbt);

			player.addChatMessage(new ChatComponentText(String.format(
					StatCollector.translateToLocal("message.debug.entity.0"),
					e.getCommandSenderName(), e.getEntityId(),
					NBTHelper.readNBT(nbt))));

			break;
		}

		case BLOCK: {

			if (world.getBlock(mop.blockX, mop.blockY, mop.blockZ) == null) {
				return stack;
			}

			Block b = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);

			NBTTagCompound nbt = new NBTTagCompound();
			TileEntity tile = null;
			if (b.hasTileEntity(world.getBlockMetadata(mop.blockX, mop.blockY,
					mop.blockZ))) {
				tile = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
				if (tile != null)
					tile.writeToNBT(nbt);
			}

			StringBuilder sb = new StringBuilder();

			sb.append(String.format(
					StatCollector.translateToLocal("message.debug.block.0"),
					b.getLocalizedName()));
			sb.append(String.format(
					StatCollector.translateToLocal("message.debug.block.1"),
					b.getUnlocalizedName()));
			sb.append(String.format(
					StatCollector.translateToLocal("message.debug.block.2"),
					Block.getIdFromBlock(b)));
			if (world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ) != 0)
				sb.append(String.format(StatCollector
						.translateToLocal("message.debug.block.3"), world
						.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ)));
			sb.append(String.format(StatCollector
					.translateToLocal("message.debug.block.4"),
					b.getBlockHardness(world, mop.blockX, mop.blockY,
							mop.blockZ)));
			sb.append(String.format(StatCollector
					.translateToLocal("message.debug.block.5"), b
					.getExplosionResistance(player, world, mop.blockX,
							mop.blockY, mop.blockZ, mop.blockX, mop.blockY,
							mop.blockZ)));
			if (b.slipperiness != 0.6F)
				sb.append(String.format(
						StatCollector.translateToLocal("message.debug.block.6"),
						b.slipperiness));
			if (b.hasTileEntity(world.getBlockMetadata(mop.blockX, mop.blockY,
					mop.blockZ))) {
				sb.append(String.format(
						StatCollector.translateToLocal("message.debug.block.7"),
						NBTHelper.readNBT(nbt)));
			} else {
				sb.append(String.format(
						StatCollector.translateToLocal("message.debug.block.8"),
						mop.blockX, mop.blockY, mop.blockZ));
			}

			player.addChatMessage(new ChatComponentText(sb.toString()));
			break;
		}
		case MISS: {

			player.addChatMessage(new ChatComponentText(StatCollector
					.translateToLocal("message.debug.nothing")));
		}
		default:
			break;

		}

		return stack;
	}

}
