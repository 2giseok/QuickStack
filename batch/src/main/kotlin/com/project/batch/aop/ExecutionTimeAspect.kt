package com.project.batch.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Aspect
@Component
open class ExecutionTimeAspect {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @Around("@annotation(com.project.batch.aop.LogExecutionTime)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()
        val methodName = joinPoint.signature.name
        logger.info("[$methodName] started at ${now()}")

        val result = joinPoint.proceed()

        return if (result is Mono<*>) {
            result
                .doOnSuccess {
                    logger.info("[$methodName] finished at ${now()} (${elapsed(startTime)}ms)")
                }
                .doOnError {
                    logger.info("[$methodName] failed at ${now()} (${elapsed(startTime)}ms)")
                }
        } else {
            logger.info("[$methodName] finished at ${now()} (${elapsed(startTime)}ms)")
            result
        }
    }

    private fun now() = LocalDateTime.now().format(formatter)
    private fun elapsed(startTime: Long) = System.currentTimeMillis() - startTime
}