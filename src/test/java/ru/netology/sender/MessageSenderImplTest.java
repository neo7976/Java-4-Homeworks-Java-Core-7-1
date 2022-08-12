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

import java.util.HashMap;
import java.util.Map;


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
        System.out.println("Завершение теста");
        messageSender = null;

    }

    @Test
    void test_send_message_if_ip_russia() {

        final Location moscow = new Location("Moscow", Country.RUSSIA, null, 0);
        final String ip = "172.0.32.11";
//        final Location newYork = new Location("New York", Country.USA, " 10th Avenue", 32);
//        final Location usa = new Location("New York", Country.USA, null,  0);

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(moscow);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Location location = geoService.byIp(ip);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenReturn(String.valueOf(Country.USA));

        Map<String, String> headers = new HashMap<String, String>();
        messageSender = new MessageSenderImpl(geoService, localizationService);
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = messageSender.send(headers);
        String expected = "Добро пожаловать";

        MatcherAssert.assertThat(result, Matchers.equalTo(expected));


    }
}