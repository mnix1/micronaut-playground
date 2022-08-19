package com.example.car.component;

import com.example.car.api.CarFacade;
import com.example.car.api.CarSnapshot;
import com.example.car.api.command.ChangeCarOwnerCommand;
import com.example.car.api.command.CreateCarCommand;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Singleton
class DefaultCarFacade implements CarFacade {
    private final CarRepository carRepository;

    DefaultCarFacade(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public UUID create(CreateCarCommand command) {
        UUID id = UUID.randomUUID();
        Car car = new Car(id, command.model, command.producer, command.productionYear, null);
        carRepository.save(new CarRecord(car));
        return id;
    }

    @Override
    public void changeOwner(ChangeCarOwnerCommand command) {
        Car car = carRepository.findById(command.id.toString()).map(CarRecord::toDomain).orElseThrow();
        car.changeOwner(command.owner);
        carRepository.update(new CarRecord(car));
    }

    @Override
    public List<CarSnapshot> list() {
        return stream(carRepository.findAll().spliterator(), false)
                .map(CarRecord::toDomain)
                .map(Car::toSnapshot)
                .collect(toList());
    }
}
