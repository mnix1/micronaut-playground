package com.example.car.component;

import com.example.audit.AuditEntity;
import com.example.audit.AuditRepository;
import com.example.car.api.CarFacade;
import com.example.car.api.CarSnapshot;
import com.example.car.api.OtherFacade;
import com.example.car.api.command.ChangeCarOwnerCommand;
import com.example.car.api.command.CreateCarCommand;
import jakarta.inject.Singleton;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Singleton
class DefaultCarFacade implements CarFacade {

    private final CarRepository carRepository;
    private final AuditRepository auditRepository;
    private final OtherFacade otherFacade;

    DefaultCarFacade(CarRepository carRepository, AuditRepository auditRepository, OtherFacade otherFacade) {
        this.carRepository = carRepository;
        this.auditRepository = auditRepository;
        this.otherFacade = otherFacade;
    }

    @Override
    @Transactional
    public UUID create(CreateCarCommand command) {
        UUID id = UUID.randomUUID();
        Car car = new Car(id, command.model(), command.producer(), command.productionYear(), null);
        carRepository.save(new CarEntity(car));
        otherFacade.somethingBeforeAudit();
        auditRepository.save(new AuditEntity(UUID.randomUUID().toString(), Instant.now(), command.toString()));
        otherFacade.somethingAfterAudit();
        return id;
    }

    @Override
    public void changeOwner(ChangeCarOwnerCommand command) {
        Car car = carRepository.findById(command.id().toString()).map(CarEntity::toDomain).orElseThrow();
        car.changeOwner(command.owner());
        carRepository.update(new CarEntity(car));
        otherFacade.somethingBeforeAudit();
        auditRepository.save(new AuditEntity(UUID.randomUUID().toString(), Instant.now(), command.toString()));
        otherFacade.somethingAfterAudit();
    }

    @Override
    public List<CarSnapshot> list() {
        return stream(carRepository.findAll().spliterator(), false).map(CarEntity::toDomain).map(Car::toSnapshot).collect(toList());
    }
}
