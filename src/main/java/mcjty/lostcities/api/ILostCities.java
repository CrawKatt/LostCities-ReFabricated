package mcjty.lostcities.api;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * Main interface for retrieving Lost Cities world information.
 */
public interface ILostCities {

    // MODID for Lost Cities
    String LOSTCITIES = "lostcities";

    // Legacy NeoForge IMC identifiers retained for API compatibility
    String GET_LOST_CITIES = "getLostCities";
    // IMC message for getting ILostCitiesPre
    String GET_LOST_CITIES_PRE = "getLostCitiesPre";

    // Meta values that you can use in assets
    String META_DONTCONNECT = "dontconnect";
    String META_SUPPORT = "support";
    String META_Z_1 = "z1";
    String META_Z_2 = "z2";
    String META_NOWATER = "nowater";
    String META_FORCEDAIR = "forcedair";

    /**
     * Get Lost City information for a given dimension. Returns null if the dimension doesn't support Lost Cities
     */
    @Nullable
    ILostCityInformation getLostInfo(Level world);

    /**
     * Register a lost city profile with a dimension. Note that this is not remembered!
     * You need to do this again after loading your world. Preferably in the chunkGenerator
     * (for example in buildSurface)
     */
    void registerDimension(ServerLevel level, ResourceKey<Level> key, String profile);

    /**
     * Call this client-side(!) before a world is created. It allows you to set the profile for the overworld
     */
    void setOverworldProfile(String profile);
}
