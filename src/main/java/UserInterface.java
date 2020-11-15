import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    static String LIST_TICKETS = "1";
    static String SINGLE_TICKET = "2";
    static String QUIT = "Quit";


    private Scanner input;
    public UserInterface()
    {
        this.input = new Scanner(System.in);
    }

    /**
     * Method to allow the user to choose their next action.
     */
    public void show()
    {
        welcome();
        menu();
        while (input.hasNext()) {
            String prompt = input.next();
            if (prompt.equals(LIST_TICKETS)) {
                listTickets();
            }
            if (prompt.equals(SINGLE_TICKET)) {
                singleTicket();
            }
            if (prompt.equals(QUIT)) {
                break;
            } else {
                menu();
            }
        }
    }

    /**
     * Method that prints out the tickets the user has in blocks of 25.
     */
    private void listTickets()
    {
        List<Ticket> tickets = Api.getAllTickets();
        for (int i = 0; i < tickets.size()-1; i++) {
            Ticket ticket = tickets.get(i);
            ticket.print();
            if (i % 24 == 0 && i != 0) {
                System.out.println("TYPE next TO VIEW THE NEXT PAGE OF TICKETS");
                System.out.println("ENTER stop TO RETURN TO THE MAIN MENU");
                String next = validAction();
                if (next.equals("stop")) {
                    System.out.println("Returning to main menu....");
                    break;
                }

                System.out.println();


            }
        }
    }

    private String validAction() {
        String action = input.next();
        if(!action.equals("stop")&& !action.equals("next")){
            System.out.println("Invalid input!");
            return validAction();
        }
        return action;
    }



    /**
     * Method that takes and integer input from the user and outputs the ticket
     * that has an id that matches the integer, if it exists.
     */
    public void singleTicket()
    {
        int ticketNum = 0;
        List<Ticket> tickets = Api.getAllTickets();
        System.out.println("Enter the ticket number you would like to view");
        if(input.hasNextInt()){
            ticketNum = input.nextInt();
        }else{
            input.next();
        }
        try {
            Ticket ticket = tickets.get(ticketNum - 1);
            ticket.print();

        } catch (IndexOutOfBoundsException e) {
            System.out.println("You have entered an invalid ticket number, please try a different number");
            singleTicket();
        }


    }

    /**
     * Method to display the main menu options to the user
     */
    private void menu()
    {
        System.out.println();
        System.out.println("To exit the application type Quit.");
        System.out.println("Enter 1 to view a list of tickets, or 2 to view an individual ticket.");

    }

    private void welcome()
    {
        System.out.print("Welcome to the Zendesk ticket viewer:");
    }

}
