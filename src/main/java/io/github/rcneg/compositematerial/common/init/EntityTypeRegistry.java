package io.github.rcneg.compositematerial.common.init;

import io.github.rcneg.compositematerial.CompositeMaterial;
import io.github.rcneg.compositematerial.client.render.DungeonZombieRenderer;
import io.github.rcneg.compositematerial.common.entities.DungeonZombie;
import io.github.rcneg.compositematerial.common.entities.projectiles.AmethystWandProjectile;
import io.github.rcneg.compositematerial.common.entities.Battlay;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Vex;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = CompositeMaterial.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CompositeMaterial.MODID);

    public static final RegistryObject<EntityType<AmethystWandProjectile>> AMETHYST_WAND_PROJECTILE = throwableItem("amethyst_wand_projectile", AmethystWandProjectile::new);

    public static final RegistryObject<EntityType<Battlay>> BATTLAY = register("battlay",EntityType.Builder.of(Battlay::new, MobCategory.CREATURE).fireImmune().sized(0.4F, 0.8F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<DungeonZombie>> DUNGEON_ZOMBIE = register("dungeon_zombie",EntityType.Builder.of(DungeonZombie::new, MobCategory.CREATURE).sized(0.6F, 1.95F).clientTrackingRange(8));


    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
        return ENTITY_TYPES.register(name, () -> entityTypeBuilder.build(name));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> throwableItem(String name, EntityType.EntityFactory<T> factory) {
        return ENTITY_TYPES.register(name, () -> (EntityType.Builder.of(factory, MobCategory.MISC).sized(0.25F, 0.25F)
                .clientTrackingRange(4).updateInterval(10).build(name)));
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent e) {
        e.put(BATTLAY.get(), Battlay.createAttributes().build());
        e.put(DUNGEON_ZOMBIE.get(), DungeonZombie.createAttributes().build());
    }
}
