import kong.unirest.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TicketTest {
    JSONObject myJson;
    public TicketTest()
    {
        JSONObject myJson = new JSONObject();
        myJson.put("subject","test ticket");
        myJson.put("id","123");
        myJson.put("description","this is a test ticket");
        myJson.put("submitter_id","5960320");

    }


    @Test
    public void print()
    {

        Ticket ticketTest = new Ticket(myJson);
        assertEquals("Ticket number: 123","Ticket number: " + ticketTest.getId());
        assertEquals("Subject: test ticket","Subject: " + ticketTest.getSubject());
        assertEquals("Submitted by: 5960320","Submitted by: " + ticketTest.getSubmitter_id());
        assertEquals("Description: this is a test ticket","Description: " + ticketTest.getDescription());

    }

    @Test
    public void getSubject()
    {
        Ticket ticketTest = new Ticket(myJson);
        assertEquals("test ticket", ticketTest.getSubject());
    }

    @Test
    public void getId()
    {
        Ticket ticketTest = new Ticket(myJson);
        assertEquals("123", ticketTest.getId());
    }

    @Test
    public void getSubmitter_id()
    {
        JSONObject myJson = new JSONObject();
        myJson.put("subject", "test ticket");
        myJson.put("id", "123");
        myJson.put("description", "this is a test ticket");
        myJson.put("submitter_id", "5960320");
        Ticket ticketTest = new Ticket(myJson);
        assertEquals("5960320", ticketTest.getSubmitter_id());
    }
}