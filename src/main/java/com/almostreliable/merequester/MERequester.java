package com.almostreliable.merequester;

import com.almostreliable.merequester.network.PacketHandler;
import com.almostreliable.merequester.platform.Platform;
import com.almostreliable.merequester.wireless.WirelessRequesterTerminalRegistration;
import com.almostreliable.merequester.wireless.ae2wtlib.WTLibCommonIntegration;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

@SuppressWarnings("WeakerAccess")
@Mod(BuildConfig.MOD_ID)
public final class MERequester {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String TERMINAL_ID = "requester_terminal";
    public static final String REQUESTER_ID = "requester";

    public MERequester() {
        onInitialize();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> {
            var client = new MERequesterClient();
            return client::onInitialize;
        });
    }

    public void onInitialize() {
        Platform.initConfig();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(MERequester::onCommonSetup);

        boolean ae2WtlibLoaded = Platform.isModLoaded("ae2wtlib");
        FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterEvent event) -> {
            if (event.getRegistryKey().equals(Registry.MENU_REGISTRY)) {
                if(ae2WtlibLoaded)
                    WTLibCommonIntegration.initMenus();
                else WirelessRequesterTerminalRegistration.initMenus();
            } else if (event.getRegistryKey().equals(Registry.ITEM_REGISTRY)) {
                if(ae2WtlibLoaded) WTLibCommonIntegration.initItems();
                else WirelessRequesterTerminalRegistration.initItems();
            }
        });
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }
}
