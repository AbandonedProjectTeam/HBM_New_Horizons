package api.hbmkotlin.energy;

import net.minecraft.util.math.Direction;

public interface IEnergyConnectorMK2 {
    public default boolean canConnect(Direction dir) {
        return dir != null;
    }
}
