package med.voll.api.domain.appointment;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.validations.AppointmentSchedulingValidator;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentSchedule {

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private DoctorRepository doctorRepository;

  @Autowired
  private PatientRepository patientRepository;

  @Autowired
  private List<AppointmentSchedulingValidator> validators;

  public AppointmentDetailsData schedule(AppointmentSchedulingData data) {
    if (!patientRepository.existsById(data.patientId())) {
      throw new ValidationException("The provided patient ID does not exist!");
    }

    if (data.doctorId() != null && !doctorRepository.existsById(data.doctorId())) {
      throw new ValidationException("The provided doctor ID does not exist!");
    }

    validators.forEach(v -> v.validate(data));

    var patient = patientRepository.getReferenceById(data.patientId());
    var doctor = selectDoctor(data);
    if (doctor == null) {
      throw new ValidationException("There are no available doctors on this date!");
    }

    var appointment = new Appointment(null, doctor, patient, data.date());
    appointmentRepository.save(appointment);

    return new AppointmentDetailsData(appointment);
  }

  private Doctor selectDoctor(AppointmentSchedulingData data) {
    if (data.doctorId() != null) {
      return doctorRepository.getReferenceById(data.doctorId());
    }

    if (data.specialty() == null) {
      throw new ValidationException("Specialty is required when a doctor is not chosen!");
    }

    return doctorRepository.getRandomAvailableDoctorByDate(data.specialty(), data.date());
  }
}
