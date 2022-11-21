package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ParamTest {

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            FRUIT,         RANK,    RESULT
            apple,         1,       apple1
            banana,        2,       banana2
            'lemon, lime', 3,       'lemon, lime3'
            strawberry,    700_000, strawberry700000
            """)
    void test(String fruit, int rank, String result) {
        assertThat(fruit + rank).isEqualTo(result);
    }
}
