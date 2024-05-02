package net.globalid.sdk

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform