package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.address.Address;
import med.voll.api.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctors")
public class DoctorController {

  @Autowired
  private DoctorRepository repository;

  @PostMapping
  @Transactional
  public void register(@RequestBody @Valid DoctorRegistrationData data) {
    repository.save(new Doctor(data));
  }

  @GetMapping
    public Page<DoctorListingData> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
    return repository.findAll(pagination).map(DoctorListingData::new);
  }

  @PutMapping
  @Transactional
  public void update(@RequestBody @Valid DoctorUpdateData data) {
    var doctor = repository.getReferenceById(data.id());
    doctor.updateInformation(data);
  }
}
