package org.example.pojos;

public class BookingDates {
     //create a booking pojo class with all the fields
        private String checkin;
        private String checkout;

        //Create a constructor  //
        public BookingDates() {
            // set the values of the fields
        }
        public BookingDates(String checkin, String checkout) {
            // set the values of the fields
            setCheckin(checkin);
            setCheckout(checkout);
        }
        //Generate getters and setters
        private void setCheckin(String checkin) {
            this.checkin = checkin;
        }
        public String getCheckin() {
            return checkin;
        }
        public String getCheckout() {
            return checkout;
        }
        public void setCheckout(String checkout) {
            this.checkout = checkout;
        }

}
