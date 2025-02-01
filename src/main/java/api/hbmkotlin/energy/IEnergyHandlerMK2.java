package api.hbmkotlin.energy;

import api.hbmkotlin.block.IBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface IEnergyHandlerMK2 extends IEnergyConnectorMK2, IBlockEntity {
    public long getPower();
    public void setPower(long power);
    public long getMaxPower();

    public static final boolean particleDebug = false;

    public default Vec3d getDebugParticlePosMK2() {
        BlockEntity be = (BlockEntity) this;
        BlockPos pos = be.getPos();
        return new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
    }

    default void ProvideInfoForECMK2(NbtCompound data) {
        data.putLong(CompatEnergyControl);
    }
}
