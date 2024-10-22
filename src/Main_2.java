import java.util.Scanner;
abstract class Beverage {
    public final void prepareRecipe() {
        boilWater();
        brewOrSteep();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    private void boilWater() {
        System.out.println("Кипячение воды...");
    }

    private void pourInCup() {
        System.out.println("Наливание в чашку...");
    }

    protected abstract void brewOrSteep();
    protected abstract void addCondiments();

    protected boolean customerWantsCondiments() {
        return true; // По умолчанию добавки нужны
    }
}

class Tea extends Beverage {
    @Override
    protected void brewOrSteep() {
        System.out.println("Заваривание чая...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление лимона...");
    }
}


class Coffee extends Beverage {
    @Override
    protected void brewOrSteep() {
        System.out.println("Заваривание кофе...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление сахара и молока...");
    }

    @Override
    protected boolean customerWantsCondiments() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Хотите добавить сахар и молоко? (y/n): ");
        String answer = scanner.nextLine().toLowerCase();
        return answer.equals("y");
    }
}

class HotChocolate extends Beverage {
    @Override
    protected void brewOrSteep() {
        System.out.println("Подогревание шоколадного молока...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление маршмеллоу...");
    }

    @Override
    protected boolean customerWantsCondiments() {
        return false;
    }
}


public class Main_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите напиток для приготовления:");
        System.out.println("1 - Чай");
        System.out.println("2 - Кофе");
        System.out.println("3 - Горячий шоколад");
        System.out.print("Ваш выбор: ");
        int choice = scanner.nextInt();

        Beverage beverage = null;

        switch (choice) {
            case 1:
                beverage = new Tea();
                break;
            case 2:
                beverage = new Coffee();
                break;
            case 3:
                beverage = new HotChocolate();
                break;
            default:
                System.out.println("Неверный выбор! Попробуйте снова.");
                return;
        }

        System.out.println("\nПриготовление вашего напитка:");
        beverage.prepareRecipe();
    }
}
