# Voting System API

## Обзор

Сервис предоставляет REST API для создания и ведения голосований:
- создание голосования с вопросом и набором опций;
- регистрация голосов участников;
- получение результатов;
- закрытие голосования.

API возвращает и принимает данные в формате JSON. Базовый путь: `/api/polls`.

## Сценарий использования

1. Создать голосование (`POST /api/polls`).
2. Раздать идентификатор голосования участникам.
3. Каждый участник отправляет голос (`POST /api/polls/{id}/votes`).
4. В любой момент организатор может получить результаты (`GET /api/polls/{id}/results`).
5. При завершении голосования организатор закрывает его (`POST /api/polls/{id}/close`).

## Эндпоинты

### POST `/api/polls`

Создаёт новое голосование.

**Пример запроса**
```json
{
  "question": "Какой язык использовать?",
  "options": ["Java", "Kotlin", "Go"]
}
```

**Успешный ответ (200 OK)**
```json
{
  "id": "6d5a4f69-7eae-45f3-9622-b6695775ddef",
  "question": "Какой язык использовать?",
  "options": ["Java", "Kotlin", "Go"]
}
```

**Ошибки**
- `400 Bad Request` — список опций пуст или содержит некорректные значения.

### POST `/api/polls/{id}/votes`

Регистрирует голос участника.

**Пример запроса**
```json
{
  "participantId": "user123",
  "option": "Java"
}
```

**Успешный ответ**
- `202 Accepted` — голос учтён.

**Ошибки**
- `400 Bad Request` — участник уже голосовал или опция отсутствует в голосовании.
- `404 Not Found` — голосование не найдено.
- `409 Conflict` — голосование закрыто.

### GET `/api/polls/{id}/results`

Возвращает текущие результаты.

**Пример ответа (200 OK)**
```json
{
  "Java": 5,
  "Kotlin": 2,
  "Go": 1
}
```

**Ошибки**
- `404 Not Found` — голосование не найдено.

### POST `/api/polls/{id}/close`

Закрывает голосование. После вызова новые голоса не принимаются.

**Успешный ответ**
- `202 Accepted`.

**Ошибки**
- `404 Not Found` — голосование не найдено.

## Модели данных

| Объект | Поле | Тип | Описание |
| --- | --- | --- | --- |
| CreatePollRequest | question | string | Текст вопроса |
|  | options | array(string) | Список допустимых ответов (минимум один) |
| CreatePollResponse | id | string | Идентификатор голосования |
|  | question | string | Текст вопроса |
|  | options | array(string) | Опции голосования |
| VoteRequest | participantId | string | Идентификатор участника |
|  | option | string | Выбранная опция |

## Статусы и ошибки

| Код | Значение | Когда возвращается |
| --- | --- | --- |
| 200 | OK | Успешное создание голосования / получение результатов |
| 202 | Accepted | Голос принят или голосование закрыто |
| 400 | Bad Request | Недопустимый голос / некорректные входные данные |
| 404 | Not Found | Голосование не существует |
| 409 | Conflict | Голосование закрыто |

Тело ответа при ошибке содержит строку с описанием проблемы.

## Примеры c `curl`

```bash
# Создание голосования
curl -X POST http://localhost:8080/api/polls \
  -H "Content-Type: application/json" \
  -d '{"question":"Лучший цвет?","options":["Красный","Синий"]}'

# Голосование
curl -X POST http://localhost:8080/api/polls/{pollId}/votes \
  -H "Content-Type: application/json" \
  -d '{"participantId":"user1","option":"Красный"}'

# Получение результатов
curl http://localhost:8080/api/polls/{pollId}/results
```

## Тестирование

- Unit-тесты (TDD): `PollServiceTests`.
- ATDD MockMvc: `PollApiAtddTests`.
- BDD Cucumber: `src/test/resources/features/voting.feature`, степы `VotingStepDefinitions`.
- SDD (табличные сценарии): `PollResultsTableTests`.

Запуск:
```bash
./gradlew test
```

