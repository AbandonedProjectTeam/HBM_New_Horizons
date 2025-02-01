package api.hbmkotlin.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockEntity {
    BlockPos getPos();
    World getWorld();
    default boolean isLoaded() {
        return getWorld().isChunkLoaded(getPos());
    }
    default BlockEntity asBlockEntity() {
        return (BlockEntity) this;
    }
}
