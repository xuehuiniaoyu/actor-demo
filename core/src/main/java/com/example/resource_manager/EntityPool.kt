package com.example.resource_manager

object EntityPool {
    private val pool by lazy { HashMap<String, Lazy<Any>>() }
    fun <T: Any> push(key: String, entity: Lazy<T>) {
        pool[key] = entity
    }

    fun <T: Any> pull(key: String): T {
        return (pool[key]?.value as? T) ?: error("pool:[$key]=null")
    }
}