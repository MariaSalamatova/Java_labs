import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

public class Main {

private static final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8); 
private static final Journal journal = new Journal(); 

public static void main(String[] args) {
System.out.println("Mentor's Journal"); 
try {
while (true) {
printMenu(); 
String choice = scanner.nextLine().trim(); 
switch (choice) {
case "1" -> addEntry(); 
case "2" -> showEntries(); 
case "0" -> {
System.out.println("Goodbye!"); 
return; 
}
default -> System.out.println("Unknown menu option. Please try again."); 
}
}
} catch (NoSuchElementException e) {
System.out.println("\nInput ended. Goodbye!"); 
}
}

private static void printMenu() {
System.out.println(); 
System.out.println("1 - Add entry"); 
System.out.println("2 - Show all entries"); 
System.out.println("0 - Exit"); 
System.out.print("Your choice: "); 
}

private static void addEntry() {
System.out.println("\nNew Entry"); 

String lastName     = readField("Student's last name: ", InputValidator::parseName); 
String firstName    = readField("Student's first name: ", InputValidator::parseName); 
LocalDate birthDate = readField("Date of birth (dd.mm.yyyy): ", InputValidator::parseBirthDate); 
String phone        = readField("Phone (+380XXXXXXXXX or 0XXXXXXXXX): ", InputValidator::parsePhone);
String street = readField("Street: ", InputValidator::parseStreet); 
String house = readField("House: ", InputValidator::parseHouse); 
String apartment = readField("Apartment: ", InputValidator::parseApartment); 

journal.add(new JournalEntry(lastName, firstName, birthDate, phone, street, house, apartment)); 
System.out.println("Record added successfully."); 
} 

private static <T> T readField(String prompt, Function<String, T> parser) { 
while (true) { 
System.out.print(prompt); 
String line = scanner.nextLine(); 
try { 
return parser.apply(line); 
} catch (IllegalArgumentException e) { 
System.out.println("Error:" + e.getMessage() + "Try again."); 
} 
} 
} 

private static void showEntries() { 
if (journal.isEmpty()) { 
System.out.println("\nLog is empty."); 
return; 
} 
List<JournalEntry> entries = journal.getEntries(); 
System.out.println("\n Log entries (" + entries.size() + ")"); 
for (int i = 0; i < entries.size(); i++) { 
System.out.println("\n#" + (i + 1)); 
System.out.println(entries.get(i)); 
} 
}
}