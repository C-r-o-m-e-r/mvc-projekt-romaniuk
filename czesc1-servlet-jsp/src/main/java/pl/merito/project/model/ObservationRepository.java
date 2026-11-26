package pl.merito.project.model;

import java.util.ArrayList;
import java.util.List;

// Proste repozytorium w pamięci serwera; dane znikają po zatrzymaniu aplikacji.
public final class ObservationRepository {

    private static final List<Observation> OBSERVATIONS = new ArrayList<>();

    private ObservationRepository() {
    }

    public static synchronized void add(Observation observation) {
        OBSERVATIONS.add(observation);
    }

    public static synchronized List<Observation> findAll() {
        return List.copyOf(OBSERVATIONS);
    }

    public static synchronized int size() {
        return OBSERVATIONS.size();
    }
}
