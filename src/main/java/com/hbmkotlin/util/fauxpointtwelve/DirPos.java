package com.hbmkotlin.util.fauxpointtwelve;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.Direction;

public class DirPos extends CustomBlockPos {

    protected Direction dir;

    public DirPos(int x, int y, int z, Direction dir) {
        super(x, y, z);
        this.dir = dir;
    }

    public DirPos(BlockEntity be, Direction dir) {
        super(be);
        this.dir = dir;
    }

    public DirPos(double x, double y, double z, Direction dir) {
        super(x, y, z);
        this.dir = dir;
    }

    @Override
    public DirPos rotate(Rotation rotationIn) {
        switch (rotationIn) {
            case NONE:
            default:
                return this;
            case CLOCKWISE_90:
                return new DirPos(-this.getZ(), this.getY(), this.getX(), this.dir.rotateYClockwise());
            case CLOCKWISE_180:
                return new DirPos(-this.getX(), this.getY(), -this.getZ(), this.dir.getOpposite());
            case COUNTERCLOCKWISE_90:
                return new DirPos(this.getZ(), this.getY(), -this.getX(), this.dir.rotateYCounterclockwise());
        }
    }

    public Direction getDir() {
        return this.dir;
    }
}
