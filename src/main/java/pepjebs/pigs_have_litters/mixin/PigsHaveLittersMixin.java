package pepjebs.pigs_have_litters.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

@Mixin(PigEntity.class)
public abstract class PigsHaveLittersMixin extends AnimalEntity {

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        super.breed(world, other);
        for (int i = 0; i < getPigletSpawnCount(); i++) {
            PassiveEntity passiveEntity = this.createChild(world, other);
            if (passiveEntity == null) {
                return;
            }
            passiveEntity.setBaby(true);
            passiveEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            world.spawnEntityAndPassengers(passiveEntity);
        }
    }

    @Shadow
    @Nullable
    @Override
    public PigEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Unique
    private int getPigletSpawnCount() {
        float rand = java.util.concurrent.ThreadLocalRandom.current().nextFloat();
        // 50% chance of 1 piglet, 40% chance of 2 piglets, 10% chance of 3 piglets
        if (rand < 0.5f) {
            return 1;
        } else if (rand < 0.9f) {
            return 2;
        } else {
            return 3;
        }
    }

    protected PigsHaveLittersMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean cannotBeSilenced() {
        return super.cannotBeSilenced();
    }
}
