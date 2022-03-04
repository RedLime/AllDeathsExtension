package com.redlimerl.mcsr.alldeaths.mixin;

import com.mojang.authlib.GameProfile;
import com.redlimerl.mcsr.alldeaths.AllDeaths;
import com.redlimerl.speedrunigt.timer.InGameTimer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos blockPos, GameProfile gameProfile) {
        super(world, blockPos, gameProfile);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeathInvoke(DamageSource source, CallbackInfo ci) {
        InGameTimer timer = InGameTimer.getInstance();
        if (timer.isPlaying() && (timer.getCategory() == AllDeaths.CATEGORY_ALL_DEATHS || timer.getCategory() == AllDeaths.CATEGORY_HALF_DEATH)) {
            int deathID = AllDeaths.getDeathCode(this.getDamageTracker());
            timer.updateMoreData(deathID, 1);

            int count = 0;
            for (int i = 1; i <= 29; i++) {
                if (timer.getMoreData(i) != 0) count++;
            }

            if ((timer.getCategory() == AllDeaths.CATEGORY_ALL_DEATHS && count == 29)
                    || (timer.getCategory() == AllDeaths.CATEGORY_HALF_DEATH && count == 15)) InGameTimer.complete();
        }
    }
}
