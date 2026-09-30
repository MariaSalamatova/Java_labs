import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public final class InputValidator { 

private InputValidator() { } 

private static final Pattern NAME = Pattern.compile("^\\p{L}[\\p{L}'’ʼ\\-]{1,49}$");
private static final Pattern STREET = Pattern.compile("^[\\p{L}0-9][\\p{L}0-9 .'’ʼ\\-]{1,59}$");
private static final Pattern HOUSE = Pattern.compile("^[1-9]\\d{0,3}\\p{L}?(/[1-9]\\d{0,3})?$"); 
private static final Pattern APARTMENT = Pattern.compile("^[1-9]\\d{0,3}\\p{L}?$"); 
private static final Pattern PHONE = Pattern.compile("^(\\+38)?0\\d{9}$"); 

private static final DateTimeFormatter DATE_FORMAT = 
DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT); 

public static String parseName(String input) { 
String s = input.trim(); 
if (!NAME.matcher(s).matches()) { 
throw new IllegalArgumentException( 
"Only letters, apostrophes and hyphens are allowed (from 2 to 50 characters)."); 
} 
return s.substring(0, 1).toUpperCase() + s.substring(1); 
} 

public static LocalDate parseBirthDate(String input) { 
LocalDate date; 
try { 
date = LocalDate.parse(input.trim(), DATE_FORMAT); 
} catch (DateTimeParseException e) { 
throw new IllegalArgumentException( 
"Invalid date. Use dd.mm.yyyy format (for example, 03/25/2005)."); 
} 
LocalDate today = LocalDate.now(); 
if (date.isAfter(today)) { 
throw new IllegalArgumentException("Date of birth cannot be in the future."); 
} 
if (date.isBefore(today.minusYears(100))) { 
throw new IllegalArgumentException("Date of birth is too early (more than 100 years ago)."); 
} 
return date; 
} 

public static String parsePhone(String input) { 
String cleaned = input.replaceAll("[\\s()\\-]", ""); 
if (!PHONE.matcher(cleaned).matches()) { 
throw new IllegalArgumentException( 
"Invalid phone number. Format: +380XXXXXXXXX or 0XXXXXXXXX."); 
} 
return cleaned.startsWith("+38") ? cleaned : "+38" + cleaned; 
} 

public static String parseStreet(String input) { 
String s = input.trim().replaceAll("\\s+", " "); 
if (!STREET.matcher(s).matches()) { 
throw new IllegalArgumentException( 
"Street name: 2-60 characters (letters, numbers, space, period, hyphen)."); 
} 
return s; 
} 

public static String parseHouse(String input) { 
String s = input.trim(); 
if (!HOUSE.matcher(s).matches()) { 
throw new IllegalArgumentException( 
"Incorrect house number. Examples: 12, 12A, 12/2."); 
} 
return s; 
} 

public static String parseApartment(String input) { 
String s = input.trim(); 
if (!APARTMENT.matcher(s).matches()) { 
throw new IllegalArgumentException( 
"Wrong apartment number. Examples: 5, 5A."); 
} 
return s; 
}
}