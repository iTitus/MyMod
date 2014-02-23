package com.iTitus.MyMod.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Multimap;
import com.iTitus.MyMod.helper.LangHelper;
import com.iTitus.MyMod.helper.NBTHelper;
import com.iTitus.MyMod.lib.LibTextures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDebug extends MyItem {

	public ItemDebug() {
		super(EnumItemType.debug);
		setMaxStackSize(1);
	}

	@Override
	public boolean putInTab() {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean b) {
		list.add(LangHelper.localize("lore.debug"));
		super.addInformation(stack, player, list, b);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isFull3D() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {

		if (world.isRemote)
			return stack;

		player.swingItem();

		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;

		switch (mop.typeOfHit) {
		case ENTITY: {

			if (mop.entityHit == null)
				return stack;

			Entity e = mop.entityHit;

			NBTTagCompound nbt = new NBTTagCompound();
			e.writeToNBT(nbt);

			player.addChatMessage(new ChatComponentText(String.format(
					LangHelper.localize("message.debug.entity.0"),
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
					LangHelper.localize("message.debug.block.0"),
					b.getLocalizedName()));
			sb.append(String.format(
					LangHelper.localize("message.debug.block.1"),
					b.getUnlocalizedName()));
			sb.append(String.format(
					LangHelper.localize("message.debug.block.2"),
					b.getIdFromBlock(b)));
			if (world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ) != 0)
				sb.append(String.format(LangHelper
						.localize("message.debug.block.3"), world
						.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ)));
			sb.append(String.format(LangHelper
					.localize("message.debug.block.4"), b.getBlockHardness(
					world, mop.blockX, mop.blockY, mop.blockZ)));
			sb.append(String.format(LangHelper
					.localize("message.debug.block.5"), b
					.getExplosionResistance(player, world, mop.blockX,
							mop.blockY, mop.blockZ, mop.blockX, mop.blockY,
							mop.blockZ)));
			if (b.slipperiness != 0.6F)
				sb.append(String.format(
						LangHelper.localize("message.debug.block.6"),
						b.slipperiness));
			if (b.hasTileEntity(world.getBlockMetadata(mop.blockX, mop.blockY,
					mop.blockZ))) {
				sb.append(String.format(
						LangHelper.localize("message.debug.block.7"),
						NBTHelper.readNBT(nbt)));
			} else {
				sb.append(String.format(
						LangHelper.localize("message.debug.block.8"),
						mop.blockX, mop.blockY, mop.blockZ));
			}

			player.addChatMessage(new ChatComponentText(sb.toString()));
			break;
		}
		case MISS: {

			player.addChatMessage(new ChatComponentText(LangHelper
					.localize("message.debug.nothing")));
		}
		default:
			break;

		}

		return stack;
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage
				.getAttributeUnlocalizedName(), new AttributeModifier(
				field_111210_e, "Weapon modifier", 1000D, 0));
		return multimap;
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		return true;
	}

	@Override
	public float func_150893_a(ItemStack stack, Block block) {
		return (block.getBlockHardness(Minecraft.getMinecraft().theWorld,
				Minecraft.getMinecraft().thePlayer.serverPosX,
				Minecraft.getMinecraft().thePlayer.serverPosY,
				Minecraft.getMinecraft().thePlayer.serverPosZ) + 1)
				* 6 * (Minecraft.getMinecraft().thePlayer.isInWater() ? 5 : 1);
	}

}
