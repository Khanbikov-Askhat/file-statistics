import java.util.List;
import java.util.ArrayList;

// Главный класс приложения
public class MainApplication {

    public static void main(String[] args) {
        System.out.println("Starting application...");

        // Создаем список
        List<String> items = new ArrayList<>();
        items.add("first");
        items.add("second");
        items.add("third");

        // Обрабатываем элементы
        for (String item : items) {
            processItem(item);
        }

        System.out.println("Application finished.");
    }

    /**
     * Обрабатывает один элемент
     */
    private static void processItem(String item) {
        if (item == null) {
            return; // пропускаем null
        }

        String processed = item.toUpperCase();
        System.out.println("Processed: " + processed);
    }
}