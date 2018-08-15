package io.github.ititus.mymod.inventory.container;

import io.github.ititus.mymod.network.NetworkHandler;
import io.github.ititus.mymod.network.message.MessageContainerUpdate;
import io.github.ititus.mymod.tile.TileBase;
import io.github.ititus.mymod.util.network.NetworkState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBase<T extends TileBase> extends Container {

    protected final EntityPlayer player;
    protected final T tile;
    protected final NetworkState clientState;

    public ContainerBase(EntityPlayer player, T tile) {
        this.player = player;
        this.tile = tile;
        this.clientState = tile != null ? tile.getNetworkState() : null;
    }

    protected void addPlayerInventory(int x, int y) {
        InventoryPlayer inv = getPlayerInventory();
        if (inv != null) {
            for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inv, i, 8 + i * 18, y + 58));
            }
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 9; col++) {
                    addSlotToContainer(new Slot(inv, 9 + col + row * 9, x + col * 18, y + row * 18));
                }
            }
        }
    }

    public T getTile() {
        return tile;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public InventoryPlayer getPlayerInventory() {
        return player != null ? player.inventory : null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile == null || tile.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        Slot slot = inventorySlots.get(slotNumber);
        ItemStack stack = slot.getStack();

        if (!stack.isEmpty()) {
            if (slotNumber < player.inventory.mainInventory.size()) {
                if (!mergeItemStack(stack, player.inventory.mainInventory.size(), inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!mergeItemStack(stack, 0, 9, false) && !mergeItemStack(stack, 9, player.inventory.mainInventory.size(), false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.getCount() <= 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        detectAndSendChanges();
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (tile != null && clientState != null) {
            NetworkState serverState = tile.getNetworkState();
            if (serverState != null) {
                tile.updateNetworkState(serverState);
                if (!clientState.equals(serverState)) {
                    for (IContainerListener listener : listeners) {
                        if (listener instanceof EntityPlayerMP) {
                            NetworkHandler.INSTANCE.sendTo(new MessageContainerUpdate(tile.getPos(), serverState), (EntityPlayerMP) listener);
                        }
                    }
                    clientState.set(serverState);
                }
            }
        }
    }

}
