package com.hbmkotlin

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.fabricmc.loader.api.FabricLoader
import java.io.File

data class ModConfigData(

    //здеся писать конфу
    /*
    Сохранять:

    ModConfig.config.(название) = (чтото)
    ModConfig.config.(название) = (чтото)
    ModConfig.saveConfig()
    -----------------------------

    Использовать:

    ModConfig.config.(название)
    */

    var startSetupEnable: Boolean = true,
    var create: Boolean = false
)

object  ModConfig {
    private val configFile = File("config/hbmkotlin.json")
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    var config: ModConfigData = ModConfigData()

    init {
        if (configFile.exists()) {
            loadConfig()
        } else {
            saveConfig()
        }
    }

    private fun setupConfig() {
        if (FabricLoader.getInstance().isModLoaded("create")) {
            config.create = true
        }
    }

    fun loadConfig() {
        config = gson.fromJson(configFile.readText(), ModConfigData::class.java)
    }

    fun saveConfig() {
        configFile.parentFile.mkdirs()
        configFile.writeText(gson.toJson(config))
    }

    fun reload() {
        if (configFile.exists()) {
            loadConfig()
        }
    }
}