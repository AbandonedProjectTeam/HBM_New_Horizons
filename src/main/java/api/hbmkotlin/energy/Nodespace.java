package api.hbmkotlin.energy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.hbmkotlin.interfaces.NotableComments;
import com.hbmkotlin.util.fauxpointtwelve.CustomBlockPos;
import com.hbmkotlin.util.fauxpointtwelve.DirPos;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public class Nodespace implements ModInitializer {

    /** Contains all "NodeWorld" instances, i.e. lists of nodes existing per world */
    public static HashMap<ServerWorld, NodeWorld> worlds = new HashMap<>();
    public static Set<PowerNetMK2> activePowerNets = new HashSet<>();

    @Override
    public void onInitialize() {
        // Подписка на событие старта сервера
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            // Действия при старте сервера
        });
    }

    public static PowerNode getNode(ServerWorld world, int x, int y, int z) {
        NodeWorld nodeWorld = worlds.get(world);
        if (nodeWorld != null) {
            return nodeWorld.nodes.get(new CustomBlockPos(x, y, z));
        }
        return null;
    }

    public static void createNode(ServerWorld world, PowerNode node) {
        NodeWorld nodeWorld = worlds.get(world);
        if (nodeWorld == null) {
            nodeWorld = new NodeWorld();
            worlds.put(world, nodeWorld);
        }
        nodeWorld.pushNode(node);
    }

    public static void destroyNode(ServerWorld world, int x, int y, int z) {
        PowerNode node = getNode(world, x, y, z);
        if (node != null) {
            worlds.get(world).popNode(node);
        }
    }

    /** Goes over each node and manages connections */
    public static void updateNodespace() {
        MinecraftServer server = getMinecraftServer();

        for (ServerWorld world : server.getWorlds()) {
            NodeWorld nodes = worlds.get(world);

            if (nodes == null)
                continue;

            for (Entry<CustomBlockPos, PowerNode> entry : nodes.nodes.entrySet()) {
                PowerNode node = entry.getValue();
                if (!node.hasValidNet() || node.recentlyChanged) {
                    checkNodeConnection(world, node);
                    node.recentlyChanged = false;
                }
            }
        }

        updatePowerNets();
    }

    private static void updatePowerNets() {
        for (PowerNetMK2 net : activePowerNets) net.resetEnergyTracker();  // Сброс перед распределением
        for (PowerNetMK2 net : activePowerNets) net.transferPower();
    }

    /** Goes over each connection point of the given node, tries to find neighbor nodes and to join networks with them */
    private static void checkNodeConnection(ServerWorld world, PowerNode node) {
        for (DirPos con : node.connections) {
            PowerNode conNode = getNode(world, con.getX(), con.getY(), con.getZ());

            if (conNode != null) {
                if (conNode.hasValidNet() && conNode.net == node.net) continue;

                if (checkConnection(conNode, con, false)) {
                    connectToNode(node, conNode);
                }
            }
        }

        if (node.net == null || !node.net.isValid()) {
            new PowerNetMK2().joinLink(node);
        }
    }

    public static boolean checkConnection(PowerNode connectsTo, DirPos connectFrom, boolean skipSideCheck) {
        for (DirPos revCon : connectsTo.connections) {
            if (revCon.getX() - revCon.getDir().getOffsetX() == connectFrom.getX() &&
                    revCon.getY() - revCon.getDir().getOffsetY() == connectFrom.getY() &&
                    revCon.getZ() - revCon.getDir().getOffsetZ() == connectFrom.getZ() &&
                    (revCon.getDir().getOpposite() == connectFrom.getDir() || skipSideCheck)) {
                return true;
            }
        }
        return false;
    }

    /** Links two nodes with different or potentially no networks */
    private static void connectToNode(PowerNode origin, PowerNode connection) {
        if (origin.hasValidNet() && connection.hasValidNet()) {
            if (origin.net.links.size() > connection.net.links.size()) {
                origin.net.joinNetworks(connection.net);
            } else {
                connection.net.joinNetworks(origin.net);
            }
        } else if (!origin.hasValidNet() && connection.hasValidNet()) {
            connection.net.joinLink(origin);
        } else if (origin.hasValidNet()) {
            origin.net.joinLink(connection);
        }
    }

    public static class NodeWorld {
        public HashMap<CustomBlockPos, PowerNode> nodes = new HashMap<>();

        public void pushNode(PowerNode node) {
            for (CustomBlockPos pos : node.positions) {
                nodes.put(pos, node);
            }
        }

        public void popNode(PowerNode node) {
            if (node.net != null) node.net.destroy();
            for (CustomBlockPos pos : node.positions) {
                nodes.remove(pos);
                node.expired = true;
            }
        }

        public void popNode(CustomBlockPos pos) {
            PowerNode node = nodes.get(pos);
            if (node != null) popNode(node);
        }
    }

    @NotableComments
    public static class PowerNode {
        public CustomBlockPos[] positions;
        public DirPos[] connections;
        public PowerNetMK2 net;
        public boolean expired = false;
        public boolean recentlyChanged = true;

        public PowerNode(CustomBlockPos... positions) {
            this.positions = positions;
        }

        public PowerNode setConnections(DirPos... connections) {
            this.connections = connections;
            return this;
        }

        public boolean hasValidNet() {
            return this.net != null && this.net.isValid();
        }

        public void setNet(PowerNetMK2 net) {
            this.net = net;
            this.recentlyChanged = true;
        }
    }

    private static MinecraftServer getMinecraftServer() {
        return net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.getServer();
    }
}
