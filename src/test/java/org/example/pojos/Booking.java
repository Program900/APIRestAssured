package org.example.pojos;

public class Booking {
    //create a booking pojo class with all the fields
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    //Create a constructor  //
    public Booking(String firstname, String lastname, int totalprice, boolean depositpaid, BookingDates bookingdates, String additionalneeds) {
        // set the values of the fields
        setAdditionalneeds(additionalneeds);
        setBookingdates(bookingdates);
        setDepositpaid(depositpaid);
        setFirstname(firstname);
        setLastname(lastname);
        setTotalprice(totalprice);

    }

    //Generate getters and setters
    private void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public int getTotalprice() {
        return totalprice;
    }
    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }
    public boolean isDepositpaid() {
        return depositpaid;
    }
    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }
    public BookingDates getBookingdates() {
        return bookingdates;
    }
    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }
    public String getAdditionalneeds() {
        return additionalneeds;
    }
    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }



}
