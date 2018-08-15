package io.github.ititus.mymod.util.side;

import com.google.common.collect.Maps;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import io.github.ititus.mymod.util.TripleBool;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

public class SideConfiguration implements INBTSerializable<NBTTagCompound> {

    private final TIntSet inputSlots, outputSlots;
    private final Map<EnumFacing, Configuration> configuration = Maps.newEnumMap(EnumFacing.class);
    private boolean autoExport, autoImport;

    public SideConfiguration() {
        this.inputSlots = new TIntHashSet();
        this.outputSlots = new TIntHashSet();
        for (EnumFacing facing : EnumFacing.VALUES) {
            this.configuration.put(facing, new Configuration());
        }
        reset();
    }

    public static void toBytes(ByteBuf buf, SideConfiguration cfg) {
        if (cfg == null) {
            buf.writeBoolean(false);
            return;
        }
        buf.writeBoolean(true);
        buf.writeBoolean(cfg.autoExport);
        buf.writeBoolean(cfg.autoImport);

        buf.writeInt(cfg.inputSlots.size());
        cfg.inputSlots.forEach(slot -> {
            buf.writeInt(slot);
            return true;
        });

        buf.writeInt(cfg.outputSlots.size());
        cfg.outputSlots.forEach(slot -> {
            buf.writeInt(slot);
            return true;
        });

        for (EnumFacing facing : EnumFacing.VALUES) {
            Configuration.toBytes(buf, cfg.get(facing));
        }
    }

    public static SideConfiguration fromBytes(ByteBuf buf) {
        if (buf.readBoolean()) {
            SideConfiguration cfg = new SideConfiguration();
            cfg.autoExport = buf.readBoolean();
            cfg.autoImport = buf.readBoolean();

            int size = buf.readInt();
            for (int i = 0; i < size; i++) {
                cfg.inputSlots.add(buf.readInt());
            }

            size = buf.readInt();
            for (int i = 0; i < size; i++) {
                cfg.outputSlots.add(buf.readInt());
            }

            for (EnumFacing facing : EnumFacing.VALUES) {
                cfg.configuration.put(facing, Configuration.fromBytes(buf));
            }
            return cfg;
        }
        return null;
    }

    public boolean isAutoExport() {
        return autoExport;
    }

    public void setAutoExport(boolean autoExport) {
        this.autoExport = autoExport;
    }

    public boolean isAutoImport() {
        return autoImport;
    }

    public void setAutoImport(boolean autoImport) {
        this.autoImport = autoImport;
    }

    public Configuration get(EnumFacing facing) {
        return configuration.get(facing);
    }

    public TripleBool getInputSlotState(EnumFacing facing, int slot) {
        if (facing == null) {
            return inputSlots.contains(slot) ? TripleBool.FALSE : TripleBool.TRUE;
        }
        return get(facing).getInputSlotState(slot);
    }

    public TripleBool getOutputSlotState(EnumFacing facing, int slot) {
        if (facing == null) {
            return outputSlots.contains(slot) ? TripleBool.FALSE : TripleBool.TRUE;
        }
        return get(facing).getOutputSlotState(slot);
    }

    public void setInputSlotState(EnumFacing facing, int slot, TripleBool state) {
        if (facing == null) {
            if (state == TripleBool.FALSE) {
                inputSlots.add(slot);
            } else if (state == TripleBool.TRUE) {
                inputSlots.remove(slot);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            get(facing).setInputSlotState(slot, state);
        }
    }

    public void setOutputSlotState(EnumFacing facing, int slot, TripleBool state) {
        if (facing == null) {
            if (state == TripleBool.FALSE) {
                outputSlots.add(slot);
            } else if (state == TripleBool.TRUE) {
                outputSlots.remove(slot);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            get(facing).setOutputSlotState(slot, state);
        }
    }

    public boolean canExtract(EnumFacing facing, int slot) {
        if (facing == null) {
            return getOutputSlotState(facing, slot).get();
        }

        TripleBool state = getOutputSlotState(facing, slot);
        if (state == TripleBool.DEFAULT) {
            return canExtract(null, slot);
        }
        return state.get();
    }

    public boolean canInsert(EnumFacing facing, int slot) {
        if (facing == null) {
            return getInputSlotState(facing, slot).get();
        }

        TripleBool state = getInputSlotState(facing, slot);
        if (state == TripleBool.DEFAULT) {
            return canInsert(null, slot);
        }
        return state.get();
    }

    public void reset() {
        autoExport = false;
        autoImport = false;
        inputSlots.clear();
        outputSlots.clear();
        for (EnumFacing facing : EnumFacing.VALUES) {
            get(facing).reset();
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("autoExport", autoExport);
        compound.setBoolean("autoImport", autoImport);
        compound.setIntArray("inputSlots", inputSlots.toArray(new int[inputSlots.size()]));
        compound.setIntArray("outputSlots", outputSlots.toArray(new int[outputSlots.size()]));
        NBTTagList tagList = new NBTTagList();
        for (EnumFacing facing : EnumFacing.VALUES) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("facing", facing.getIndex());
            tag.setTag("configuration", get(facing).serializeNBT());
            tagList.appendTag(tag);
        }
        compound.setTag("configurations", tagList);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        autoExport = compound.getBoolean("autoExport");
        autoImport = compound.getBoolean("autoImport");
        inputSlots.clear();
        inputSlots.addAll(compound.getIntArray("inputSlots"));
        outputSlots.clear();
        outputSlots.addAll(compound.getIntArray("outputSlots"));
        NBTTagList tagList = compound.getTagList("configurations", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            get(EnumFacing.getFront(tag.getInteger("facing"))).deserializeNBT(tag.getCompoundTag("configuration"));
        }
    }

    public void set(SideConfiguration cfg) {
        autoExport = cfg.autoExport;
        autoImport = cfg.autoImport;
        inputSlots.clear();
        inputSlots.addAll(cfg.inputSlots);
        outputSlots.clear();
        outputSlots.addAll(cfg.outputSlots);
        for (EnumFacing facing : EnumFacing.VALUES) {
            get(facing).set(cfg.get(facing));
        }
    }

    public SideConfiguration copy() {
        SideConfiguration cfg = new SideConfiguration();
        cfg.set(this);
        return cfg;
    }
}
