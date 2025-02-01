package com.hbmkotlin.util.fauxpointtwelve;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

/**
 * Adapted BlockPos for Fabric 1.20.1
 */
public class CustomBlockPos implements Cloneable {

    private int x;
    private int y;
    private int z;

    public CustomBlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CustomBlockPos(BlockEntity be) {
        net.minecraft.util.math.BlockPos pos = be.getPos();
        if (pos != null) {
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
        } else {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
    }

    public CustomBlockPos(double x, double y, double z) {
        this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }

    public CustomBlockPos mutate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public CustomBlockPos add(int x, int y, int z) {
        return (x == 0 && y == 0 && z == 0) ? this : new CustomBlockPos(this.x + x, this.y + y, this.z + z);
    }

    public CustomBlockPos add(double x, double y, double z) {
        return (x == 0.0D && y == 0.0D && z == 0.0D) ? this
                : new CustomBlockPos(this.x + x, this.y + y, this.z + z);
    }

    public CustomBlockPos add(CustomBlockPos pos) {
        return this.add(pos.getX(), pos.getY(), pos.getZ());
    }

    public CustomBlockPos rotate(Rotation rotation) {
        switch (rotation) {
            case NONE:
            default:
                return this;
            case CLOCKWISE_90:
                return new CustomBlockPos(-this.z, this.y, this.x);
            case CLOCKWISE_180:
                return new CustomBlockPos(-this.x, this.y, -this.z);
            case COUNTERCLOCKWISE_90:
                return new CustomBlockPos(this.z, this.y, -this.x);
        }
    }

    public CustomBlockPos offset(Direction dir) {
        return this.offset(dir, 1);
    }

    public CustomBlockPos offset(Direction dir, int distance) {
        return this.add(dir.getOffsetX() * distance, dir.getOffsetY() * distance, dir.getOffsetZ() * distance);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    @Override
    public int hashCode() {
        return (this.y + this.z * 27644437) * 27644437 + this.x;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CustomBlockPos)) return false;
        CustomBlockPos pos = (CustomBlockPos) obj;
        return this.x == pos.x && this.y == pos.y && this.z == pos.z;
    }

    @Override
    public CustomBlockPos clone() {
        try {
            return (CustomBlockPos) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public enum Rotation {
        NONE,
        CLOCKWISE_90,
        CLOCKWISE_180,
        COUNTERCLOCKWISE_90
    }
}
