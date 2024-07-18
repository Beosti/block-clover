package com.yuanno.blockclover.init;

import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;

import java.util.function.Predicate;

public class ModEntityPredicates {

    public static Predicate<Entity> getEnemyFactions(LivingEntity entity) {

        if(entity == null) {
            return Predicates.alwaysTrue();
        }

        return target -> {

            if (!(target instanceof LivingEntity)) {
                return false;
            }

            if (entity == target) {
                return false;
            }

            // Mounted entities are considered allies because of the amount of fuck ups that it can cause
            if(target.getVehicle() != null && target.getVehicle().equals(entity)) {
                return false;
            }

            boolean isSpectating = !EntityPredicates.NO_SPECTATORS.test(target);
            if (isSpectating) {
                return false;
            }

            return true;
        };
    }
}
