package com.singkong.myweather.data

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date

class ConvertersTest {

    private val testDate: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse("2020-09-01T13:00")

    @Test fun dateToTimestamp() {
        assertEquals(testDate.time, Converters().dateToTimestamp(testDate))
    }

    @Test fun timestampToDate() {
        assertEquals(Converters().fromTimestamp(testDate.time), testDate)
    }
}