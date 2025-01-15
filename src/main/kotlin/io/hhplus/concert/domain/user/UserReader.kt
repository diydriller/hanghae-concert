package io.hhplus.concert.domain.user

interface UserReader {
    fun findUser(userId: String): User?
}