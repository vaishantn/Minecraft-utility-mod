package net.prodragon.utilitymod.waypoint;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record WaypointData (ResourceKey<Level> dimension, BlockPos position){
}
