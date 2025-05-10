package com.bajookie.echoes_of_the_elders.entity;

import com.bajookie.echoes_of_the_elders.entity.custom.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModEntities {

    public static final EntityType<MonolookEntity> MONOLOOK_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "monolook_entity"),
            FabricEntityTypeBuilder.<MonolookEntity>create(SpawnGroup.CREATURE, MonolookEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(8).build());

    public static final EntityType<SecondSunProjectileEntity> SECOND_SUN_PROJECTILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "second_sun_entity"),
            FabricEntityTypeBuilder.<SecondSunProjectileEntity>create(SpawnGroup.CREATURE, SecondSunProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

    public static final EntityType<OrbOfLightningProjectileEntity> CHAIN_LIGHTNING_PROJECTILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "chain_lightning_entity"),
            FabricEntityTypeBuilder.<OrbOfLightningProjectileEntity>create(SpawnGroup.CREATURE, OrbOfLightningProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

    public static final EntityType<IcicleProjectile> ICICLE_PROJECTILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "icicle_entity"),
            FabricEntityTypeBuilder.<IcicleProjectile>create(SpawnGroup.CREATURE, IcicleProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<VacuumProjectileEntity> VACUUM_PROJECTILE_ENTITY_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "vacuum_projectile_entity"),
            FabricEntityTypeBuilder.<VacuumProjectileEntity>create(SpawnGroup.CREATURE, VacuumProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<StarArrowProjectile> STAR_ARROW_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "star_arrow_entity"),
            FabricEntityTypeBuilder.<StarArrowProjectile>create(SpawnGroup.CREATURE, StarArrowProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<GaleArrowProjectile> GALE_ARROW_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "gale_arrow_entity"),
            FabricEntityTypeBuilder.<GaleArrowProjectile>create(SpawnGroup.CREATURE, GaleArrowProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<SkyWardProjectileEntity> AIR_SWEEPER_PROJECTILE_ENTITY_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "air_sweeper_entity"),
            FabricEntityTypeBuilder.<SkyWardProjectileEntity>create(SpawnGroup.CREATURE, SkyWardProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<MoltenChamberShotProjectile> MOLTEN_CHAMBER_SHOT_PROJECTILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "magma_bullet_entity"),
            FabricEntityTypeBuilder.<MoltenChamberShotProjectile>create(SpawnGroup.CREATURE, MoltenChamberShotProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<PelletProjectile> PELLET_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "pellet_entity"),
            FabricEntityTypeBuilder.<PelletProjectile>create(SpawnGroup.CREATURE, PelletProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.1f, 0.1f)).build());
    public static final EntityType<OrbOfAnnihilationEntity> ORB_OF_ANNIHILATION_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "orb_of_annihilation_entity"),
            FabricEntityTypeBuilder.<OrbOfAnnihilationEntity>create(SpawnGroup.CREATURE, OrbOfAnnihilationEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(20).build());

    /**
     * Register Mob Attributes here:
     */
    public static void registerMobAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.MONOLOOK_ENTITY_TYPE, MonolookEntity.createMonolookEntityAttributes());
    }
}