package com.tilikki.training.unimager.demo.util

import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import java.net.InetAddress
import java.net.UnknownHostException

object NetworkUtilities {
    fun isInternetAvailable(): Boolean {
        var result = false
        Thread {
            result = try {
                val address: InetAddress = InetAddress.getByName(UnsplashRepository.BASE_URL)
                !address.equals("")
            } catch (e: UnknownHostException) {
                false
            }
        }
        return result
    }
}