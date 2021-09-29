package io.github.vp.lovelydi.interfaces

typealias CreateInstance<R> = () -> R
typealias OnDispose<T> = (T) -> Unit
