package med.voll.api.domain.patient;

import med.voll.api.domain.address.Address;

public record PatientDetailData(Long id, String name, String email, String cpf, String phone, Address address) {

  public PatientDetailData(Patient patient) {
    this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf(), patient.getTelephone(), patient.getAddress());
  }
}
