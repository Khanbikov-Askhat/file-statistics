/**
 * Тестовый класс для проверки сбора статистики
 */
public class TestClass {
    private String name;
    private int value;

    // Конструктор по умолчанию
    public TestClass() {
        this.name = "default";
        this.value = 0;
    }

    /**
     * Конструктор с параметрами
     */
    public TestClass(String name, int value) {
        this.name = name;
        this.value = value;
    }

    // Геттер для имени
    public String getName() {
        return name;
    }

    // Сеттер для имени
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Метод для вычисления значения
     */
    public int calculate() {
        int result = value * 2;
        return result; // возвращаем результат
    }
}