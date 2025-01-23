package io.hhplus.concert.exception

import io.hhplus.concert.response.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(BindException::class)
    fun handleValidationExceptions(ex: BindException): ResponseEntity<BaseResponse<Unit>> {
        logger.error("Exception occurred: ${ex.message}", ex)

        val errorMessages = ex.bindingResult.fieldErrors
            .joinToString(" , ") { "${it.field}: ${it.defaultMessage ?: "Invalid value"}" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BaseResponse(
                isSuccess = false,
                message = errorMessages
            )
        )
    }

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleMissingRequestHeaderExceptions(ex: MissingRequestHeaderException): ResponseEntity<BaseResponse<Unit>> {
        logger.error("Exception occurred: ${ex.message}", ex)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BaseResponse(
                isSuccess = false,
                message = ex.message
            )
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<BaseResponse<Unit>> {
        logger.error("Exception occurred: ${ex.notFoundStatus.message}", ex)

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            BaseResponse(ex.notFoundStatus)
        )
    }

    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(ex: ConflictException): ResponseEntity<BaseResponse<Unit>> {
        logger.error("Exception occurred: ${ex.conflictStatus.message}", ex)

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            BaseResponse(ex.conflictStatus)
        )
    }
}