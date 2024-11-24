package com.client

import com.client.ui.CustomScreen
import com.hbmkotlin.ModConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.TitleScreen

class hbmkotlinClient : ClientModInitializer {
    private var isFirstLoad = true

    override fun onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            if (isFirstLoad && client.currentScreen is TitleScreen) {
                isFirstLoad = false
                handleCustomMenu(client)
            }
        }
    }

    private fun handleCustomMenu(client: MinecraftClient) {
        if (ModConfig.config.startSetupEnable) {
            client.setScreen(CustomScreen())
            ModConfig.config.startSetupEnable = false
            ModConfig.saveConfig()
        }
    }
}
