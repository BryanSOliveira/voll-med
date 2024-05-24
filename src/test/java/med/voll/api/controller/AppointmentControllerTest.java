package med.voll.api.controller;

import med.voll.api.domain.appointment.AppointmentDetailsData;
import med.voll.api.domain.appointment.AppointmentSchedule;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<AppointmentSchedulingData> appointmentSchedulingDataJson;

  @Autowired
  private JacksonTester<AppointmentDetailsData> appointmentDetailsDataJson;

  @MockBean
  private AppointmentSchedule appointmentSchedule;

  @Test
  @DisplayName("Should return HTTP 400 when the information is invalid")
  @WithMockUser
  void schedule_scenario1() throws Exception {
    var response = mvc.perform(post("/appointments"))
            .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Should return HTTP 200 when the information is valid")
  @WithMockUser
  void schedule_scenario2() throws Exception {
    var date = LocalDateTime.now().plusHours(1);
    var specialty = Specialty.CARDIOLOGY;

    var detailData = new AppointmentDetailsData(null, 2L, 5L, date);

    when(appointmentSchedule.schedule(any())).thenReturn(detailData);

    var response = mvc.
            perform(
                    post("/appointments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(appointmentSchedulingDataJson.write(
                                    new AppointmentSchedulingData(2L, 5L, date, specialty)
                            ).getJson())
            )
            .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    var expectedJson = appointmentDetailsDataJson.write(
            detailData
    ).getJson();

    assertThat(response.getContentAsString()).isEqualTo(expectedJson);
  }
}