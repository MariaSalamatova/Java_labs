import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Journal {
    private final List<JournalEntry> entries = new ArrayList<>();

    public void add(JournalEntry entry) {
        entries.add(entry);
    }

    public List<JournalEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }
}