package io.connorwyatt.todos.common.domain.streams

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.*

class StreamNameUtilitiesTests {
    @ParameterizedTest
    @CsvSource(
        "examples-123,examples",
        "examples-00000000-0000-0000-0000-000000000000,examples",
        "\$ce-examples,examples"
    )
    fun `category should return the correct category from a stream name`(
        streamName: String,
        expectedCategory: String
    ) {
        expectThat(StreamNameUtilities.parseCategory(streamName)).isEqualTo(expectedCategory)
    }

    @ParameterizedTest
    @ValueSource(strings = ["\$et-example.exampleEvent.v1", "\$all"])
    fun `category should throw if the stream name does not have a category`(streamName: String) {
        expectThrows<Exception> { StreamNameUtilities.parseCategory(streamName) }
    }
}
