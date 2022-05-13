package me.spica.note.persistent.sync.core

interface ProviderConfig {
    val remoteAddress: String
    val username: String
    val provider: String
    val authenticationHeaders: Map<String, String>
}