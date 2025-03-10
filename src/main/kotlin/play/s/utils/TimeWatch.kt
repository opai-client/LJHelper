package play.s.utils

/**
 * @author yuchenxue
 * @date 2025/02/21
 */

class TimeWatch {
    private var time = System.currentTimeMillis()

    fun hasPassTime(pass: Long): Boolean {
        return System.currentTimeMillis() - time >= pass
    }

    fun hasLeftTime(left: Long): Long {
        if (hasPassTime(left)) {
            return 0L
        }
        return left - (System.currentTimeMillis() - time)
    }

    val passTime: Long
        get() = System.currentTimeMillis() - time

    fun reset() = apply {
        time = System.currentTimeMillis()
    }
}
