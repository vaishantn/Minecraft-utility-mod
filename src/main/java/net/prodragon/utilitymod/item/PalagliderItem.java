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

public class PalagliderItem extends Item {

    private static final double FORWARDS_ACCELERATION = 0.0035;
    private static final double MAX_HORIZONTAL_SPEED = 0.75;
    private static final double NORMAL_FALL_SPEED = -0.12;
    private static final double DIVE_FALL_SPEED = -0.35;
    private static final double UPWARD_LOOK_FALL_SPEED = -0.06;


	public PalagliderItem(Properties properties){
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        super.inventoryTick(itemStack, level, owner, slot);
        if(!(owner instanceof Player player)) {
            return;
        }

            boolean isHeld = player.getMainHandItem() == itemStack || player.getOffhandItem() == itemStack;
            if(!isHeld || player.onGround() || player.isInWater() || player.isFallFlying()) {
                return;
            }

            Vec3 velocity = player.getDeltaMovement();

            if (velocity.y >= 0.0) {
                return;
            }

            Vec3 look = player.getLookAngle();


            Vec3 forward = new Vec3(look.x, 0.0, look.z);

            if (forward.lengthSqr() > 1.0E-4){
                forward = forward.normalize();
            }else{
                forward = Vec3.ZERO;
            }
            double x = velocity.x * 0.99 + forward.x * FORWARDS_ACCELERATION;

            double z = velocity.z * 0.99 + forward.z * FORWARDS_ACCELERATION;

            double horizontalSpeed = Math.sqrt(x * x + z * z);

            if (horizontalSpeed > MAX_HORIZONTAL_SPEED){
                double scale = MAX_HORIZONTAL_SPEED / horizontalSpeed;
            }

            double y;

            if (look.y < -0.35){
                y = Math.max(DIVE_FALL_SPEED, velocity.y - 0.025);
            } else if (look.y > 0.35){
                y = Math.max(velocity.y, UPWARD_LOOK_FALL_SPEED);
            } else {
                y = Math.max(velocity.y, NORMAL_FALL_SPEED);
            }
            player.setDeltaMovement(x, y , z);

            player.fallDistance = 0.0F;
            }
        }


