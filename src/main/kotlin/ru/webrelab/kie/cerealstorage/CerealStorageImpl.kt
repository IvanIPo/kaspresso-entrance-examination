package ru.webrelab.kie.cerealstorage

class CerealStorageImpl(
    override val containerCapacity: Float,
    override val storageCapacity: Float
) : CerealStorage {

    /**
     * Блок инициализации класса.
     * Выполняется сразу при создании объекта
     */
    init {
        require(containerCapacity >= 0) {
            "Ёмкость контейнера не может быть отрицательной"
        }
        require(storageCapacity >= containerCapacity) {
            "Ёмкость хранилища не должна быть меньше ёмкости одного контейнера"
        }
    }

    private val storage = mutableMapOf<Cereal, Float>()
    override fun addCereal(cereal: Cereal, amount: Float): Float {
        if (amount < 0) throw IllegalArgumentException()
        if (!storage.containsKey(cereal) && !isEnoughSpaceForNewContainer()) {
            throw IllegalStateException()
        } else {
            val newValue = getAmount(cereal) + amount
            if (newValue > containerCapacity) {
                storage[cereal] = containerCapacity
                return newValue - containerCapacity
            } else {
                storage[cereal] = newValue
                return 0f
            }
        }
    }

    private fun isEnoughSpaceForNewContainer(): Boolean {
        val emptySpace = storageCapacity - containerCapacity * storage.size
        return emptySpace >= containerCapacity
    }

    override fun getCereal(cereal: Cereal, amount: Float): Float {
        if (amount < 0) throw IllegalArgumentException()
        val currentAmount = getAmount(cereal)
        if (amount > currentAmount) {
            return currentAmount
        }
        else {
            storage[cereal] = currentAmount - amount
            return amount
        }
    }

    override fun removeContainer(cereal: Cereal): Boolean {
        if (getAmount(cereal) == 0f) {
            storage.remove(cereal)
            return true
        } else return false
    }

    override fun getAmount(cereal: Cereal): Float {
        return storage[cereal] ?: 0f
    }

    override fun getSpace(cereal: Cereal): Float {
        return containerCapacity - getAmount(cereal)
    }

    override fun toString(): String {
        val mainData = "Объём хранилища: $storageCapacity\n" +
                "Объём одного контейнера: $containerCapacity\n" +
                "Количество контейнеров: ${storage.size}, в частности:"
        val sb = StringBuilder().append(mainData)
        storage.forEach { container -> sb.append("\n${container.key.local} — ${container.value}") }
        return sb.toString()
    }

}
