package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentDetailsData;
import med.voll.api.domain.appointment.AppointmentSchedule;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

  @Autowired
  private AppointmentSchedule appointmentSchedule;

  @PostMapping
  @Transactional
  public ResponseEntity schedule(@RequestBody @Valid AppointmentSchedulingData data) {
    var dto = appointmentSchedule.schedule(data);
    return ResponseEntity.ok(dto);
  }
}
