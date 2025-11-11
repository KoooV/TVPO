package com.kov.votingsystem.model;

import com.kov.votingsystem.exception.DuplicateVoteException;
import com.kov.votingsystem.exception.PollClosedException;
import com.kov.votingsystem.exception.UnknownOptionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//ATDD tests
class PollTests {

    @Test
    @DisplayName("Конструктор: фильтрация опций, удаление дублей и инициализация счётчиков")
    void constructorInitializesOptions() {
        var poll = new Poll("Q", Arrays.asList("A", "A", " ", null, "B"));
        Map<String, Integer> results = poll.getResults();
        assertThat(results).containsKeys("A", "B").hasSize(2);
        assertThat(results.get("A")).isZero();
        assertThat(results.get("B")).isZero();
    }

    @Test
    @DisplayName("Конструктор: при отсутствии валидных опций бросается IllegalArgumentException")
    void constructorWithoutOptionsThrows() {
        assertThatThrownBy(() -> new Poll("Q", Arrays.asList(" ", null)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Регистрация: каждый голос увеличивает счёт выбранной опции")
    void registerVoteIncrementsCount() {
        var poll = new Poll("Q", List.of("A", "B"));
        poll.registerVote("u1", "A");
        poll.registerVote("u2", "A");
        poll.registerVote("u3", "B");

        Map<String, Integer> results = poll.getResults();
        assertThat(results).containsEntry("A", 2).containsEntry("B", 1);
    }

    @Test
    @DisplayName("Проверка дубля: повторный голос того же участника запрещён")
    void duplicateVoteThrows() {
        var poll = new Poll("Q", List.of("A", "B"));
        poll.registerVote("u1", "A");
        assertThatThrownBy(() -> poll.registerVote("u1", "B"))
            .isInstanceOf(DuplicateVoteException.class);
    }

    @Test
    @DisplayName("Проверка неизвестной опции: бросается UnknownOptionException")
    void unknownOptionThrows() {
        var poll = new Poll("Q", List.of("A"));
        assertThatThrownBy(() -> poll.registerVote("u1", "X"))
            .isInstanceOf(UnknownOptionException.class);
    }

    @Test
    @DisplayName("Закрытие опроса: после close() новые голоса запрещены")
    void closedPollThrows() {
        var poll = new Poll("Q", List.of("A"));
        poll.close();
        assertThatThrownBy(() -> poll.registerVote("u1", "A"))
            .isInstanceOf(PollClosedException.class);
    }

    @Test
    @DisplayName("Результаты: возвращаемая Map является неизменяемой")
    void resultsAreImmutable() {
        var poll = new Poll("Q", List.of("A"));
        Map<String, Integer> results = poll.getResults();
        assertThatThrownBy(() -> results.put("X", 1))
            .isInstanceOf(UnsupportedOperationException.class);
    }
}
