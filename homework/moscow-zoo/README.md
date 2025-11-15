# Moscow Zoo Management System

Система управления Московским зоопарком - консольное приложение для учета животных, предметов инвентаря и управления зоопарком.

## Архитектура и основные идеи

### Доменная модель
Система разделена на две основные сущности:
- **Животные (Animals)** - живые существа зоопарка
- **Вещи (Things)** - предметы инвентаря и оборудование

### Паттерны проектирования

#### 1. Builder Pattern
Используется для создания сложных объектов животных и вещей:
- `AnimalBuilderFactory` - фабрика строителей животных
- `ThingBuilderFactory` - фабрика строителей вещей
- Специфические билдеры для каждого типа животных (ElephantBuilder, LionBuilder и т.д.)

#### 2. Repository Pattern
- `AnimalRepository` и `ThingsRepository` для управления данными
- InMemory реализации для хранения данных в памяти

#### 3. Service Layer
Сервисы разделены по ответственности:
- `AnimalRegistrationService` - регистрация животных
- `ThingRegistrationService` - регистрация вещей
- `FoodCalculationService` - расчет питания
- `VeterinaryClinic` - ветеринарные услуги

### Классификация животных
- **Herbivore** (травоядные) - Rabbit, Elephant, Giraffe, Monkey
- **Predator** (хищники) - Fox, Lion, Tiger, Wolf
- Каждое животное имеет уникальные характеристики (длина хобота, грива, полосы и т.д.)

### Система инвентаризации
- Автоматическая генерация инвентарных номеров
- Раздельные счетчики для животных (1000+) и вещей (2000+)
- Учет специфических параметров (площадь клетки, вместимость мисок)

## Применение принципов SOLID

### S - Single Responsibility Principle (Принцип единственной ответственности)
- `AnimalRegistrationService` - отвечает только за регистрацию животных
- `FoodCalculationService` - отвечает только за расчет питания
- `VeterinaryClinic` - отвечает только за проверку здоровья
- `ConsolePrinter` - отвечает только за вывод информации
- `InputValidator` - отвечает только за валидацию ввода

### O - Open/Closed Principle (Принцип открытости/закрытости)
```java
// Легко добавить новый тип животного без изменения существующего кода
public interface AnimalBuilder<T extends AnimalBuilder<T>> {
    // Базовый интерфейс, расширяемый для конкретных животных
}

// Новый тип животного можно добавить через:
// 1. Новый builder, реализующий AnimalBuilder
// 2. Добавление в AnimalType enum
// 3. Добавление в AnimalBuilderFactory
```

### L - Liskov Substitution Principle (Принцип подстановки Лисков)
```java
// Все животные могут быть заменены базовым классом Animal
List<Animal> animals = Arrays.asList(
    new Elephant(...), 
    new Lion(...), 
    new Rabbit(...)
);

// Все билдеры могут быть заменены базовыми интерфейсами
AnimalBuilder<?> builder = AnimalBuilderFactory.create(AnimalType.ELEPHANT);
ThingBuilder<?> thingBuilder = ThingBuilderFactory.create(ThingType.CAGE);
```
### I - Interface Segregation Principle (Принцип разделения интерфейсов)
```java
// Раздельные интерфейсы для разных типов строителей
public interface AnimalBuilder<T extends AnimalBuilder<T>> { ... }
public interface ThingBuilder<T extends ThingBuilder<T>> { ... }

// Специфические интерфейсы для конкретных животных
public interface ElephantBuilder extends AnimalBuilder<ElephantBuilder> {
    ElephantBuilder withTrunkLength(double trunkLength);
    ElephantBuilder withTuskLength(double tuskLength);
}
```

### D - Dependency Inversion Principle (Принцип инверсии зависимостей)
```java
// Зависимости от абстракций, а не от реализаций
@Service
public class AnimalRegistrationService {
    private final AnimalRepository animalRepository;  // Зависимость от интерфейса
    private final VeterinaryClinic veterinaryClinic;  // Зависимость от интерфейса
    
    public AnimalRegistrationService(AnimalRepository animalRepository, 
                                   VeterinaryClinic veterinaryClinic) {
        this.animalRepository = animalRepository;
        this.veterinaryClinic = veterinaryClinic;
    }
}

// Внедрение зависимостей через Spring DI контейнер
@Component
public class ApplicationRunner implements CommandLineRunner {
    private final ConsoleMenu consoleMenu;  // DI через конструктор
    
    public ApplicationRunner(ConsoleMenu consoleMenu) {
        this.consoleMenu = consoleMenu;
    }
}
```

## Реализация функциональных требований
### 1. Добавление новых животных
Используется паттерн Builder для пошагового создания животных \
Валидация всех вводимых параметров\
Автоматическая генерация инвентарных номеров

### 2. Проверка здоровья животных
   VeterinaryClinic сервис проверяет состояние здоровья\
Возможность принять или отказать в приеме животного\
Интеграция с процессом регистрации

### 3. Расчет количества еды
   FoodCalculationService вычисляет общее потребление пищи\
Разные алгоритмы расчета для травоядных и хищников\
Учет индивидуальных характеристик животных

### 4. Контактный зоопарк
   Фильтрация животных по уровню доброты (KindnessLevel)

Определение совместимости животных для контактного зоопарка

Безопасное взаимодействие с посетителями

## Структура проекта
```
src/main/java/hse/moscowzoo/
├── builders/              # Паттерн Builder
│   ├── interfaces/        # Интерфейсы билдеров
│   |                      # Реализации билдеров
├── domain/                # Доменная модель
│   ├── entities/          # Сущности
│   │   ├── alive/         # Животные
│   │   └── things/        # Вещи
│   ├── valueobjects/      # Value Objects
│   └── dto/               # Data Transfer Objects
├── repositories/          # Паттерн Repository
├── services/              # Бизнес-логика
├── ui/                    # Пользовательский интерфейс
├── utils/                 # Вспомогательные классы
└── runner/                # Запуск приложения
```

## Инструкция по запуску
### Требования
Java 21 или выше\
Gradle 8.0 или выше


#### 1. Клонирование репозитория
```bash
git clone <repository-url>
cd moscow-zoo
```

#### 2. Сборка проекта
```bash
./gradlew build
```

#### 3. Запуск приложения
```bash
./gradlew bootRun
```

#### 4. Альтернативный запуск
```bash
java -jar build/libs/moscow-zoo-1.0-SNAPSHOT.jar
```

## Доступные функции в меню
✅ Добавление животных (8 видов)

✅ Просмотр всех животных

✅ Расчет питания

✅ Контактная информация зоопарка

✅ Добавление вещей (4 типа)

✅ Просмотр всех вещей

✅ Полная инвентаризация

## Покрытие кода и Jacoco отчеты
### Текущее покрытие кода
65% - базовое покрытие основных функциональных сценариев.

### Генерация отчетов Jacoco
#### 1. Запуск тестов с покрытием
```bash
./gradlew test jacocoTestReport
```
#### 2. Просмотр отчетов
```
HTML отчет: build/reports/jacoco/test/html/index.html
XML отчет: build/reports/jacoco/test/jacocoTestReport.xml
```
### Проверка стиля кода
```bash
./gradlew checkstyleMain checkstyleTest
```


