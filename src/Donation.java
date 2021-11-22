import java.util.Date;


public class Donation {
    
    private int alumniId;
    private int eventId;
    private double amountDonated;
    private Date dateCreated;

    
    public Donation(int alumniId, int  eventId, double donationAmount) {
        dateCreated = new Date();
        this.alumniId = alumniId;
        this.eventId = eventId;
        this.amountDonated = donationAmount;
    }

    public int getAlumniId() {
        return alumniId;
    }

    public int getEventId() {
        return eventId;
    }

    public double getAmountDonated() {
        return amountDonated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String receipt() {
        return "Date donation was made: " + dateCreated + " Amount Donated: " + amountDonated + " ID of Donator: " + alumniId + " ID of Event: " + eventId;
    }
}
