package com.han.frontend.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ScoreboardFacade {
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final int TIMEOUT_MS = 3000;

    private final Json json;

    public ScoreboardFacade() {
        json = new Json();
        json.setOutputType(JsonWriter.OutputType.json); // standard JSON with quoted field names
        json.setUsePrototypes(false);                   // always send every field, even a score of 0
        json.setIgnoreUnknownFields(true);              // tolerate extra fields the backend may add
    }

    // Request body of POST /api/scores: exactly the two fields the backend's Score entity expects
    private static class ScoreSubmission {
        private String playerName;
        private int point;

        private ScoreSubmission() {}

        private ScoreSubmission(String playerName, int point) {
            this.playerName = playerName;
            this.point = point;
        }
    }

    /** GET /api/health: reports true if the backend answered. */
    public void checkHealth(Consumer<Boolean> onResult) {
        send(get("/health"), (status, body) -> onResult.accept(status == HttpStatus.SC_OK),
            error -> onResult.accept(false));
    }

    /** POST /api/scores: stores one score. The backend's point column is an Integer, so huge scores are clamped. */
    public void submitScore(String playerName, long points, Consumer<ScoreEntry> onSaved, Consumer<String> onError) {
        int point = (int) Math.max(0L, Math.min(Integer.MAX_VALUE, points));
        HttpRequest request = new HttpRequestBuilder().newRequest()
            .method(HttpMethods.POST)
            .url(BASE_URL + "/scores")
            .header("Content-Type", "application/json")
            .content(json.toJson(new ScoreSubmission(playerName, point)))
            .timeout(TIMEOUT_MS)
            .build();

        send(request, (status, body) -> {
            if (status == HttpStatus.SC_CREATED) {
                onSaved.accept(json.fromJson(ScoreEntry.class, body));
            } else {
                onError.accept("Submit failed (HTTP " + status + ")");
            }
        }, onError);
    }

    /** GET /api/scores/leaderboard?limit=N: the top N scores, highest first. */
    @SuppressWarnings("unchecked")
    public void fetchLeaderboard(int limit, Consumer<List<ScoreEntry>> onLoaded, Consumer<String> onError) {
        send(get("/scores/leaderboard?limit=" + limit), (status, body) -> {
            if (status == HttpStatus.SC_OK) {
                Array<ScoreEntry> entries = json.fromJson(Array.class, ScoreEntry.class, body);
                List<ScoreEntry> result = new ArrayList<>();
                for (ScoreEntry entry : entries) {
                    result.add(entry);
                }
                onLoaded.accept(result);
            } else {
                onError.accept("Leaderboard failed (HTTP " + status + ")");
            }
        }, onError);
    }

    private interface ResponseHandler {
        void handle(int status, String body);
    }

    private HttpRequest get(String path) {
        return new HttpRequestBuilder().newRequest()
            .method(HttpMethods.GET)
            .url(BASE_URL + path)
            .timeout(TIMEOUT_MS)
            .build();
    }

    private void send(HttpRequest request, ResponseHandler onResponse, Consumer<String> onError) {
        if (Gdx.net == null) { // headless tests: no libGDX networking available
            onError.accept("Scoreboard offline");
            return;
        }
        Gdx.net.sendHttpRequest(request, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse response) {
                // Read on the network thread (the stream is only valid here), then hop to the render thread
                int status = response.getStatus().getStatusCode();
                String body = response.getResultAsString();
                onRenderThread(() -> {
                    try {
                        onResponse.handle(status, body);
                    } catch (RuntimeException e) {
                        onError.accept("Unreadable reply from backend");
                    }
                });
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("[ScoreboardFacade] Backend unreachable: " + t.getMessage());
                onRenderThread(() -> onError.accept("Scoreboard offline"));
            }

            @Override
            public void cancelled() {
                onRenderThread(() -> onError.accept("Request cancelled"));
            }
        });
    }

    private void onRenderThread(Runnable task) {
        if (Gdx.app != null) {
            Gdx.app.postRunnable(task);
        } else {
            task.run();
        }
    }
}
