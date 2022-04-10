package com.redlimerl.mcsr.alldeaths;


import com.redlimerl.mcsr.alldeaths.mixin.accessor.DamageTrackerAccessor;
import com.redlimerl.speedrunigt.timer.category.RunCategory;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.entity.damage.DamageRecord;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;

public class AllDeaths implements ClientModInitializer {

    public static final RunCategory CATEGORY_ALL_DEATHS = new RunCategory("all_deaths", "mc_juice#All_Deaths", "All Deaths");
    public static final RunCategory CATEGORY_HALF_DEATH = new RunCategory("half_death", "mc_juice#Half_Death", "Half Death");

    @Override
    public void onInitializeClient() {

    }

    public static int getDeathCode(DamageTracker damageTracker) {
        DamageTrackerAccessor damageTrackerAccessor = (DamageTrackerAccessor) damageTracker;
        DamageRecord fallDamage = damageTrackerAccessor.invokeGetBiggestFall();
        DamageRecord recentDamage = damageTrackerAccessor.getRecentDamage().get(damageTrackerAccessor.getRecentDamage().size() - 1);
        DamageSource damageSource = recentDamage.getDamageSource();

        if (fallDamage != null && damageSource == DamageSource.FALL) {
            return fallDamage.getFallDeathSuffix() == null ?
                    8 : // Normal fall death (high)
                    9;  // Fall death, but last block you touched before reaching the ground was a ladder like blocks
        }

        if (damageSource == DamageSource.CACTUS) return 2;
        if (damageSource == DamageSource.DROWN) return 3;
        if (damageSource == DamageSource.FLY_INTO_WALL) return 4; // Elytra
        if (damageSource == DamageSource.ANVIL) return 10;
        if (damageSource == DamageSource.IN_FIRE) return 11;
        if (damageSource == DamageSource.ON_FIRE) return 12;
        if (damageSource == DamageSource.LAVA) return 14;
        if (damageSource == DamageSource.LIGHTNING_BOLT) return 15;
        if (damageSource == DamageSource.HOT_FLOOR) return 16; // Magma block
        if (damageSource == DamageSource.MAGIC) return 17; // Magic, but interaction with dispenser(non entity)
        if (damageSource == DamageSource.STARVE) return 22;
        if (damageSource == DamageSource.IN_WALL) return 23;
        if (damageSource == DamageSource.CRAMMING) return 24; // 24+ entities in one block
        if (damageSource == DamageSource.SWEET_BERRY_BUSH) return 25;
        if (damageSource == DamageSource.OUT_OF_WORLD) return 28;
        if (damageSource == DamageSource.WITHER) return 29; // Wither effect, not a wither boss

        switch (recentDamage.getDamageSource().getName()) {
            case "arrow": return 1;
            case "badRespawnPoint": return 6;
            case "fall": return 7; // Fall death, but "hit the ground too hard"
            case "fireworks": return 13;
            case "indirectMagic": return 17; // Magic, but interaction with entity
            case "mob": return 18;
            case "fireball": return 19;
            case "sting": return 20;
            case "witherSkull": return 21; // Shot by Wither skull, not explode or wither effect
            case "thorns": return 26;
            case "trident": return 27;
        }

        if (recentDamage.getDamageSource().isExplosive()) return 5; // Normal explode(TNT), so need checking after firework and bedRespawn

        return 0;
    }
}
