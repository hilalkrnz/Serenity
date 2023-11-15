package com.example.serenity.data.mapper

interface Mapper<I, O> {
    fun map(input: I?): O
}