import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JournalEntry { 
private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy"); 

private final String lastName; 
private final String firstName; 
private final LocalDate birthDate; 
private final String phone; 
private final String street; 
private final String house; 
private final String apartment; 

public JournalEntry(String lastName, String firstName, LocalDate birthDate, 
String phone, String street, String house, String apartment) { 
this.lastName = lastName; 
this.firstName = firstName; 
this.birthDate = birthDate; 
this.phone = phone; 
this.street = street; 
this.house = house; 
this.apartment = apartment; 
} 

public String getLastName() { return lastName; } 
public String getFirstName() { return firstName; } 
public LocalDate getBirthDate() { return birthDate; } 
public String getPhone() { return phone; } 
public String getStreet() { return street; } 
public String getHouse() { return house; } 
public String getApartment() { return apartment; } 

@Override 
public String toString() { 
return String.format( 
"Student: %s %s%n" + 
"Date of birth: %s%n" + 
"Phone: %s%n" + 
"Address: street %s, building %s, apartment %s", 
lastName, firstName, birthDate.format(DATE_FORMAT), 
phone, street, house, apartment); 
}
}