package com.almostreliable.merequester.wireless.ae2wtlib;

import appeng.api.features.GridLinkables;
import appeng.core.AppEng;
import appeng.items.tools.powered.WirelessTerminalItem;
import com.almostreliable.merequester.wireless.WirelessRequesterTerminalRegistration;
import de.mari_023.ae2wtlib.wut.WUTHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class WTLibCommonIntegration {

    public static void initItems() {
        ItemWR itemWR = new ItemWR();
        WirelessRequesterTerminalRegistration.WIRELESS_REQUESTER_TERMINAL = itemWR;
        ForgeRegistries.ITEMS.register("wireless_requester_terminal", itemWR);
        GridLinkables.register(itemWR, WirelessTerminalItem.LINKABLE_HANDLER);
        WUTHandler.addTerminal("requester", itemWR::tryOpen, WRMenuHost::new, WRMenu.TYPE, itemWR);
    }

    public static void initMenus() {
        ForgeRegistries.MENU_TYPES.register(AppEng.makeId(WRMenu.ID), WRMenu.TYPE);
    }
}
