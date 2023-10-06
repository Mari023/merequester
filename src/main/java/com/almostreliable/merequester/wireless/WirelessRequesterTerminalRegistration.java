package com.almostreliable.merequester.wireless;

import appeng.api.features.GridLinkables;
import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEItems;
import appeng.core.localization.GuiText;
import appeng.hotkeys.HotkeyActions;
import appeng.hotkeys.InventoryHotkeyAction;
import appeng.items.tools.powered.WirelessTerminalItem;
import com.almostreliable.merequester.Utils;
import net.minecraftforge.registries.ForgeRegistries;

public class WirelessRequesterTerminalRegistration {
    public static WirelessTerminalItem WIRELESS_REQUESTER_TERMINAL;

    public static void initItems() {
        WIRELESS_REQUESTER_TERMINAL = new WirelessRequesterTerminalItem();
        ForgeRegistries.ITEMS.register("wireless_requester_terminal", WIRELESS_REQUESTER_TERMINAL);
        GridLinkables.register(WIRELESS_REQUESTER_TERMINAL, WirelessTerminalItem.LINKABLE_HANDLER);
        Upgrades.add(AEItems.ENERGY_CARD, WIRELESS_REQUESTER_TERMINAL, 2, GuiText.WirelessTerminals.getTranslationKey());
    }

    public static void initMenus() {
        ForgeRegistries.MENU_TYPES.register(Utils.getRL(WirelessRequesterTerminalMenu.ID), WirelessRequesterTerminalMenu.TYPE);
    }
}
