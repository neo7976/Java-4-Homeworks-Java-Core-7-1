package ru.netology.geo;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.MatcherAssert.*;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

class GeoServiceImplTest {
    GeoServiceImpl geoService;

    public static Stream<Arguments> sourceIp() {
        return Stream.of(Arguments.of("127.0.0.1", new Location(null, null, null, 0))
                , Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15))
                , Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32))
                , Arguments.of("172.1.1.1", new Location("Moscow", Country.RUSSIA, null, 0))
                , Arguments.of("96.2.5.1", new Location("New York", Country.USA, null, 0)));
    }

    @BeforeEach
    void setUp() {
        System.out.println("Начало теста");
        geoService = new GeoServiceImpl();

    }

    @AfterEach
    void tearDown() {
        System.out.println("Завершение теста");
        geoService = null;
    }

    @ParameterizedTest
    @MethodSource("sourceIp")
    void byIp(String ip, Location local) {
        Location result = geoService.byIp(ip);

        assertThat(local, equalTo(result));

    }

    @Test
    void byCoordinates() {
    }
}