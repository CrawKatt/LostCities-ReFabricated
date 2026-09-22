package mcjty.lostcities.mixin;

import mcjty.lostcities.setup.ForgeEventHandlers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Inject(method = "setInitialSpawn", at = @At("HEAD"), cancellable = true)
    private static void lostcities$createSpawn(ServerLevel level, ServerLevelData settings, boolean generateBonusChest,
                                                boolean debug, CallbackInfo ci) {
        if (!debug && ForgeEventHandlers.INSTANCE.onCreateSpawnPoint(level, settings)) {
            ci.cancel();
        }
    }
}