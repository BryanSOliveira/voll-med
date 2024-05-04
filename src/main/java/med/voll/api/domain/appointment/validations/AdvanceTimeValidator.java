package med.voll.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AdvanceTimeValidator implements AppointmentSchedulingValidator {

  public void validate(AppointmentSchedulingData data) {
    var appointmentDate = data.date();
    var now = LocalDateTime.now();
    var minutesDifference = Duration.between(now, appointmentDate).toMinutes();

    if (minutesDifference < 30) {
      throw new ValidationException("Appointment must be scheduled with a minimum advance notice of 30 minutes");
    }
  }
}
