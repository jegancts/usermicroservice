package com.userdetail.microservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userdetail.microservice.controller.UserContactDetailsController;
import com.userdetail.microservice.exceptions.UserIdvalidationException;
import com.userdetail.microservice.exceptions.UserNotFoundException;
import com.userdetail.microservice.service.UserContactDetailsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/*import static mocks.HotelMock.buildAllHotels;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;*/
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@RunWith(SpringRunner.class)
@WebMvcTest
public class UserContactDetailsControllerTest {

    private static final String ARRIVAL = LocalDate.parse(LocalDate.now().plusDays(0).toString(), DateTimeFormatter.ISO_LOCAL_DATE).toString();
    private static final String DEPARTURE = LocalDate.parse(LocalDate.now().plusDays(1).toString(), DateTimeFormatter.ISO_LOCAL_DATE).toString();

    private static final String EL_VAL = "$%7B%22%22.getClass().forName(%22java.lang.System%22).getDeclaredMethod(%22getProperty%22%2C %22%22.getClass()).invoke(%22%22%2C%22user.name%22)%7D";

    @Autowired
    private MockMvc mockMvc;

   /* @MockBean
    private HotelAvailabilitiesPort hotelAvailabilitiesPort;

    @MockBean
    private RoomTypeValidator roomTypeValidator;

    @MockBean
    private HotelPricePort hotelPricePort;

    @MockBean
    private LocationPricePort locationPricePort;

    @MockBean
    private DeleteObsoleteHotelAvailAsyncPort deleteObsoleteHotelAvailAsyncPort;

    @MockBean
    private HotelLocationPort hotelLocationPort;*/

    @MockBean
    private UserContactDetailsService UserContactDetailsService;


    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserContactDetailsController controller;


    @Before
    public void setup() {
        //searchCriteria = buildSearchCriteria();
        //controller = new UserContactDetailsController();
        //hotelDtoList = HotelDtoMock.buildAllHotelDtos();
        //hotelList = HotelMock.buildAllHotels();
    }

    @Test
    public void shouldReturn400ResponseGetCall() throws Exception {
        this.mockMvc.perform(get("/user/1L"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void shouldThrowUserNotFoundException() {

        when(UserContactDetailsService.getUserDetailsById(1L))
                .thenThrow( new UserNotFoundException("user details not found in the database for Id : " + 1L));
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> controller.getUserById(1L))
                .withMessageContaining("user details not found in the database for Id");
    }

    @Test
    public void shouldThrowUserNotFoundExceptionForAllUsers() {

        when(UserContactDetailsService.getAllUserDetails())
                .thenThrow( new UserNotFoundException("no user details found in the database"));
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> controller.getAllUsers())
                .withMessageContaining("no user details found in the database");
    }

    @Test
    public void shouldThrowUNoSuchElementException() {
        when(UserContactDetailsService.getUserDetailsByIds("1,2,3"))
                .thenThrow( new NoSuchElementException("user details for the requested id's not present in database"));
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> controller.getUsersByIds("1,2,3"))
                .withMessageContaining("user details for the requested id's not present in database");
    }

    @Test
    public void shouldThrowUUserIdvalidationException() {

        when(UserContactDetailsService.getUserDetailsByIds("a,b,c"))
                .thenThrow( new UserIdvalidationException("please enter valid ids with comma sepereted eg: 1,2,3"));
        assertThatExceptionOfType(UserIdvalidationException.class)
                .isThrownBy(() -> controller.getUsersByIds("a,b,c"))
                .withMessageContaining("please enter valid ids with comma sepereted eg: 1,2,3");

    }









    /*@Test
    public void shouldReturn200Response() throws Exception {
        List<Hotel> hotels = buildAllHotels();

        when(hotelAvailabilitiesPort.getHotelAvailabilities(any(SearchCriteria.class))).thenReturn(hotels);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("arrival", ARRIVAL);
        requestParams.add("departure", DEPARTURE);
        requestParams.add("country", "gb");
        requestParams.add("language", "en");
        requestParams.add("hotelCodes", "LONLEI,BASQUA,COVCRO");
        requestParams.add("adults", "2");
        requestParams.add("children", "0");
        requestParams.add("cot", "false");
        requestParams.add("type", "DB");
        requestParams.add("rooms", "1");
        requestParams.add("sort", "DISTANCE");
        requestParams.add("page","1");
        requestParams.add("size","40");

        String path = "src/test/resources/stubs/availabilities/availabilitiesresponse.json";

        this.mockMvc.perform(get("/search/hotels/availabilities").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.readTree(new File(path).getAbsoluteFile()).toString()));
    }

    @Test
    public void shouldReturn400BadRequest() throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("arrival", ARRIVAL);
        requestParams.add("departure", DEPARTURE);
        requestParams.add("country", "gb");
        requestParams.add("language", "en");
        requestParams.add("hotelCodes", "LONLEI,BASQUA,COVCRO");
        requestParams.add("adults", "2");
        requestParams.add("children", "0");
        requestParams.add("cot", "false");
        requestParams.add("type", "DB");
        requestParams.add("rooms", "1");
        requestParams.add("sort","");

        this.mockMvc.perform(get("/search/hotels/availabilities").params(requestParams))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errors[0]").value("searchCriteria: Invalid Sort Type. Sort Type should be either DISTANCE or PRICE."));
    }

    @Test
    public void shouldReturn200EmptyHotelsList() throws Exception {
        when(hotelAvailabilitiesPort.getHotelAvailabilities(buildSearchCriteria())).thenReturn(Collections.emptyList());

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("arrival", ARRIVAL);
        requestParams.add("departure", DEPARTURE);
        requestParams.add("country", "gb");
        requestParams.add("language", "en");
        requestParams.add("hotelCodes", "LONLEI,BASQUA,COVCRO");
        requestParams.add("adults", "2");
        requestParams.add("children", "0");
        requestParams.add("cot", "false");
        requestParams.add("type", "DB");
        requestParams.add("rooms", "1");
        requestParams.add("sort", "DISTANCE");
        requestParams.add("page","1");
        requestParams.add("size","40");

        String path = "src/test/resources/stubs/availabilities/availabilitiesresponse_nodata.json";
        this.mockMvc.perform(get("/search/hotels/availabilities").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.readTree(new File(path).getAbsoluteFile()).toString()));
    }

    private SearchCriteria buildSearchCriteria() {
        return SearchCriteria.builder()
                .hotelCodes(Arrays.asList("LONLEI,BASQUA,COVCRO"))
                .arrival(ARRIVAL)
                .departure(DEPARTURE)
                .adults(new int[]{2})
                .children(new int[]{1})
                .cot(false)
                .rooms(1)
                .type(new String[]{RoomType.DB.name()})
                .language("en")
                .country("gb")
                .sort(SortType.DISTANCE)
                .page(0)
                .size(0)
                .build();
    }

    *//** Pen test defect fix test cases **//*
    @Test
    public void shouldReturn400BadRequest_ForELUnderHotelCodes() throws Exception {

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("arrival", ARRIVAL);
        requestParams.add("departure", DEPARTURE);
        requestParams.add("country", "gb");
        requestParams.add("language", "en");
        requestParams.add("hotelCodes", "EDIWAV");
        requestParams.add("hotelCodes", EL_VAL);
        requestParams.add("adults", "2");
        requestParams.add("children", "0");
        requestParams.add("cot", "false");
        requestParams.add("type", "DB");
        requestParams.add("rooms", "1");
        requestParams.add("sort","DISTANCE");

        this.mockMvc.perform(get("/search/hotels/availabilities").params(requestParams))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errors[0]").value("searchCriteria: Invalid hotel codes in input"));
    }

    @Test
    public void shouldReturn400BadRequest_ForELUnderArrivalDate() throws Exception {

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("arrival", ARRIVAL);
        requestParams.add("departure", DEPARTURE);
        requestParams.add("country", "gb");
        requestParams.add("language", "en");
        requestParams.add("hotelCodes", "EDIWAV");
        requestParams.add("arrival", EL_VAL);
        requestParams.add("adults", "2");
        requestParams.add("children", "0");
        requestParams.add("cot", "false");
        requestParams.add("type", "DB");
        requestParams.add("rooms", "1");
        requestParams.add("sort","DISTANCE");

        this.mockMvc.perform(get("/search/hotels/availabilities").params(requestParams))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errors[0]").value("arrival: must be in correct date format"));
    }*/

}
