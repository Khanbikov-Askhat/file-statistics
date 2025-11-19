# File Statistics Utility

Консольная утилита для сбора статистики по файлам в директории.

## Описание

Утилита анализирует файлы в указанной директории и собирает статистику:
- Количество файлов по расширениям
- Общий размер файлов
- Общее количество строк
- Количество непустых строк
- Количество строк с комментариями (для Java и Bash)

## Требования

### Обязательные

- Java 11 или выше
- Maven 3.6+

## Сборка

### С использованием Maven

```bash
mvn clean package
```

## Использование


### Прямое использование

```bash
java -cp target/file-statistics-1.0-SNAPSHOT.jar src/main/java/com/rtk/filestatistics/Main.java <path> [опции]
```

### Параметры командной строки

- `<path>` - путь к директории для анализа (обязательный)
- `--recursive` - рекурсивный обход поддиректорий
- `--max-depth=<N>` - максимальная глубина рекурсии
- `--thread=<N>` - количество потоков для обработки
- `--include-ext=<ext1,ext2,...>` - включить только указанные расширения
- `--exclude-ext=<ext1,ext2,...>` - исключить указанные расширения
- `--git-ignore` - учитывать файл .gitignore (опционально)
- `--output=<plain|xml|json>` - формат вывода (по умолчанию: plain)

### Примеры

```bash
# Базовое использование
java -cp target/file-statistics-1.0-SNAPSHOT.jar src/main/java/com/rtk/filestatistics/Main.java /path/to/directory

# Рекурсивный обход с ограничением глубины
java -cp target/file-statistics-1.0-SNAPSHOT.jar src/main/java/com/rtk/filestatistics/Main.java /path/to/directory --recursive --max-depth=3

# Многопоточная обработка
java -cp target/file-statistics-1.0-SNAPSHOT.jar src/main/java/com/rtk/filestatistics/Main.java /path/to/directory --thread=4

# Фильтрация по расширениям
java -cp target/file-statistics-1.0-SNAPSHOT.jar src/main/java/com/rtk/filestatistics/Main.java /path/to/directory --include-ext=java,xml

# Вывод в JSON
java -cp target/file-statistics-1.0-SNAPSHOT.jar src/main/java/com/rtk/filestatistics/Main.java /path/to/directory --output=json
```

## Архитектура

Проект использует Clean Architecture с разделением на слои:
- **Domain** - доменные модели (FileStatistics, Arguments, OutputFormat)
- **Application** - бизнес-логика (FileProcessor, Command)
- **Infrastructure** - инфраструктурные компоненты (парсеры, коллекторы, фильтры, форматтеры)
- **Presentation** - представление (ArgumentParser)

## Паттерны проектирования

- **Builder** - для создания Arguments
- **Factory Method** - для создания CommentParser
- **Template Method** - в AbstractFileStatisticsCollector
- **Strategy** - для FileFilter и OutputFormatter
- **Composite** - для CompositeFileFilter
- **Observer** - для отслеживания прогресса обработки
- **Command** - для FileStatisticsCommand

## Тестирование

### С использованием Maven

```bash
mvn test
```

