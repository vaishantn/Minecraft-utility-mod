package net.prodragon.utilitymod.waypoint;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.prodragon.utilitymod.UtilityMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class WaypointSavedData extends SavedData {
    private static final Codec<UUID> UUID_CODEC =
            Codec.STRING.comapFlatMap(
                    value -> {
                        try {
                            return DataResult.success(UUID.fromString(value));
                        } catch (IllegalArgumentException exception) {
                            return DataResult.error(
                                    () -> "Invalid player UUID: " + value,
                                    null
                            );
                        }
                    },
                    UUID::toString
            );

    private static final Codec<WaypointData> WAYPOINT_CODEC = RecordCodecBuilder.create(
            instance ->
                    instance.group(
                            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(WaypointData::dimension),
                            BlockPos.CODEC.fieldOf("position").forGetter(WaypointData::position)
                    ).apply(instance, WaypointData::new)
    );

    private static final Codec<WaypointSavedData> CODEC = Codec.unboundedMap(UUID_CODEC, WAYPOINT_CODEC).xmap(WaypointSavedData::new, savedData -> savedData.waypoints);

    public static final SavedDataType<WaypointSavedData> TYPE = new SavedDataType<>(UtilityMod.id("waypoints"), WaypointSavedData::new, CODEC, DataFixTypes.LEVEL);

    private final Map<UUID, WaypointData> waypoints;

    public WaypointSavedData() {
        this.waypoints = new HashMap<>();
    }

    private WaypointSavedData(Map<UUID, WaypointData> savedWaypoints) {
        this.waypoints = new HashMap<>(savedWaypoints);
    }

    public static WaypointSavedData get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(TYPE);
    }

    public void setWaypoint(UUID id, ResourceKey<Level> dimension, BlockPos pos) {
        waypoints.put(id, new WaypointData(dimension, pos.immutable()));
        setDirty();
    }

    public Optional<WaypointData> getWaypoint(UUID id) {
        return Optional.ofNullable(waypoints.get(id));
    }

    public void clearWaypoint(UUID id) {
        if(waypoints.remove(id) != null) {
            setDirty();
        }
    }

    public boolean hasWaypoint(UUID id) {
        return waypoints.containsKey(id);
    }
}
