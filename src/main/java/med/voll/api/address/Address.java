package med.voll.api.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  private String street;
  private String district;
  private String zipCode;
  private String city;
  private String state;
  private String complement;
  private String number;

  public Address(AddressData data) {
    this.street = data.street();
    this.district = data.district();
    this.zipCode = data.zipCode();
    this.city = data.city();
    this.state = data.state();
    this.complement = data.complement();
    this.number = data.number();
  }

  public void updateInformation(AddressData data) {
    if (data.street() != null) {
      this.street = data.street();
    }
    if (data.district() != null) {
      this.district = data.district();
    }
    if (data.zipCode() != null) {
      this.zipCode = data.zipCode();
    }
    if (data.city() != null) {
      this.city = data.city();
    }
    if (data.state() != null) {
      this.state = data.state();
    }
    if (data.complement() != null) {
      this.complement = data.complement();
    }
    if (data.number() != null) {
      this.number = data.number();
    }
  }
}
