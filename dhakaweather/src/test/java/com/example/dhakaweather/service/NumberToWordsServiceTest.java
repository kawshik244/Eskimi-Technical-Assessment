package com.example.dhakaweather.service;

import com.example.dhakaweather.serviceImpl.NumberToWordsServiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberToWordsServiceTest {

    private final NumberToWordsServiceImpl service = new NumberToWordsServiceImpl();

    @Test
    void testPositive() {
        assertEquals("positive one point three zero", service.convert(1.3));
    }

    @Test
    void testNegative() {
        // -5.4 -> "minus five point four zero"
        assertEquals("minus five point four zero", service.convert(-5.4));
    }

    @Test
    void testAveragePrecision() {
        // -1.44
        assertEquals("minus one point four four", service.convert(-1.44));
    }

    @Test
    void testZero() {
        assertEquals("positive zero point zero zero", service.convert(0.0));
    }
}