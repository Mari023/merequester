package com.almostreliable.merequester.wireless;

import appeng.api.config.Actionable;
import appeng.api.features.GridLinkables;
import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEItems;
import appeng.core.localization.GuiText;
import appeng.items.tools.powered.WirelessTerminalItem;
import com.almostreliable.merequester.ModTab;
import com.almostreliable.merequester.Utils;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class WirelessRequesterTerminalRegistration {
    public static WirelessTerminalItem WIRELESS_REQUESTER_TERMINAL;

    public static void registerWirelessRequesterTerminal() {
        WIRELESS_REQUESTER_TERMINAL = new WirelessRequesterTerminalItem();
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation("merequester", "wireless_requester_terminal"), WIRELESS_REQUESTER_TERMINAL);

        //add terminal to ItemGroup
        ItemStack chargedTerminal = new ItemStack(WIRELESS_REQUESTER_TERMINAL);
        var emptyTerminal = chargedTerminal.copy();
        WIRELESS_REQUESTER_TERMINAL.injectAEPower(chargedTerminal, WIRELESS_REQUESTER_TERMINAL.getAEMaxPower(chargedTerminal), Actionable.MODULATE);
        ItemGroupEvents.modifyEntriesEvent(ModTab.TAB_KEY)
            .register(entries -> entries.addAfter(emptyTerminal, chargedTerminal));

        GridLinkables.register(WIRELESS_REQUESTER_TERMINAL, WirelessTerminalItem.LINKABLE_HANDLER);
        Registry.register(BuiltInRegistries.MENU, Utils.getRL(WirelessRequesterTerminalMenu.ID), WirelessRequesterTerminalMenu.TYPE);
        Upgrades.add(AEItems.ENERGY_CARD, WIRELESS_REQUESTER_TERMINAL, 2, GuiText.WirelessTerminals.getTranslationKey());
    }
}
