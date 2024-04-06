package med.voll.api.domain.doctor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "doctors")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String telephone;
  private String crm;

  @Enumerated(EnumType.STRING)
  private Specialty specialty;

  @Embedded
  private Address address;

  private Boolean active;

  public Doctor(DoctorRegistrationData data) {
    this.active = true;
    this.name = data.name();
    this.email = data.email();
    this.telephone = data.telephone();
    this.crm = data.crm();
    this.specialty = data.specialty();
    this.address = new Address(data.address());
  }

  public void updateInformation(DoctorUpdateData data) {
    if (data.name() != null) {
      this.name = data.name();
    }
    if (data.telephone() != null) {
      this.telephone = data.telephone();
    }
    if (data.address() != null) {
      this.address.updateInformation(data.address());
    }
  }

  public void delete() {
    this.active = false;
  }
}
