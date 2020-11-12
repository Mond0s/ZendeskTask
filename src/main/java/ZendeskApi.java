
import com.fasterxml.jackson.core.JsonProcessingException;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ZendeskApi {


    public static void main(String[] args) throws JsonProcessingException {
        Details details = new Details();
        List<Ticket> ticketList = new ArrayList<Ticket>();
        String url = "https://aymanelgendy2.zendesk.com/api/v2/tickets.json";
        while (url != null) {
            JSONObject resp = getTickets(details, url);
            JSONArray resp_array = resp.getJSONArray("tickets");


            updateTicketlist(resp_array, ticketList);

            if (resp.isNull("next_page")) {
                break;
            }
            url = resp.getString("next_page");


        }
        System.out.println("Welcome to the Zendesk ticket viewer:");
        welcomePrompt();

        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String prompt = input.next();
            if (prompt.equals("1")) {
                listTickets(ticketList, input);

            }
            if (prompt.equals("2")) {
                System.out.println("Enter the ticket number you would like to view");
                singleTicket(ticketList, input);
            }
            if (prompt.equals("Quit")) {
                break;
            }
            else{
                welcomePrompt();
            }
        }
    }

    /**
     * Method to take in a list of tickets and a user input to request a ticket number,
     * then returns the details of the ticket that matches that number/id
     * @param ticketList
     * @param input
     */
    private static void singleTicket(List<Ticket> ticketList, Scanner input) {
        int ticketNum = input.nextInt();
        try{
            Ticket ticket = ticketList.get(ticketNum-1);
            ticket.print();

        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("You have entered an invalid ticket number, please a different number");
            singleTicket(ticketList,input);
        }





    }

    private static void listTickets(List<Ticket> ticketList, Scanner input) {
        for (int i = 0; i < ticketList.size(); i++) {
            Ticket ticket = ticketList.get(i);
            ticket.print();
            if (i % 24 == 0 && i != 0) {
                System.out.println("TYPE next TO VIEW THE NEXT PAGE OF TICKETS");
                System.out.println("ENTER ANY OTHER WORD TO RETURN TO THE MAIN MENU");
                String next = input.next();
                if (!next.equals("next")) {
                    System.out.println("Returning to main menu....");
                    break;
                }


            }




        }

    }

    private static JSONObject getTickets(Details details, String url) {
        HttpResponse<JsonNode> response = Unirest.get(url)
                .basicAuth(details.user, details.password)
                .asJson();
        if (!(response.getStatus() == 200)) {
            System.out.println("Status: " + response.getStatus() + " Error while attempting to make the request");
            System.out.println("Application closing...");
            System.exit(0);
        }
        JSONObject resp = response.getBody().getObject();
        return resp;

    }


    private static void updateTicketlist(JSONArray resp_array, List<Ticket> ticketList) {
        for (int i = 0; i < resp_array.length(); i++) {
            JSONObject ticketJson = resp_array.getJSONObject(i);


            ticketList.add(new Ticket((ticketJson)));

        }
    }
    private static void welcomePrompt(){
        System.out.println();
        System.out.println("Enter 1 to view a list of tickets, or 2 to view an individual ticket.");
        System.out.println("To exit the application type Quit.");
    }


}

