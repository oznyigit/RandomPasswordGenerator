package com.oznyigit.randompasswordgenerator

import kotlin.random.Random

class PasswordGenerator {
    fun generate(n: Int, str: String) : String
    {
        val sb = StringBuilder()

        for (i in 1..n) {
            sb.append(str[Random.nextInt(str.length)])
        }
        return sb.toString()
    }
}