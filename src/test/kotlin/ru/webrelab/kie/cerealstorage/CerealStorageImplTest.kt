package ru.webrelab.kie.cerealstorage

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CerealStorageImplTest {

    private val storage = CerealStorageImpl(10f, 20f)

    @Test
    fun `should throw if containerCapacity is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(-4f, 10f)
        }
    }

    @Test
    fun `should throw if storageCapacity smaller than containerCapacity`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(24f, 10f)
        }
    }

    @Test
    fun `should throw if amount is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.getCereal(Cereal.BUCKWHEAT, -1f)
        }
        assertThrows(IllegalArgumentException::class.java) {
            storage.addCereal(Cereal.BUCKWHEAT, -1f)
        }
    }

    @Test
    fun `addCereal should throw if there's no space for a new cereal `() {
        assertThrows(IllegalStateException::class.java) {
            storage.addCereal(Cereal.BUCKWHEAT, 12f)
            storage.addCereal(Cereal.RICE, 12f)
            storage.addCereal(Cereal.MILLET, 12f)

        }
    }

    @Test
    fun `getCereal should throw if there's no space for a new cereal `() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.getCereal(Cereal.BUCKWHEAT, -1f)
        }
    }

    @Test
    fun addCerealTest() {
        storage.addCereal(Cereal.BULGUR, 5.5f)
        storage.addCereal(Cereal.BUCKWHEAT, 7.5f)
        assertEquals(5.5f, storage.getAmount(Cereal.BULGUR), 0.01f)
        assertEquals(7.5f, storage.getAmount(Cereal.BUCKWHEAT), 0.01f)
    }

    @Test
    fun getCerealTest() {
        storage.addCereal(Cereal.BULGUR, 5.50005f)
        assertEquals(1.0f, storage.getCereal(Cereal.BULGUR, 1.000436f), 0.01f)
        assertEquals(4.5f, storage.getCereal(Cereal.BULGUR, 5f), 0.01f)
    }

    @Test
    fun removeContainerTest() {
        storage.addCereal(Cereal.BULGUR, 0.0f)
        storage.addCereal(Cereal.BUCKWHEAT, 7.5f)
        assertTrue(storage.removeContainer(Cereal.BULGUR))
        assertFalse(storage.removeContainer(Cereal.BUCKWHEAT))
    }

    @Test
    fun getAmountTest() {
        storage.addCereal(Cereal.BULGUR, 5.50005f)
        storage.addCereal(Cereal.BUCKWHEAT, 7.5f)
        assertEquals(5.5f, storage.getAmount(Cereal.BULGUR), 0.01f)
    }

    @Test
    fun getSpaceTest() {
        storage.addCereal(Cereal.BULGUR, 5.50005f)
        assertEquals(4.5f, storage.getSpace(Cereal.BULGUR), 0.01f)
    }

    @Test
    fun toStringTest() {
        storage.addCereal(Cereal.RICE, 6f)
        storage.addCereal(Cereal.BUCKWHEAT, 1.5f)
        val expectedValue = "Объём хранилища: 20.0\n" +
                "Объём одного контейнера: 10.0\n" +
                "Количество контейнеров: 2, в частности:\n" +
                "Рис — 6.0\n" +
                "Гречка — 1.5"
        assertEquals(expectedValue, storage.toString())
    }


}