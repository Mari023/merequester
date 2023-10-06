package com.almostreliable.merequester.wireless.ae2wtlib;

import appeng.init.client.InitScreens;

public class WTLibClientIntegration {
    public static void init() {
        InitScreens.register(WRMenu.TYPE, WRScreen::new, "/screens/ae2wtlib_wrt.json");
    }
}
