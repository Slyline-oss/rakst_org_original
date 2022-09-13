package org.raksti.web.data.service;

import org.raksti.web.data.entity.SamplePerson;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, UUID> {

}
