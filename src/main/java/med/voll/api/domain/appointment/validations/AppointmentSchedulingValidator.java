package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentSchedulingData;

public interface AppointmentSchedulingValidator {
  void validate(AppointmentSchedulingData data);
}
