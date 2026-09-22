package mcjty.lostcities.setup;


import mcjty.lostcities.LostCities;
import mcjty.lostcities.worldgen.LostCityFeature;
import mcjty.lostcities.worldgen.LostCitySphereFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import java.util.function.Supplier;

public class Registration {

    private static final LostCityFeature LOSTCITY_FEATURE_INSTANCE = new LostCityFeature();
    private static final LostCitySphereFeature LOSTCITY_SPHERE_FEATURE_INSTANCE = new LostCitySphereFeature();
    public static final Supplier<LostCityFeature> LOSTCITY_FEATURE = () -> LOSTCITY_FEATURE_INSTANCE;
    public static final Supplier<LostCitySphereFeature> LOSTCITY_SPHERE_FEATURE = () -> LOSTCITY_SPHERE_FEATURE_INSTANCE;

    public static final ResourceLocation LOSTCITY = ResourceLocation.fromNamespaceAndPath(LostCities.MODID, "lostcity");

    public static final ResourceKey<DimensionType> DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, LOSTCITY);
    public static final ResourceKey<Level> DIMENSION = ResourceKey.create(Registries.DIMENSION, LOSTCITY);

    public static void init() {
        Registry.register(BuiltInRegistries.FEATURE, LOSTCITY, LOSTCITY_FEATURE_INSTANCE);
        Registry.register(BuiltInRegistries.FEATURE, ResourceLocation.fromNamespaceAndPath(LostCities.MODID, "spheres"), LOSTCITY_SPHERE_FEATURE_INSTANCE);
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.RAW_GENERATION,
                ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(LostCities.MODID, "lostcities")));
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(LostCities.MODID, "spheres")));
    }
}
