import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api {
    public static String url = "https://aymanelgendy2.zendesk.com/api/v2/tickets.json";

    /**
     * Method that makes a request to the Zendesk Api and retrieves a json object
     * that contains all the tickets in the account.
     * @param page
     * @return
     */
    public static JSONObject getTickets(String page)
    {
        HttpResponse<JsonNode> response = Unirest.get(page)
                .basicAuth(Details.user, Details.password)
                .asJson();

        if (response.getStatus() == 200) {

            return response.getBody().getObject();
        }
        System.out.println("Status: " + response.getStatus() + " Error while attempting to make the request");
        System.out.println("Application closing...");
        System.exit(0);
        return null;
    }

    /**
     * Method to return our ticketlist that contains all of our tickets.
     * @return
     */
    public static List<Ticket> getAllTickets()
    {
        List<Ticket> ticketList = new ArrayList<Ticket>();
        String nextUrl = Api.url;
        while (nextUrl!=null) {
            JSONObject response = Api.getTickets(nextUrl);
            updateTicketlist(response.getJSONArray("tickets"),ticketList);

            if (response.isNull("next_page")) {
                nextUrl = null;
            } else {
                nextUrl = response.getString("next_page");
            }
        }
        return ticketList;
    }

    /**
     * Method that adds each page of tickets into a list that contains all our previously retrieved tickets.
     * @return
     */
    public static void updateTicketlist(JSONArray resp_array, List<Ticket> ticketList)
    {
        for (int i = 0; i < resp_array.length(); i++) {
            JSONObject ticketJson = resp_array.getJSONObject(i);
            ticketList.add(new Ticket(ticketJson));
        }
    }

}
