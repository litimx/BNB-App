package learn.masteryweek.ui;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleIO {
    //Scanner scanner
    private final Scanner scanner = new Scanner(System.in);
    //DateTimeFormatter
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    //Methods:
    //readString
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    //readRequiredString
    public String readRequiredString(String prompt) {
        String result;
        do {
            result = readString(prompt).trim();
            if (result.length() == 0) {
                System.out.println("Value is required.");
            }
        } while (result.length() == 0);
        return result;

    }

    //readInt
    public int readInt(String prompt, int min, int max) {
        int result;
        do {
            String value = readString(prompt);
            try {
                result = Integer.parseInt(value);
                if(result < min || result > max) {
                    System.out.println("Input is out of range. Please enter a number between " + min + " and " + max + ".");
                    result = min - 1;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Not a valid number.");
                result = min - 1;
            }
        } while (result < min || result > max);
        return result;
    }

    //readBoolean
    public boolean readBoolean(String prompt) {
        boolean result = false;
        do {
            String value = readString(prompt).trim().toLowerCase();
            if (value.equals("y")) {
                result = true;
                break;
            } else if (value.equals("n")) {
                result = false;
                break;
            } else {
                System.out.println("Please enter 'y' or 'n'.");
            }
        } while (true);
        return result;
    }

    //readLocalDate
    public LocalDate readLocalDate(String prompt) {
        LocalDate result = null;
        do {
            String value = readString(prompt);
            try{
                result = LocalDate.parse(value,dateFormatter);
            } catch (Exception ex) {
                System.out.println("You have entered an invalid date. Please try again.");
            }
        } while (result == null);
        return result;
    }


}
