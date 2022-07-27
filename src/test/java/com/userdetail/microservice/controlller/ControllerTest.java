package com.userdetail.microservice.controlller;

/*import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import mocks.HotelDtoMock;
import mocks.HotelMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.whitbread.availabilitycacheservice.domain.model.availabilities.Hotel;
import uk.co.whitbread.availabilitycacheservice.domain.ports.primary.HotelAvailabilitiesPort;
import uk.co.whitbread.availabilitycacheservice.domain.ports.secondary.DeleteObsoleteHotelAvailAsyncPort;
import uk.co.whitbread.availabilitycacheservice.infrastructure.exceptions.HotelAvailabilitiesException;
import uk.co.whitbread.availabilitycacheservice.infrastructure.mapper.HotelAvailabilitiesMapper;
import uk.co.whitbread.availabilitycacheservice.infrastructure.rest.request.SearchCriteria;
import uk.co.whitbread.availabilitycacheservice.infrastructure.rest.request.enums.RoomType;
import uk.co.whitbread.availabilitycacheservice.infrastructure.rest.response.HotelAvailabilitiesDto;
import uk.co.whitbread.availabilitycacheservice.infrastructure.rest.response.HotelDto;*/

import com.userdetail.microservice.controller.UserContactDetailsController;
import com.userdetail.microservice.dao.UserContactDetailsRepository;
import com.userdetail.microservice.dto.AddressDto;
import com.userdetail.microservice.dto.UserDetailsDto;
import com.userdetail.microservice.entity.Address;
import com.userdetail.microservice.entity.UserDetails;
import com.userdetail.microservice.service.UserContactDetailsService;
import org.h2.engine.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.powermock.api.mockito.PowerMockito.doNothing;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private UserContactDetailsController controller;

    @Autowired
    private UserContactDetailsService userContactDetailsService;


    @MockBean
    private UserContactDetailsRepository userContactDetailsRepository;

    List<UserDetails> listOfUserDetails = null;
    UserDetails userDetails = null;
    UserDetailsDto userDetailsDto =null;

    @Before
    public void setup() {
        //searchCriteria = buildSearchCriteria();
        //controller = new HotelAvailabilitiesController(hotelAvailabilitiesPort,deleteObsoleteHotelAvailAsyncPort);
       // hotelDtoList = HotelDtoMock.buildAllHotelDtos();
        listOfUserDetails = buildUserDetails();
        userDetails = buildUserDetailsForFindById();
        userDetailsDto= buildUserDetailsDto();

    }

    public  List<UserDetails> buildUserDetails() {

        List<UserDetails> listOffUser = new ArrayList<>();

        UserDetails userDetails = new UserDetails();
        userDetails.setId(1L);
        userDetails.setFirstName("FirstName");
        userDetails.setLastName("LastName");
        userDetails.setPhoneNo("1234567890");
        Address address = new Address();
        address.setId(1L);
        address.setDoorNo("30A");
        address.setStreetName("Street Name");
        address.setPostCode("XXZ123");
        userDetails.setAddress(address);


        UserDetails userDetails1 = new UserDetails();
        userDetails1.setId(2L);
        userDetails1.setFirstName("FirstName1");
        userDetails1.setLastName("LastName1");
        userDetails1.setPhoneNo("1234567891");
        Address address1 = new Address();
        address1.setId(2L);
        address1.setDoorNo("30B");
        address1.setStreetName("Street Name1");
        address1.setPostCode("XXZ121");
        userDetails1.setAddress(address1);

        listOffUser.add(userDetails);
        listOffUser.add(userDetails1);

        return listOffUser;

    }

    public  UserDetails buildUserDetailsForFindById() {

        UserDetails userDetails = new UserDetails();
        userDetails.setId(1L);
        userDetails.setFirstName("FirstName");
        userDetails.setLastName("LastName");
        userDetails.setPhoneNo("1234567890");
        Address address = new Address();
        address.setId(1L);
        address.setDoorNo("30A");
        address.setStreetName("Street Name");
        address.setPostCode("XXZ123");
        userDetails.setAddress(address);

        return userDetails;

    }

    public  UserDetailsDto buildUserDetailsDto() {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setFirstName("UpdatedFirstName");
        userDetailsDto.setLastName("UpdatedLastName");
        AddressDto addressDto = new AddressDto();
        addressDto.setDoorNo("U30A");
        addressDto.setStreetName("Updated Street Name");
        addressDto.setPostCode("UXXZ123");
        userDetailsDto.setAddress(addressDto);
        return userDetailsDto;

    }


    //all user
    @Test
    public void shouldReturnAllUserDetailsTest() {
        PowerMockito.when(userContactDetailsRepository.findAll()).thenReturn(listOfUserDetails);
        ResponseEntity responseEntity = controller.getAllUsers();
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        ArrayList arrayList = (ArrayList) responseEntity.getBody();
        assertThat(((ArrayList) responseEntity.getBody()).size()).isEqualTo(2);

    }

    //getById
    @Test
    public void shouldReturnUserDetailsByIdTest() {
        PowerMockito.when(userContactDetailsRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(userDetails));
        ResponseEntity responseEntity = controller.getUserById(1L);
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        UserDetails userDetails = (UserDetails) responseEntity.getBody();
        assertThat(userDetails.getFirstName()).isEqualTo("FirstName");
        assertThat(userDetails.getPhoneNo()).isEqualTo("1234567890");
        assertThat(userDetails.getAddress().getPostCode()).isEqualTo("XXZ123");

    }


    //getByIds
    @Test
    public void shouldReturnUserDetailsByIdsTest() {
        PowerMockito.when(userContactDetailsRepository.findAllById(Arrays.asList(1L,2L))).thenReturn(listOfUserDetails);
        ResponseEntity responseEntity = controller.getUsersByIds("1,2");
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        ArrayList arrayList = (ArrayList) responseEntity.getBody();
        assertThat(((ArrayList) responseEntity.getBody()).size()).isEqualTo(2);

    }

    //delete by id
    @Test
    public void shouldDeleteUserDetailsByIdTest() {
        PowerMockito.when(userContactDetailsRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(userDetails));
        doNothing().when(userContactDetailsRepository).deleteById(1L);
        ResponseEntity responseEntity = controller.deleteUserById(1L);
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    //post call
    @Test
    public void shouldSaveUserDetailsTest() {
        PowerMockito.when(userContactDetailsRepository.save(userDetails)).thenReturn(userDetails);
        ResponseEntity responseEntity = controller.saveUser(userDetails);
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    //Put by Id
    @Test
    public void shouldUpdateUserDetailsTest() {
        PowerMockito.when(userContactDetailsRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(userDetails));
        PowerMockito.when(userContactDetailsRepository.save(userDetails)).thenReturn(userDetails);
        ResponseEntity responseEntity = controller.updateUserById(userDetailsDto,1L);
        Assert.assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();

        UserDetails userDetails = (UserDetails) responseEntity.getBody();
        assertThat(userDetails.getFirstName()).isEqualTo("UpdatedFirstName");
    }













    /*@Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    private static final String ARRIVAL = "2021-06-14";
    private static final String DEPARTURE = "2021-06-16";
    private static final String COUNTRY = "gb";
    private static final String LANGUAGE = "en";
    private static final int ROOMS = 1;
    private static final int[] ADULTS = new int[]{1};
    private static final int[] CHILDREN = new int[0];

    private HotelAvailabilitiesController controller;
    private List<HotelDto> hotelDtoList;
    private List<Hotel> hotelList;
    private SearchCriteria searchCriteria;

    @Mock
    HotelAvailabilitiesPort hotelAvailabilitiesPort;

    @Mock
    DeleteObsoleteHotelAvailAsyncPort deleteObsoleteHotelAvailAsyncPort;

    @Before
    public void setup() {
        searchCriteria = buildSearchCriteria();
        controller = new HotelAvailabilitiesController(hotelAvailabilitiesPort,deleteObsoleteHotelAvailAsyncPort);
        hotelDtoList = HotelDtoMock.buildAllHotelDtos();
        hotelList = HotelMock.buildAllHotels();
    }

    @Test
    public void shouldReturnHotelAvailabilities() {
        when(hotelAvailabilitiesPort.getHotelAvailabilities(searchCriteria)).thenReturn(hotelList);

        PowerMockito.mockStatic(HotelAvailabilitiesMapper.class);
        when(HotelAvailabilitiesMapper.mapHotelToHotelDto(hotelList)).thenReturn(hotelDtoList);

        ResponseEntity<HotelAvailabilitiesDto> response = controller.getHotelAvailabilities(searchCriteria);

        HotelAvailabilitiesDto hotelAvailabilitiesDtoResponse = response.getBody();
        assertThat(hotelAvailabilitiesDtoResponse.getHotelAvailabilities()).isNotEmpty();
        assertThat(hotelAvailabilitiesDtoResponse.getHotelAvailabilities())
                .extracting(HotelDto::getHotelCode)
                .containsExactly("PLYPTI", "PLYLOC", "PLYMAR", "LISBAR", "PAIWHI");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturnEmptyHotelAvailabilitiesWith200Response() {
        when(hotelAvailabilitiesPort.getHotelAvailabilities(searchCriteria)).thenReturn(Collections.emptyList());

        ResponseEntity<HotelAvailabilitiesDto> response = controller.getHotelAvailabilities(searchCriteria);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response.getBody().getHotelAvailabilities().isEmpty());
    }

    @Test
    public void shouldThrowInternalServerError() {
        when(hotelAvailabilitiesPort.getHotelAvailabilities(searchCriteria))
                .thenThrow(new RuntimeException("Error occurred while searching hotel availabilities"));

        assertThatExceptionOfType(HotelAvailabilitiesException.class)
                .isThrownBy(() -> controller.getHotelAvailabilities(searchCriteria))
                .withMessageContaining("Error occurred while searching hotel availabilities");
    }

    private SearchCriteria buildSearchCriteria() {
        return SearchCriteria.builder()
                .hotelCodes(Arrays.asList("PLYPTI", "PLYLOC", "PLYMAR", "LISBAR", "PAIWHI"))
                .arrival(ARRIVAL)
                .departure(DEPARTURE)
                .adults(new int[]{1})
                .children(new int[]{1})
                .cot(false)
                .rooms(1)
                .type(new String[]{RoomType.SB.name()})
                .language(LANGUAGE)
                .country(COUNTRY)
                .build();
    }*/
}
