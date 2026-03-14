package io.github.rcneg.compositematerial.common.mixin;

import com.mojang.logging.LogUtils;
import io.github.rcneg.compositematerial.CompositeMaterial;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;
import java.util.UUID;

@Mixin(Raid.class)
public abstract class RaidMixin {
    @Shadow
    @Final
    private Set<UUID> heroesOfTheVillage;
    @Shadow
    private int postRaidTicks;
    @Shadow
    @Final
    private ServerLevel level;
    @Shadow
    public abstract boolean isVictory();
    @Shadow public abstract int getBadOmenLevel();

    @Unique
    private boolean cm$rewarded = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void cm$giveRaidWinRewards(CallbackInfo ci) {
        if (!this.isVictory()) {
            cm$rewarded = false;
            return;
        }

        if (cm$rewarded) return;
        cm$rewarded = true;

        for (UUID uuid : this.heroesOfTheVillage) {
            ServerPlayer player = this.level.getServer().getPlayerList().getPlayer(uuid);
            if (player == null) continue;

            int heroLevel = Math.max(0, this.getBadOmenLevel() - 2);

            ItemStack reward = new ItemStack(ItemRegistry.ENLIGHTENED_TOTEM.get(), heroLevel);
            if (reward.isEmpty()) continue;

            if (!player.getInventory().add(reward)){
                player.drop(reward, false);
            }
        }
    }
}