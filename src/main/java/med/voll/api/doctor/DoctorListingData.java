package med.voll.api.doctor;

public record DoctorListingData(String name, String email, String crm, Specialty specialty) {

  public DoctorListingData(Doctor doctor) {
    this(doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty());
  }
}
