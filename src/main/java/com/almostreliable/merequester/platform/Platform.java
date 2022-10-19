package com.almostreliable.merequester.platform;

import com.almostreliable.merequester.BuildConfig;
import com.almostreliable.merequester.Registration;
import com.almostreliable.merequester.Utils;
import com.almostreliable.merequester.mixin.accessors.ScreenMixin;
import com.almostreliable.merequester.network.DragAndDropPacket;
import com.almostreliable.merequester.network.RequestUpdatePacket;
import com.almostreliable.merequester.network.RequesterSyncPacket;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class Platform {

    private Platform() {}

    public static void initConfig() {
        MidnightConfig.init(BuildConfig.MOD_ID, Config.class);
    }

    public static int getRequestLimit() {
        return Config.REQUESTS;
    }

    public static double getIdleEnergy() {
        return Config.IDLE_ENERGY;
    }

    public static boolean requireChannel() {
        return Config.REQUIRE_CHANNEL;
    }

    public static CreativeModeTab createTab() {
        return FabricItemGroup.builder()
            .title(Utils.translate("itemGroup", "tab"))
            .icon(() -> Registration.REQUESTER.stack())
            .noScrollBar()
            .build();
    }

    public static void sendRequestUpdate(long requesterId, int requestIndex, boolean state) {
        ClientPlayNetworking.send(
            RequestUpdatePacket.CHANNEL,
            RequestUpdatePacket.encode(requesterId, requestIndex, state)
        );
    }

    public static void sendRequestUpdate(long requesterId, int requestIndex, long amount, long batch) {
        ClientPlayNetworking.send(
            RequestUpdatePacket.CHANNEL,
            RequestUpdatePacket.encode(requesterId, requestIndex, amount, batch)
        );
    }

    public static void sendDragAndDrop(long requesterId, int requestIndex, ItemStack item) {
        ClientPlayNetworking.send(
            DragAndDropPacket.CHANNEL,
            DragAndDropPacket.encode(requesterId, requestIndex, item)
        );
    }

    public static void sendClearData(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, RequesterSyncPacket.CHANNEL, RequesterSyncPacket.encode());
        }
    }

    public static void sendInventoryData(Player player, long requesterId, CompoundTag data) {
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(
                serverPlayer,
                RequesterSyncPacket.CHANNEL,
                RequesterSyncPacket.encode(requesterId, data)
            );
        }
    }

    public static List<Renderable> getRenderables(Screen screen) {
        return Utils.cast(screen, ScreenMixin.class).merequester$getRenderables();
    }

    public static boolean isModLoaded(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }
}
