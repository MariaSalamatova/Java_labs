import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

public class Main {
    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2026, 11, 15);
        Clock clock = Clock.fixed(today.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        CardRegistry registry = new CardRegistry();
        Turnstile turnstile = new Turnstile(registry, new BigDecimal("15.00"), clock);

        // Issuing cards
        Card student = registry.issueTermCard(CardCategory.STUDENT, CardType.MONTHLY, today.minusDays(5));
        Card expired = registry.issueTermCard(CardCategory.SCHOOL, CardType.TEN_DAYS, today.minusDays(30));
        Card trips5 = registry.issueTripCard(CardCategory.REGULAR, CardType.TRIPS_5);
        Card trips10 = registry.issueTripCard(CardCategory.SCHOOL, CardType.TRIPS_10);
        AccumulativeCard accum = registry.issueAccumulativeCard(CardCategory.REGULAR, new BigDecimal("40.00"));
        Card broken = registry.issueTermCard(CardCategory.REGULAR, CardType.MONTHLY, today);
        broken.setDamaged(true);
        Card foreign = new TripCard("TR-999999", CardCategory.REGULAR, CardType.TRIPS_5); // not in the registry

        System.out.println("=== Issued cards (" + registry.size() + ") ===");
        for (Card c : registry.all()) {
            System.out.println(c);
        }

        // Attempt to issue an invalid card
        try {
            registry.issueAccumulativeCard(CardCategory.STUDENT, new BigDecimal("100"));
        } catch (IllegalArgumentException e) {
            System.out.println("\nIssue rejected: " + e.getMessage());
        }

        System.out.println("\n=== Turnstile passages ===");
        attempt(turnstile, student);
        attempt(turnstile, student);
        attempt(turnstile, expired);
        for (int i = 0; i < 6; i++) {
            attempt(turnstile, trips5);   // 5 successful, the 6th is denied
        }
        attempt(turnstile, trips10);
        attempt(turnstile, accum);        // 40 -> 25
        attempt(turnstile, accum);        // 25 -> 10
        attempt(turnstile, accum);        // 10 < 15 - denied
        accum.topUp(new BigDecimal("30.00"));
        System.out.println("(card " + accum.getId() + " topped up by 30.00)");
        attempt(turnstile, accum);        // 40 -> 25
        attempt(turnstile, broken);
        attempt(turnstile, foreign);
        attempt(turnstile, null);

        System.out.println("\n=== Report: summary ===");
        System.out.println(turnstile.totalReport());
        System.out.println("\n=== Report: by travel card type ===");
        System.out.print(turnstile.byTypeReport());
    }

    private static void attempt(Turnstile turnstile, Card card) {
        String who = card == null ? "(no card)" : card.getId();
        System.out.println(who + ": " + turnstile.pass(card));
    }
}
