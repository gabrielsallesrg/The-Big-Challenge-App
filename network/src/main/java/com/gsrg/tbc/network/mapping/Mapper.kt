package com.gsrg.tbc.network.mapping

abstract class Mapper<in T, out E> {

    abstract fun mapFrom(data: T): E

}