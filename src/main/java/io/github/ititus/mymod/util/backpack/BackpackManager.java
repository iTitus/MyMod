package io.github.ititus.mymod.util.backpack;

import io.github.ititus.mymod.MyMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * https://github.com/TheCBProject/EnderStorage/blob/master/src/main/java/codechicken/enderstorage/manager/EnderStorageManager.java
 */
@Mod.EventBusSubscriber(modid = MyMod.MOD_ID)
public class BackpackManager extends WorldSavedData {

    public static final String NAME = String.format("%s_%s", MyMod.MOD_ID, MyMod.BACKPACK);
    private final Map<UUID, Backpack> backpacks = new HashMap<>();
    private WeakReference<World> world = new WeakReference<>(null);

    public BackpackManager() {
        this(NAME);
    }

    public BackpackManager(String name) {
        super(name);
    }

    public static BackpackManager get(World world) {
        MapStorage storage = world.getMapStorage();
        BackpackManager instance = (BackpackManager) storage.getOrLoadData(BackpackManager.class, NAME);

        if (instance == null) {
            instance = new BackpackManager();
            instance.setWorld(world);
            storage.setData(NAME, instance);
        }

        instance.setWorld(world);
        return instance;
    }

    @SubscribeEvent
    public static void onWorldSave(WorldEvent.Save event) {
        if (!event.getWorld().isRemote) {
            //getServerInstance().save();
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        //getServerInstance().sync(event.player);
    }

    public World getWorld() {
        return world.get();
    }

    public void setWorld(World world) {
        this.world = new WeakReference<>(world);
    }

    public UUID getFree() {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        }
        while (backpacks.containsKey(uuid) || (uuid.getMostSignificantBits() == 0 && uuid.getLeastSignificantBits() == 0));
        return uuid;
    }

    public Backpack getBackpack(UUID uuid) {
        return uuid != null && (uuid.getMostSignificantBits() != 0 || uuid.getLeastSignificantBits() != 0) ? backpacks.get(uuid) : null;
    }

    public void putBackpack(UUID uuid, Backpack backpack) {
        if (uuid == null || (uuid.getMostSignificantBits() == 0 && uuid.getLeastSignificantBits() == 0)) {
            throw new IllegalArgumentException("UUID: " + uuid);
        }
        backpacks.put(uuid, backpack);
        markDirty();
    }

    public Backpack getOrCreateBackpack(UUID uuid) {
        if (uuid == null || (uuid.getMostSignificantBits() == 0 && uuid.getLeastSignificantBits() == 0)) {
            return null;
        }
        Backpack backpack = backpacks.get(uuid);
        if (backpack == null) {
            backpack = new Backpack(this);
            backpacks.put(uuid, backpack);
            markDirty();
        }
        return backpack;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        backpacks.clear();
        NBTTagList list = nbt.getTagList("backpacks", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound compound = list.getCompoundTagAt(i);
            UUID uuid = NBTUtil.getUUIDFromTag(compound.getCompoundTag("uuid"));
            if (uuid != null && (uuid.getMostSignificantBits() != 0 || uuid.getLeastSignificantBits() != 0)) {
                Backpack backpack = new Backpack(this);
                backpack.deserializeNBT(compound.getCompoundTag("backpack"));
                backpacks.put(uuid, backpack);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList list = new NBTTagList();
        backpacks.forEach((uuid, backpack) -> {
            if (uuid != null && (uuid.getMostSignificantBits() != 0 || uuid.getLeastSignificantBits() != 0)) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setTag("uuid", NBTUtil.createUUIDTag(uuid));
                compound.setTag("backpack", backpack.serializeNBT());
                list.appendTag(compound);
            }
        });
        nbt.setTag("backpacks", list);
        return nbt;
    }
}
