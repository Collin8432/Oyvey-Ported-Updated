package mio.example.event;

public class Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }
}
