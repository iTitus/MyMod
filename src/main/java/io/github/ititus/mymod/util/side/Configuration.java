package io.github.ititus.mymod.util.side;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.github.ititus.mymod.util.TripleBool;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class Configuration implements INBTSerializable<NBTTagCompound> {

    private final TIntObjectMap<Boolean> inputSlots, outputSlots;
    private EnumConfigurationMode mode;

    public Configuration() {
        this.inputSlots = new TIntObjectHashMap<>();
        this.outputSlots = new TIntObjectHashMap<>();
        reset();
    }

    public static void toBytes(ByteBuf buf, Configuration cfg) {
        buf.writeInt(cfg.mode.getIndex());

        buf.writeInt(cfg.inputSlots.size());
        cfg.inputSlots.forEachEntry((slot, state) -> {
            buf.writeInt(slot);
            buf.writeBoolean(state);
            return true;
        });

        buf.writeInt(cfg.outputSlots.size());
        cfg.outputSlots.forEachEntry((slot, state) -> {
            buf.writeInt(slot);
            buf.writeBoolean(state);
            return true;
        });
    }

    public static Configuration fromBytes(ByteBuf buf) {
        Configuration cfg = new Configuration();
        cfg.mode = EnumConfigurationMode.get(buf.readInt());

        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            cfg.inputSlots.put(buf.readInt(), buf.readBoolean());
        }

        size = buf.readInt();
        for (int i = 0; i < size; i++) {
            cfg.outputSlots.put(buf.readInt(), buf.readBoolean());
        }

        return cfg;
    }

    public EnumConfigurationMode getMode() {
        return mode;
    }

    public void setMode(EnumConfigurationMode mode) {
        this.mode = mode;
    }

    public void reset() {
        mode = EnumConfigurationMode.NONE;
        inputSlots.clear();
        outputSlots.clear();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("mode", mode.getIndex());

        NBTTagList tagList = new NBTTagList();
        inputSlots.forEachEntry((slot, state) -> {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("slot", slot);
            tag.setBoolean("state", state);
            return true;
        });
        compound.setTag("inputSlots", tagList);

        tagList = new NBTTagList();
        outputSlots.forEachEntry((slot, state) -> {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("slot", slot);
            tag.setBoolean("state", state);
            return true;
        });
        compound.setTag("outputSlots", tagList);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        mode = EnumConfigurationMode.get(compound.getInteger("mode"));

        inputSlots.clear();
        NBTTagList tagList = compound.getTagList("inputSlots", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            inputSlots.put(tag.getInteger("slot"), tag.getBoolean("state"));
        }

        outputSlots.clear();
        tagList = compound.getTagList("outputSlots", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            outputSlots.put(tag.getInteger("slot"), tag.getBoolean("state"));
        }
    }

    public void set(Configuration cfg) {
        mode = cfg.mode;
        inputSlots.clear();
        inputSlots.putAll(cfg.inputSlots);
        outputSlots.clear();
        outputSlots.putAll(cfg.outputSlots);
    }

    public TripleBool getInputSlotState(int slot) {
        return inputSlots.containsKey(slot) ? TripleBool.get(inputSlots.get(slot)) : TripleBool.DEFAULT;
    }

    public TripleBool getOutputSlotState(int slot) {
        return outputSlots.containsKey(slot) ? TripleBool.get(outputSlots.get(slot)) : TripleBool.DEFAULT;
    }

    public void setInputSlotState(int slot, TripleBool state) {
        if (state == TripleBool.DEFAULT) {
            inputSlots.remove(slot);
        } else {
            inputSlots.put(slot, state.get());
        }
    }

    public void setOutputSlotState(int slot, TripleBool state) {
        if (state == TripleBool.DEFAULT) {
            outputSlots.remove(slot);
        } else {
            outputSlots.put(slot, state.get());
        }
    }
}
