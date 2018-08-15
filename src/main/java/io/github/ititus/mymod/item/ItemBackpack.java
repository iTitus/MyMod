package io.github.ititus.mymod.item;

import io.github.ititus.mymod.MyMod;
import io.github.ititus.mymod.handler.GuiHandler;
import io.github.ititus.mymod.util.backpack.Backpack;
import io.github.ititus.mymod.util.backpack.BackpackManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class ItemBackpack extends ItemBase {

    public ItemBackpack() {
        super(MyMod.BACKPACK);
        setMaxStackSize(1);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ICapabilityProvider() {

            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
                if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    if (nbt != null && nbt.hasKey("backpack", Constants.NBT.TAG_COMPOUND)) {
                        NBTTagCompound compound = nbt.getCompoundTag("backpack");
                        UUID uuid = NBTUtil.getUUIDFromTag(compound.getCompoundTag("uuid"));
                        if (uuid != null && (uuid.getMostSignificantBits() != 0 || uuid.getLeastSignificantBits() != 0)) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Nullable
            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
                if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    if (nbt != null && nbt.hasKey("backpack", Constants.NBT.TAG_COMPOUND)) {
                        NBTTagCompound compound = nbt.getCompoundTag("backpack");
                        UUID uuid = NBTUtil.getUUIDFromTag(compound.getCompoundTag("uuid"));
                        if (uuid != null && (uuid.getMostSignificantBits() != 0 || uuid.getLeastSignificantBits() != 0)) {
                            IItemHandler handler = null;
                            if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
                                World world = null;
                                handler = BackpackManager.get(world).getBackpack(uuid).getItemHandler();
                            } else {

                            }

                            //FMLServerHandler.instance().getServer().getEntityWorld();
                            //FMLClientHandler.instance().getWorldClient();
                            //FMLClientHandler.instance().getServer();
                            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler);
                        }
                    }
                }
                return null;
            }
        };
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!player.isSneaking()) {
            ItemStack stack = player.getHeldItem(hand);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemBackpack) {
                if (!world.isRemote) {
                    UUID uuid = null;
                    BackpackManager backpackManager = BackpackManager.get(world);

                    NBTTagCompound nbt = stack.getTagCompound(), compound = null;
                    if (nbt != null && nbt.hasKey("backpack", Constants.NBT.TAG_COMPOUND)) {
                        compound = nbt.getCompoundTag("backpack");
                        uuid = NBTUtil.getUUIDFromTag(compound.getCompoundTag("uuid"));
                    }

                    if (uuid == null || (uuid.getMostSignificantBits() == 0 && uuid.getLeastSignificantBits() == 0)) {
                        uuid = backpackManager.getFree();
                        if (nbt == null) {
                            nbt = new NBTTagCompound();
                            stack.setTagCompound(nbt);
                        }
                        if (compound == null) {
                            compound = new NBTTagCompound();
                            nbt.setTag("backpack", compound);
                        }
                        compound.setTag("uuid", NBTUtil.createUUIDTag(uuid));
                    }

                    Backpack backpack = backpackManager.getBackpack(uuid);
                    if (backpack == null) {
                        backpack = new Backpack(backpackManager);
                        backpackManager.putBackpack(uuid, backpack);
                    }

                    player.openGui(MyMod.instance, GuiHandler.BACKPACK, world, hand == EnumHand.MAIN_HAND ? 0 : 1, 0, 0);
                }
                return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
            }
        }
        return super.onItemRightClick(world, player, hand);
    }
}
