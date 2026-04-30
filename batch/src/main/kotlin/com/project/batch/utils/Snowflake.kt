package com.project.batch.utils

import org.springframework.stereotype.Component
import java.util.random.RandomGenerator

@Component
class Snowflake {

    companion object {
        private const val NODE_ID_BITS = 10
        private const val SEQUENCE_BITS = 12
        private const val MAX_NODE_ID = (1L shl NODE_ID_BITS) - 1
        private const val MAX_SEQUENCE = (1L shl SEQUENCE_BITS) - 1
        private const val START_TIME_MILLIS = 1704067200000L
    }

    private val nodeId = RandomGenerator.getDefault().nextLong(MAX_NODE_ID + 1)

    private var lastTimeMillis = START_TIME_MILLIS
    private var sequence = 0L

    @Synchronized
    fun nextId(): Long {
        var currentTimeMillis = System.currentTimeMillis()

        check(currentTimeMillis >= lastTimeMillis) { "Invalid Time" }

        if (currentTimeMillis == lastTimeMillis) {
            sequence = (sequence + 1) and MAX_SEQUENCE
            if (sequence == 0L) {
                currentTimeMillis = waitNextMillis(currentTimeMillis)
            }
        } else {
            sequence = 0L
        }

        lastTimeMillis = currentTimeMillis

        return ((currentTimeMillis - START_TIME_MILLIS) shl (NODE_ID_BITS + SEQUENCE_BITS)) or
                (nodeId shl SEQUENCE_BITS) or
                sequence
    }

    private fun waitNextMillis(currentTimestamp: Long): Long {
        var timestamp = currentTimestamp
        while (timestamp <= lastTimeMillis) {
            timestamp = System.currentTimeMillis()
        }
        return timestamp
    }
}