package chess.domain.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTest {
    @DisplayName("start 커맨드가 들어오면 Start 상태를 반환한다.")
    @Test
    void start() {
        Command command = CommandFactory.initialCommand("start");

        assertThat(command).isInstanceOf(Start.class);
    }

    @DisplayName("start가 들어오고, move 커맨드가 들어오면 Move 상태로 변경한다.")
    @Test
    void move() {
        Command command = CommandFactory.initialCommand("start");
        Command move = command.execute("move");

        assertThat(move).isInstanceOf(Move.class);
    }

    @DisplayName("move를 한 뒤, Ready 상태로 변경한다.")
    @Test
    void ready() {
        Command command = CommandFactory.initialCommand("start");
        Command move = command.execute("move");

        assertThat(move.ready()).isInstanceOf(Ready.class);
    }

    @DisplayName("start를 한 뒤, end 커맨드가 들어오면 End 상태로 변경한다.")
    @Test
    void end() {
        Command command = CommandFactory.initialCommand("start");
        Command end = command.execute("end");

        assertThat(end).isInstanceOf(End.class);
    }

    @DisplayName("start를 한 뒤, status 커맨드가 들어오면 Status 상태로 변경한다.")
    @Test
    void status() {
        Command command = CommandFactory.initialCommand("start");
        Command status = command.execute("status");

        assertThat(status).isInstanceOf(Status.class);
    }

    @DisplayName("start 상태에서 옳지 않은 커맨드가 들어오면 예외가 발생한다.")
    @Test
    void startException() {
        Command command = CommandFactory.initialCommand("start");

        assertThatThrownBy(() -> command.execute("mive"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("ready 상태에서 옳지 않은 커맨드가 들어오면 예외가 발생한다.")
    @Test
    void readyException() {
        Command command = CommandFactory.initialCommand("start");
        Command move = command.execute("move");
        Command ready = move.ready();

        assertThatThrownBy(() -> ready.execute("mive"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("move 상태에서 커맨드를 실행하려 하면 예외가 발생한다.")
    @Test
    void moveException() {
        Command command = CommandFactory.initialCommand("start");
        Command move = command.execute("move");

        assertThatThrownBy(() -> move.execute("end"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("end 상태에서 커맨드를 실행하려 하면 예외가 발생한다.")
    @Test
    void endException() {
        Command command = CommandFactory.initialCommand("start");
        Command end = command.execute("end");

        assertThatThrownBy(() -> end.execute("end"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("status 상태에서 커맨드를 실행하려 하면 예외가 발생한다.")
    @Test
    void statusException() {
        Command command = CommandFactory.initialCommand("start");
        Command status = command.execute("status");

        assertThatThrownBy(() -> status.execute("status"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}