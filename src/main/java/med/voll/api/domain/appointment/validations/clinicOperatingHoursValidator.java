package med.voll.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class clinicOperatingHoursValidator implements AppointmentSchedulingValidator {

  public void validate(AppointmentSchedulingData data) {
    var appointmentDate = data.date();

    var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var beforeClinicOpening = appointmentDate.getHour() < 7;
    var afterClinicClosing = appointmentDate.getHour() > 18;

    if (sunday || beforeClinicOpening || afterClinicClosing) {
      throw new ValidationException("Appointment outside clinic operating hours");
    }
  }
}
