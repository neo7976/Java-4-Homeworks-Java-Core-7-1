package ru.netology.i18n;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

class LocalizationServiceImplTest {
    LocalizationServiceImpl localizationService;

    public static Stream<Arguments> source() {
        return Stream.of(Arguments.of(Country.RUSSIA, "Добро пожаловать")
                ,Arguments.of(Country.BRAZIL, "Welcome")
                ,Arguments.of(Country.USA, "Welcome")
                ,Arguments.of(Country.GERMANY, "Welcome"));
    }

    @BeforeEach
    void setUp() {
        System.out.println("Начало теста");
        localizationService = new LocalizationServiceImpl();

    }

    @AfterEach
    void tearDown() {
        System.out.println("Завершение теста");
        localizationService = null;
    }

    @ParameterizedTest
    @MethodSource("source")
    void locale(Country country, String welcomeText) {
        String result = localizationService.locale(country);
        MatcherAssert.assertThat(welcomeText, Matchers.equalTo(result));
    }
}