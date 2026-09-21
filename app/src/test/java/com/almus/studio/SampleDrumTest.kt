package com.almus.studio

import com.almus.studio.data.DrumKit
import com.almus.studio.data.DrumSample
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SampleDrumTest {

    @Test
    fun sampleNormalizationClampsAndStripsPath() {
        val s = DrumSample(
            id = "x",
            pitch = 140,
            name = "Kick",
            fileName = "samples\\kick.wav",
            velocityMin = 0,
            velocityMax = 140,
            gainDb = 30f,
            pan = 2f,
            startFrame = -4L,
            endFrame = -1L
        ).normalized()

        assertEquals(127, s.pitch)
        assertEquals(1, s.velocityMin)
        assertEquals(127, s.velocityMax)
        assertEquals("kick.wav", s.fileName)
        assertEquals(12f, s.gainDb)
        assertEquals(1f, s.pan)
        assertEquals(0L, s.startFrame)
    }

    @Test
    fun kitKeepsOneMappingPerPitchVelocityRange() {
        val kit = DrumKit(
            samples = listOf(
                DrumSample(
                    id = "a",
                    pitch = 36,
                    name = "Kick",
                    fileName = "a.wav"
                ),
                DrumSample(
                    id = "b",
                    pitch = 36,
                    name = "Kick 2",
                    fileName = "b.wav"
                ),
                DrumSample(
                    id = "c",
                    pitch = 38,
                    name = "Snare",
                    fileName = "c.wav"
                )
            )
        ).normalized()

        assertTrue(kit.samples.size <= 2)
    }

    @Test
    fun samplerControlsAreClampedAndPersistable() {
        val s = DrumSample(
            id = "x",
            pitch = 36,
            name = "Kick",
            fileName = "kick.wav",
            startFrame = 10L,
            endFrame = 1000L,
            fadeInFrames = 200L,
            fadeOutFrames = 300L,
            reverse = true,
            normalize = true
        ).normalized()

        assertEquals(10L, s.startFrame)
        assertEquals(1000L, s.endFrame)
        assertEquals(200L, s.fadeInFrames)
        assertEquals(300L, s.fadeOutFrames)
        assertTrue(s.reverse)
        assertTrue(s.normalize)
    }
}
