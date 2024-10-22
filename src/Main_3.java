import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
interface IMediator {
    void sendMessage(String message, User sender);
    void sendPrivateMessage(String message, User sender, String receiverName);
    void addUser(User user);
    void removeUser(User user);
}

class ChatRoom implements IMediator {
    private Map<String, User> users = new HashMap<>();

    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users.values()) {
            if (!user.equals(sender)) {
                user.receiveMessage(message, sender.getName());
            }
        }
    }

    @Override
    public void sendPrivateMessage(String message, User sender, String receiverName) {
        User receiver = users.get(receiverName);
        if (receiver != null) {
            receiver.receivePrivateMessage(message, sender.getName());
        } else {
            sender.receiveMessage("Пользователь с именем " + receiverName + " не найден.", "System");
        }
    }

    @Override
    public void addUser(User user) {
        users.put(user.getName(), user);
        sendMessage("Пользователь " + user.getName() + " присоединился к чату.", user);
    }

    @Override
    public void removeUser(User user) {
        users.remove(user.getName());
        sendMessage("Пользователь " + user.getName() + " покинул чат.", user);
    }
}
abstract class User {
    protected IMediator mediator;
    protected String name;

    public User(IMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void sendMessage(String message);
    public abstract void sendPrivateMessage(String message, String receiverName);
    public abstract void receiveMessage(String message, String senderName);
    public abstract void receivePrivateMessage(String message, String senderName);
}

class ChatUser extends User {
    public ChatUser(IMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(name + " отправляет сообщение: " + message);
        mediator.sendMessage(message, this);
    }

    @Override
    public void sendPrivateMessage(String message, String receiverName) {
        System.out.println(name + " отправляет личное сообщение " + receiverName + ": " + message);
        mediator.sendPrivateMessage(message, this, receiverName);
    }

    @Override
    public void receiveMessage(String message, String senderName) {
        System.out.println(name + " получил сообщение от " + senderName + ": " + message);
    }

    @Override
    public void receivePrivateMessage(String message, String senderName) {
        System.out.println(name + " получил ЛИЧНОЕ сообщение от " + senderName + ": " + message);
    }
}


public class Main_3 {
    public static void main(String[] args) {
        IMediator chatRoom = new ChatRoom();
        User alice = new ChatUser(chatRoom, "Alice");
        User bob = new ChatUser(chatRoom, "Bob");
        User charlie = new ChatUser(chatRoom, "Charlie");
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        System.out.println("Добро пожаловать в чат! Доступные команды: ");
        System.out.println("1 - Отправить сообщение");
        System.out.println("2 - Отправить личное сообщение");
        System.out.println("3 - Выйти из чата");
        while (running) {
            System.out.print("\nВыберите команду: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите сообщение: ");
                    String message = scanner.nextLine();
                    alice.sendMessage(message);
                    break;

                case 2:
                    System.out.print("Введите имя получателя: ");
                    String receiver = scanner.nextLine();
                    System.out.print("Введите сообщение: ");
                    String privateMessage = scanner.nextLine();
                    alice.sendPrivateMessage(privateMessage, receiver);
                    break;

                case 3:
                    System.out.println("Пользователь Alice покидает чат...");
                    chatRoom.removeUser(alice);
                    running = false;
                    break;

                default:
                    System.out.println("Неверная команда. Попробуйте снова.");
                    break;
            }
        }

        scanner.close();
        System.out.println("Программа завершена.");
    }
}

