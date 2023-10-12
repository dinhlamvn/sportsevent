package com.inspius.sportsevent.extension

import com.inspius.sportsevent.extensions.asDate
import com.inspius.sportsevent.extensions.parseDatetimeUTC
import com.inspius.sportsevent.extensions.parseHttpException
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response
import java.util.Date

@RunWith(JUnit4::class)
class ExtensionsTest {

    @Test
    fun apiException_parseErrorTest() {
        val error = HttpException(
            Response.error<ResponseBody>(
                500, ResponseBody.create(MediaType.parse("plain/text"), "HTTP 500")
            )
        )

        val parsedError = error.parseHttpException()

        assertEquals("HTTP 500", parsedError.message)
    }

    @Test
    fun date_parseDatetimeUTCTest() {
        val input = "2024-09-01T18:00:00.000Z"
        val expect = "01 Sep 2024 18:00"
        val output = input.parseDatetimeUTC()
        assertEquals(expect, output)
    }

    @Test
    fun date_asDateTest() {
        val input = "2024-09-01T18:00:00.000Z"
        val expect = Date("01 Sep 2024 18:00")
        val output = input.asDate()
        assertEquals(expect, output)
    }
}