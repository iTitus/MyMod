package io.github.ititus.mymod.util.grid;

import net.minecraft.util.ITickable;

public interface IGridMember<G extends BaseGrid> extends ITickable {

    void setGrid(G grid);

    G getGrid();

    boolean valid();

    long toLong();

    void checkGrids();

    @Override
    default void update() {
        G grid = getGrid();
        if (grid == null) {
            checkGrids();
        }
        if (grid != null) {
            grid.onMemberUpdate(this);
        }
    }


}
