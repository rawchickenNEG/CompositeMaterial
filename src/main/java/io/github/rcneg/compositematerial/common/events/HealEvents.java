package io.github.rcneg.compositematerial.common.events;

import io.github.rcneg.compositematerial.common.helper.EntityHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.init.PotionEffectRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class HealEvents {
    //地牢钢盾吸取回复
    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event){
        LivingEntity entity = event.getEntity();
        Vec3 center = EntityHelper.getVec3(entity);
        AABB aabb = new AABB(center, center).inflate(16);
        List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, aabb);
        for (LivingEntity target : entities) {
            Item item = ItemRegistry.DUNGEON_SHIELD.get();
            if (target instanceof Player player
                    && (player.getItemBySlot(EquipmentSlot.MAINHAND).is(item)
                    || player.getItemBySlot(EquipmentSlot.OFFHAND).is(item))
                    && player.isUsingItem()
                    && player.getUseItem().is(item)
                    && !(entity instanceof Player player1
                    && (player1.getItemBySlot(EquipmentSlot.MAINHAND).is(item)
                    || player1.getItemBySlot(EquipmentSlot.OFFHAND).is(item))
                    && player1.isUsingItem()
                    && player1.getUseItem().is(item))){
                player.heal(event.getAmount());
                entity.playSound(SoundEvents.FIRE_EXTINGUISH,0.1F , 0.8F);
                event.setCanceled(true);
            }
        }
        if(entity.hasEffect(PotionEffectRegistry.DEGENERATION.get())){
            entity.playSound(SoundEvents.FIRE_EXTINGUISH,0.1F , 0.8F);
            event.setCanceled(true);
        }
    }
}
