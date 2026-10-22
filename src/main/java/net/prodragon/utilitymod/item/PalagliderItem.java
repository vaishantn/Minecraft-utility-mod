package net.prodragon.utilitymod.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class PalagliderItem extends Item
{
	public PalagliderItem(Properties properties){
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if(owner instanceof Player player) {
            boolean isHeld = player.getMainHandItem() == itemStack || player.getOffhandItem() == itemStack;
            if(isHeld && !player.onGround() && player.getDeltaMovement().y<0) {
                Vec3 vec3 = player.getDeltaMovement();

                player.setDeltaMovement(vec3.x, Math.max(vec3.y, -0.15), vec3.z);

                player.fallDistance = 0.0F;
            }
        }
        super.inventoryTick(itemStack, level, owner, slot);
    }
}

