package com.busaha.busahaapp.util

import java.util.*
import kotlin.math.sqrt

object Normalize {
    fun normalizeL2(vector: FloatArray): FloatArray {
        // compute vector 2-norm
        var norm2 = 0f
        for (i in vector.indices) {
            norm2 += vector[i] * vector[i]
        }
        norm2 = sqrt(norm2)
        if (norm2 == 0f) {
            Arrays.fill(vector, 1f)
        } else {
            for (i in vector.indices) {
                vector[i] = vector[i] / norm2
            }
        }
        return vector
    }
}