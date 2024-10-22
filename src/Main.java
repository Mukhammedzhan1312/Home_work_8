import java.util.Stack;
import java.util.Scanner;
interface ICommand {
    void execute();
    void undo();
}


class Light {
    public void turnOn() {
        System.out.println("Свет включен.");
    }

    public void turnOff() {
        System.out.println("Свет выключен.");
    }
}

class Door {
    public void open() {
        System.out.println("Дверь открыта.");
    }

    public void close() {
        System.out.println("Дверь закрыта.");
    }
}
class Thermostat {
    private int temperature;

    public void increaseTemperature() {
        temperature++;
        System.out.println("Температура увеличена до " + temperature + "°C.");
    }

    public void decreaseTemperature() {
        temperature--;
        System.out.println("Температура уменьшена до " + temperature + "°C.");
    }
}

class LightOnCommand implements ICommand {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }
}

class LightOffCommand implements ICommand {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }
}
class DoorOpenCommand implements ICommand {
    private Door door;

    public DoorOpenCommand(Door door) {
        this.door = door;
    }

    @Override
    public void execute() {
        door.open();
    }

    @Override
    public void undo() {
        door.close();
    }
}

class DoorCloseCommand implements ICommand {
    private Door door;

    public DoorCloseCommand(Door door) {
        this.door = door;
    }

    @Override
    public void execute() {
        door.close();
    }

    @Override
    public void undo() {
        door.open();
    }
}
class IncreaseTemperatureCommand implements ICommand {
    private Thermostat thermostat;

    public IncreaseTemperatureCommand(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    @Override
    public void execute() {
        thermostat.increaseTemperature();
    }

    @Override
    public void undo() {
        thermostat.decreaseTemperature();
    }
}

class DecreaseTemperatureCommand implements ICommand {
    private Thermostat thermostat;

    public DecreaseTemperatureCommand(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    @Override
    public void execute() {
        thermostat.decreaseTemperature();
    }

    @Override
    public void undo() {
        thermostat.increaseTemperature();
    }
}

class Invoker {
    private Stack<ICommand> history = new Stack<>();

    public void executeCommand(ICommand command) {
        command.execute();
        history.push(command);
    }

    public void undoCommand() {
        if (!history.isEmpty()) {
            ICommand command = history.pop();
            command.undo();
        } else {
            System.out.println("Нет команд для отмены.");
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Light light = new Light();
        Door door = new Door();
        Thermostat thermostat = new Thermostat();
        ICommand lightOn = new LightOnCommand(light);
        ICommand lightOff = new LightOffCommand(light);
        ICommand doorOpen = new DoorOpenCommand(door);
        ICommand doorClose = new DoorCloseCommand(door);
        ICommand increaseTemp = new IncreaseTemperatureCommand(thermostat);
        ICommand decreaseTemp = new DecreaseTemperatureCommand(thermostat);

        Invoker invoker = new Invoker();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nВыберите команду:");
            System.out.println("1. Включить свет");
            System.out.println("2. Выключить свет");
            System.out.println("3. Открыть дверь");
            System.out.println("4. Закрыть дверь");
            System.out.println("5. Увеличить температуру");
            System.out.println("6. Уменьшить температуру");
            System.out.println("7. Отменить последнюю команду");
            System.out.println("8. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищаем буфер ввода

            switch (choice) {
                case 1:
                    invoker.executeCommand(lightOn);
                    break;
                case 2:
                    invoker.executeCommand(lightOff);
                    break;
                case 3:
                    invoker.executeCommand(doorOpen);
                    break;
                case 4:
                    invoker.executeCommand(doorClose);
                    break;
                case 5:
                    invoker.executeCommand(increaseTemp);
                    break;
                case 6:
                    invoker.executeCommand(decreaseTemp);
                    break;
                case 7:
                    invoker.undoCommand();
                    break;
                case 8:
                    System.out.println("Выход из приложения...");
                    running = false;
                    break;
                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }

        scanner.close();
    }
}
