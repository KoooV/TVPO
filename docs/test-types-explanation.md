# Виды тестов в проекте Voting System

## 📋 Таблица всех тестов

| Тип | Файл | Уровень | Назначение |
|-----|------|---------|-----------|
| **Unit Tests** | `PollTests.java` | Модель | Тестирует класс Poll изолированно |
| **Unit Tests** | `PollServiceTests.java` | Сервис | Тестирует класс PollService изолированно |
| **SDD (Specification by Example)** | `PollResultsTableTests.java` | Логика | Табличные тесты с разными сценариями |
| **ATDD (Acceptance Test)** | `PollApiAtddTests.java` | API | Тестирует REST API endpoints |
| **Integration Test** | `VotingSystemApplicationTests.java` | Приложение | Проверяет загрузку Spring контекста |
| **BDD (Behavior Driven)** | `CucumberIT.java` + `voting.feature` | E2E | Сценарии на естественном языке |

---

## 1️⃣ UNIT TESTS (Модульные тесты)

### Что это?
Тесты **одного класса** в изоляции от остального кода. Не используют БД, сеть, другие сервисы.

### Ваши примеры:
- **PollTests.java** — тестирует класс `Poll` (модель данных)
- **PollServiceTests.java** — тестирует класс `PollService` (бизнес-логика)

### Назначение каждого теста в PollTests.java:

| Тест | Проверяет | Формат (Given/When/Then) |
|------|-----------|------------------------|
| `constructorInitializesOptions()` | Конструктор правильно инициализирует опции, убирает дубли и null | Given: список с дублями и null → When: создаётся Poll → Then: результат содержит только уникальные опции |
| `constructorWithoutOptionsThrows()` | При пустом списке опций бросается исключение | Given: пустой список → When: создаём Poll → Then: IllegalArgumentException |
| `registerVoteIncrementsCount()` | Каждый голос увеличивает счётчик | Given: Poll с опциями A,B → When: 3 голоса (A,A,B) → Then: A=2, B=1 |
| `duplicateVoteThrows()` | Повторный голос того же участника запрещён | Given: Poll и голос u1→A → When: u1 голосует снова за B → Then: DuplicateVoteException |
| `unknownOptionThrows()` | Голос за неизвестную опцию запрещён | Given: Poll с опцией A → When: голос за X → Then: UnknownOptionException |
| `closedPollThrows()` | После закрытия голоса запрещены | Given: Poll закрыт → When: попытка голоса → Then: PollClosedException |
| `resultsAreImmutable()` | Результаты нельзя изменять | Given: Map результатов → When: попытка put() → Then: UnsupportedOperationException |

### Плюсы:
✅ Быстро выполняются  
✅ Легко отладить (всё в одном классе)  
✅ Не требуют БД/сети  

### Минусы:
❌ Не проверяют интеграцию между компонентами  

---

## 2️⃣ SDD TESTS (Specification by Example)

### Что это?
**Табличное тестирование** — один метод запускается несколько раз с разными наборами входных данных.

### Ваш пример: PollResultsTableTests.java

```java
@ParameterizedTest(name = "A={0}, B={1} -> ожидается A={2}, B={3}")
@CsvSource({
    "'A,A,B', 'A,B', 2, 1",      // Сценарий 1
    "'B,B,B', 'A,B', 0, 3",      // Сценарий 2
    "'A',      'A,B', 1, 0"      // Сценарий 3
})
void tableDriven(String votesCsv, String optionsCsv, int expectedA, int expectedB) { ... }
```

### Что проверяет:

| Сценарий | Голоса | Опции | Ожидание | Смысл |
|----------|--------|-------|---------|-------|
| 1 | A,A,B | A,B | A=2, B=1 | Большинство голосов за A |
| 2 | B,B,B | A,B | A=0, B=3 | Единогласное голосование за B |
| 3 | A | A,B | A=1, B=0 | Один голос |

### Назначение:
🎯 **Проверить разные входные данные в одном тесте**  
Эквивалент трёх отдельных Unit Tests, но в одном методе.

### Плюсы:
✅ Компактность — много сценариев в одном методе  
✅ Легко добавить новый сценарий (новая строка в @CsvSource)  
✅ Покрывает граничные случаи  

### Минусы:
❌ Если один сценарий падает, могут скрыться ошибки в других  

---

## 3️⃣ ATDD TESTS (Acceptance Test Driven Development)

### Что это?
Тесты **бизнес-сценариев** — проверяют **требования клиента** через готовый код.

### Ваши примеры:

#### A. PollApiAtddTests.java
Тестирует **REST API** с использованием `MockMvc` (имитация HTTP запросов без реального сервера).

```java
@Test
@DisplayName("Условие: создан опрос; Действие: участник голосует дважды; 
            Ожидаемый результат: первый запрос 202, второй 400")
void eachVoteCountedOnce() throws Exception {
    // Условие (Given)
    Poll mockPoll = new Poll("Best?", List.of("A", "B"));
    when(pollService.createPoll(...)).thenReturn(mockPoll);
    
    // Действие (When)
    mockMvc.perform(post("/polls/...").contentType(MediaType.APPLICATION_JSON)...)
    
    // Проверка (Then)
    .andExpect(status().isAccepted()) // 202
}
```

**Назначение:**
- ✅ Проверяет **API контракт** (какой статус код вернуть)
- ✅ Проверяет **ошибки** (400 при дубле голоса)
- ✅ Проверяет **интеграцию** API с сервисом

#### B. PollTests.java и PollServiceTests.java
Хотя они **Unit Tests**, они следуют **ATDD подходу** благодаря стилю написания:

```
Given (Условие) → When (Действие) → Then (Проверка)
```

**Пример:**
```java
@Test
@DisplayName("Регистрация: каждый голос увеличивает счёт выбранной опции")
void registerVoteIncrementsCount() {
    // Given (Условие)
    var poll = new Poll("Q", List.of("A", "B"));
    
    // When (Действие)
    poll.registerVote("u1", "A");
    poll.registerVote("u2", "A");
    poll.registerVote("u3", "B");
    
    // Then (Проверка)
    Map<String, Integer> results = poll.getResults();
    assertThat(results).containsEntry("A", 2).containsEntry("B", 1);
}
```

### Плюсы:
✅ Пишутся **перед кодом** (TDD дисциплина)  
✅ Проверяют **требования**, а не реализацию  
✅ Служат **документацией** для разработчиков  

### Минусы:
❌ Медленнее Unit Tests (особенно API тесты)  
❌ Зависят от структуры кода  

---

## 4️⃣ INTEGRATION TESTS

### Что это?
Тесты **целого приложения** — загружают Spring контекст и проверяют, что всё работает вместе.

### Ваш пример: VotingSystemApplicationTests.java

```java
@SpringBootTest
class VotingSystemApplicationTests {
    @Test
    void contextLoads() {
        // Просто проверяет, что приложение запускается
    }
}
```

**Назначение:**
- ✅ Проверяет, что Spring контекст правильно инициализируется
- ✅ Все бины создаются без ошибок
- ✅ Нет конфликтов зависимостей

### Плюсы:
✅ Проверяет интеграцию **всех компонентов**  
✅ Ловит ошибки в конфигурации Spring  

### Минусы:
❌ Медленные  
❌ Требуют полного контекста приложения  

---

## 5️⃣ BDD TESTS (Behavior Driven Development)

### Что это?
Тесты на **естественном языке** (Gherkin) — пишут **бизнес-аналитики**, тестировщики и разработчики **вместе**.

### Ваш пример:

**Файл: `voting.feature`**
```gherkin
Feature: Голосование
  Scenario: Учет голоса участника
    Given создано голосование с вопросом "Лучший цвет" и опциями "Красный, Синий"
    When участник "user1" голосует за "Красный"
    Then результат для "Красный" равен 1
```

**Реализация: `VotingStepDefinitions.java`**
```java
@Given("создано голосование с вопросом {string} и опциями {string}")
public void createPoll(String question, String optionsCsv) {
    List<String> options = Arrays.stream(optionsCsv.split(",")).map(String::trim).toList();
    var poll = pollService.createPoll(question, options);
    this.pollId = poll.getId();
}

@When("участник {string} голосует за {string}")
public void vote(String user, String option) {
    pollService.vote(pollId, user, option);
}

@Then("результат для {string} равен {int}")
public void thenResult(String option, Integer expected) {
    Map<String, Integer> results = pollService.results(pollId);
    Assertions.assertThat(results.get(option)).isEqualTo(expected);
}
```

### Запуск:
```bash
./gradlew test --tests com.kov.votingsystem.bdd.BddRunner
```

### Плюсы:
✅ **Доступны бизнесс-людям** (без кода)  
✅ **Документируют требования** на человеческом языке  
✅ **Выявляют недопонимание** между командой и бизнесом  

### Минусы:
❌ Медленные  
❌ Требуют синхронизации Gherkin и кода  
❌ Сложнее отладить  

---

## 📊 Пирамида тестов (Test Pyramid)

```
       🏔️ E2E (BDD, Integration)  — медленные, редкие
      /  \
     /    \ 
    /      \
   / ATDD   \  — средняя скорость
  /          \
 /            \
/ SDD + Unit   \  — быстрые, много
/________________\
```

**Соотношение в вашем проекте:**
- 🔵 Unit Tests: **7** тестов (PollTests, PollServiceTests) — БЫСТРЫЕ
- 🟢 SDD Tests: **3** сценария (PollResultsTableTests) — БЫСТРЫЕ
- 🟡 ATDD Tests: **2-3** API теста (PollApiAtddTests) — СРЕДНИЕ
- 🔴 BDD Tests: **1** сценарий (CucumberIT) — МЕДЛЕННЫЕ
- 🟣 Integration: **1** тест (VotingSystemApplicationTests) — МЕДЛЕННЫЕ

---

## 🎯 Коротко: Когда использовать каждый тип?

| Тип | Когда писать | Примеры в вашем проекте |
|-----|--------------|----------------------|
| **Unit** | Для каждого класса/метода | Poll, PollService |
| **SDD** | Много сценариев одной функции | Разные комбинации голосов |
| **ATDD** | API endpoints, требования клиента | REST контроллер |
| **BDD** | Согласование с бизнесом | Фичи на русском |
| **Integration** | Конфигурация Spring | Проверка запуска приложения |

---

## 📝 Запуск тестов

```bash
# Все тесты
./gradlew test

# Только Unit Tests
./gradlew test --tests "*Tests" --exclude-task "*IT"

# Только Integration Tests  
./gradlew test --tests "*IT"

# Только BDD
./gradlew test --tests com.kov.votingsystem.bdd.BddRunner

# Отчёт покрытия
./gradlew test jacocoTestReport
```

---

## ✅ Итого

В вашем проекте используется **полная стратегия тестирования**:
1. **Unit Tests** — быстро проверяют логику
2. **SDD Tests** — покрывают разные сценарии
3. **ATDD Tests** — проверяют требования на уровне API
4. **BDD Tests** — согласуют требования с бизнесом
5. **Integration Tests** — проверяют запуск приложения

Это обеспечивает **высокое качество** кода с разных углов зрения! 🎯

