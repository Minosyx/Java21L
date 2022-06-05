import java.io.*;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class Main
{
    public static void main(String[] arg) throws IOException {
        String fileName = "wizytowki";
        System.out.print("Podaj nazwę pliku do pracy: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String tmp = reader.readLine();
        if ( !tmp.isEmpty() ){
            fileName = tmp;
        }
        fileName += ".csv";
        File f = new File(fileName);
        if ( f.createNewFile() )
        {
            System.out.printf("Utworzono plik %s%n", fileName);
        }
        boolean running = true;
        while ( running )
        {
            int choice;
            System.out.print(printOpts());
            try
            {
                choice = parseInt(reader.readLine());
            }
            catch ( NumberFormatException e )
            {
                System.out.println("Brak liczby całkowitej we wczytanych danych!!!");
                e.printStackTrace();
                continue;
            }
            switch (choice) {
                case 1 -> {
                    if (f.canRead())
                        showElements(fileName, false);
                }
                case 2 -> {
                    if (f.canWrite())
                    addElement(fileName);
                }
                case 3 -> {
                    if (f.canRead())
                        showElements(fileName, true);
                }
                case 4 -> running = false;
                default -> System.out.println("Podano błędną opcję!");
            }
        }
    }

    public static String printOpts(){
        String text = "";
        text += "Wybierz opcję:\n";
        text += "\t1. Wyświetl wizytówki\n";
        text += "\t2. Dodaj nową wizytówkę\n";
        text += "\t3. Wyświetl wizytówki osób o określonym nazwisku\n";
        text += "\t4. Wyłącz program\n";
        text += "Twój wybór: ";
        return text;
    }

    public static void addElement(String fileName)
    {
        String line = "";
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("\nImię: ");
            line += reader.readLine() + ';';
            System.out.print("Nazwisko: ");
            line += reader.readLine() + ';';
            System.out.print("Tel.: ");
            line += reader.readLine() + ';';
            System.out.print("e-mail: ");
            line += reader.readLine() + '\n';
            System.out.print("\n");
            writeFile(fileName, line);
        }
        catch ( IOException e )
        {
            System.out.println("Ooops... Wystąpił błąd: ");
            e.printStackTrace();
        }
    }

    public static void showElements(String fileName, boolean filtered)
    {
        String filter = "";
        try
        {
            if ( filtered )
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Podaj nazwisko: ");
                filter = reader.readLine();
            }
            String text = readFile(fileName);
            String[] lines = text.split("\n");
            for (String line : lines) {
                String[] fields = line.split(";");
                if (line.equals("") || filtered && !fields[1].equals(filter)) {
                    continue;
                }
                System.out.printf("\nImię: %s%n", fields[0]);
                System.out.printf("Nazwisko: %s%n", fields[1]);
                System.out.printf("Tel.: %s%n", fields[2]);
                System.out.printf("e-mail: %s%n", fields[3]);
                System.out.print("\n");
            }
        }
        catch ( IOException e )
        {
            System.out.println("Ooops... Wystąpił błąd: ");
            e.printStackTrace();
        }
    }

    public static String readFile(String fileName)
    {
        String result = "";
        try
        {
            File myFile = new File(fileName);
            Scanner myScanner = new Scanner(myFile);
            while ( myScanner.hasNextLine() )
            {
                result += myScanner.nextLine() + "\r\n";
            }
            myScanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Ooops... Wystąpił błąd: ");
            e.printStackTrace();
        }
        return result;
    }

    public static void writeFile(String fileName, String line)
    {
        try
        {
            FileWriter fw = new FileWriter(fileName, true);
            fw.write(line);
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println("Ooops... Wystąpił błąd: ");
            e.printStackTrace();
        }
    }
}