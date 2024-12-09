package com.hbmkotlin

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

object Sounds {
    // Определите идентификатор звука
    val UU_ANTHEM_ID = Identifier("hbmkotlin", "uu_anthem")
    val AIR_RAID_ID = Identifier("hbmkotlin", "air_raid")

    // Используйте метод `SoundEvent.of` для создания события
    val UU_ANTHEM: SoundEvent = SoundEvent.of(UU_ANTHEM_ID)
    val AIR_RAID: SoundEvent = SoundEvent.of(AIR_RAID_ID)

    fun registerSounds() {
        // Зарегистрируйте звук в реестре
        Registry.register(Registries.SOUND_EVENT, UU_ANTHEM_ID, UU_ANTHEM)
        Registry.register(Registries.SOUND_EVENT, AIR_RAID_ID, AIR_RAID)
    }
}

/*
world.playSound(
    null,
    blockPos,
    Sounds.AIR_RAID,
    soundCategory.MUSIC,
    1.0f,
    1.0f
*/