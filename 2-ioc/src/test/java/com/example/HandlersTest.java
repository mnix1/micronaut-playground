package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//IOC-test-5
@MicronautTest
class HandlersTest {

    private final MovementHistory movementHistory;

    private final MovementService movementService;

    HandlersTest(MovementHistory movementHistory, MovementService movementService) {
        this.movementHistory = movementHistory;
        this.movementService = movementService;
    }

    @Test
    void handles() {
        //when
        movementService.handle(new WalkCommand("EAST"));
        movementService.handle(new RunCommand("NORTH", 10));
        movementService.handle(new RunCommand("NORTH", 30));
        movementService.handle(new WalkCommand("EAST"));
        movementService.handle(new SitCommand());
        //then
        assertThat(movementHistory.actions).containsExactly("WALK EAST", "RUN NORTH 10", "RUN NORTH 30", "WALK EAST", "SIT");
    }

    @Singleton
    static class MovementService {

        private final List<MovementCommandHandler> movementCommandHandlers;

        MovementService(List<MovementCommandHandler> movementCommandHandlers) {
            this.movementCommandHandlers = movementCommandHandlers;
        }

        void handle(MovementCommand command) {
            movementCommandHandlers.forEach(handler -> handler.handle(command));
        }
    }

    @Singleton
    static class MovementHistory {

        List<String> actions = new ArrayList<>();

        void next(String action) {
            actions.add(action);
        }
    }

    interface MovementCommandHandler {
        void handle(MovementCommand command);
    }

    @Singleton
    static class SitCommandHandler implements MovementCommandHandler {

        private final MovementHistory history;

        SitCommandHandler(MovementHistory history) {
            this.history = history;
        }

        @Override
        public void handle(MovementCommand command) {
            if (command instanceof SitCommand) {
                history.next("SIT");
            }
        }
    }

    @Singleton
    static class MovingCommandHandler implements MovementCommandHandler {

        private final MovementHistory history;

        MovingCommandHandler(MovementHistory history) {
            this.history = history;
        }

        @Override
        public void handle(MovementCommand command) {
            if (command instanceof WalkCommand walkCommand) {
                history.next("WALK " + walkCommand.direction);
            }
            if (command instanceof RunCommand runCommand) {
                history.next("RUN " + runCommand.direction + " " + runCommand.speed);
            }
        }
    }

    interface MovementCommand {}

    record WalkCommand(String direction) implements MovementCommand {}

    record SitCommand() implements MovementCommand {}

    record RunCommand(String direction, int speed) implements MovementCommand {}
}
