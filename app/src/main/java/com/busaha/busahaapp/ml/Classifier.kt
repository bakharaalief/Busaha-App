package com.busaha.busahaapp.ml

import android.content.res.AssetManager
import com.busaha.busahaapp.util.Normalize
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class Classifier(assetManager: AssetManager, modelPath: String) {

    private var interpreter: Interpreter

    init {
        interpreter = Interpreter(loadModelFile(assetManager, modelPath))
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun searchRecommend(inputData: IntArray, labelData: Array<String>): List<Pair<String, Float>> {

        //input to float array
        val inputFloat: FloatArray = inputData.map { it.toFloat() }.toFloatArray()

        //input to Normalize
        val inputNormalize = Normalize.normalizeL2(inputFloat)

        //normalize with axis 0
        val inputNormalizeAxis0: FloatArray =
            inputNormalize.map { if (it == 0F) 0F else 1F }.toFloatArray()

        //dua dimens
        val resultOutput = Array(1) { FloatArray(8) }

        //run model in tflite
        interpreter.run(inputNormalizeAxis0, resultOutput)

        val result = HashMap<String, Float>()

        //looping to input value to hash
        for (i in labelData.indices) {
            result[labelData[i]] = resultOutput[0][i]
        }

        return result.toList().sortedByDescending { (_, value) -> value }
    }
}