package ru.netology.sender;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.*;


/**
 * 1. Поверить, что MessageSenderImpl всегда отправляет только русский текст, если ip относится к российскому сегменту адресов.
 * 2. Поверить, что MessageSenderImpl всегда отправляет только английский текст, если ip относится к американскому сегменту адресов.
 */

class MessageSenderImplTest {

    MessageSenderImpl messageSender;


    @BeforeEach
    void setUp() {
        System.out.println("Начало теста");
    }

    @AfterEach
    void tearDown() {
        System.out.println("\nЗавершение теста");
        messageSender = null;

    }

    @Test
    void test_send_message_if_ip_russia() {

        final Location moscow = new Location("Moscow", Country.RUSSIA, null, 0);
        final String ip = "172.0.32.11";

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.anyString()))
                .thenReturn(moscow);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        Map<String, String> headers = new HashMap<String, String>();
        messageSender = new MessageSenderImpl(geoService, localizationService);
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = messageSender.send(headers);
        String expected = "Добро пожаловать";

        MatcherAssert.assertThat(result, Matchers.equalTo(expected));


    }


    @Test
    void test_send_message_if_ip_usa() {
        List<Country> countryUsa = Arrays.asList(Country.USA, Country.BRAZIL, Country.GERMANY);

        final Location newYork = new Location("New York", Country.USA, null, 0);
        final String ip = "96.44.183.149";

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.anyString()))
                .thenReturn(newYork);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(countryUsa.get(0)))
                .thenReturn("Welcome");

        Map<String, String> headers = new HashMap<String, String>();
        messageSender = new MessageSenderImpl(geoService, localizationService);
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = messageSender.send(headers);
        String expected = "Welcome";

        MatcherAssert.assertThat(result, Matchers.equalTo(expected));
    }
}