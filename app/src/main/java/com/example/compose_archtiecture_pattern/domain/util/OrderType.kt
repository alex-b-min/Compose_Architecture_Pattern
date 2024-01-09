package com.example.compose_archtiecture_pattern.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}