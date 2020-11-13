import kong.unirest.json.JSONObject;

public class Ticket {
    private String id;
    private String subject;
    private String submitter_id;
    private String description;


    public Ticket(String id , String subject, String submitter_id,String description)
    {
        this.id = id;
        this.subject = subject;
        this.submitter_id = submitter_id;
        this.description = description;
    }

    public Ticket(JSONObject Json)
    { /*  Constructor overloading */
        this(
                Json.getString("id"),
                Json.getString("subject"),
                Json.getString("submitter_id"),
                Json.getString("description")
        );

    }

    public void print()
    {
        System.out.println("Ticket number: " + id);
        System.out.println("Submitted by: " + submitter_id);
        System.out.println("Subject: " + subject);
        System.out.println("Description: " + description);

        System.out.println();
    }

    public String getSubject()
    {
        return subject;
    }

    public String getId()
    {
        return id;
    }

    public String getSubmitter_id()
    {
        return submitter_id;
    }

    public String getDescription()
    {
        return description;
    }
}
