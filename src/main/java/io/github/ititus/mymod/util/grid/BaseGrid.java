package io.github.ititus.mymod.util.grid;

import com.google.common.collect.Sets;

import java.util.*;

public class BaseGrid<M extends IGridMember> {

    public final Comparator<M> gridMemberComparator = Comparator.comparingLong(IGridMember::toLong);

    private final Set<M> members = Collections.newSetFromMap(new WeakHashMap<>());

    public Set<M> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public int getSize() {
        return members.size();
    }

    public void addMember(M member) {
        if (member != null && members.add(member)) {
            member.setGrid(this);
            onGridChange();
        }
    }

    public void removeMember(M member) {
        if (members.remove(member)) {
            if (member != null && member.getGrid() == this) {
                member.setGrid(null);
            }
            onGridChange();
        }
    }

    public <G extends BaseGrid<M>> void merge(G grid) {
        if (grid == null || grid == this) {
            return;
        }
        if (grid.getSize() > 0) {
            boolean changed = false;
            Iterator<M> iterator = grid.getMembers().iterator();
            while (iterator.hasNext()) {
                M member = iterator.next();
                if (member != null && member.valid() && members.add(member)) {
                    member.setGrid(this);
                    changed = true;
                }
            }
            if (changed) {
                onGridChange();
            }
        }
        grid.destroy();
    }

    public void destroy() {
        Set<M> oldMembers = Sets.newHashSet(members);
        Iterator<M> iterator = oldMembers.iterator();
        while (iterator.hasNext()) {
            M member = iterator.next();
            removeMember(member);
        }
    }

    public void onGridChange() {
    }

    public void onMemberUpdate(M member) {
        Set<M> oldMembers = Sets.newHashSet(members);
        Iterator<M> iterator = oldMembers.iterator();
        while (iterator.hasNext()) {
            M member_ = iterator.next();
            if (member_ == null || !member_.valid()) {
                removeMember(member_);
            }
        }
    }

}
