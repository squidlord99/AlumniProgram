import java.io.FileNotFoundException;

public class UI {
    private int id;
    private InOut io;

    /**
     * 
     * @throws FileNotFoundException
     */
    public UI() throws FileNotFoundException {
        io = new InOut();
    }

    // TODO way to logout
    /**
     * 
     * @throws FileNotFoundException
     */
    public void userInterface() throws FileNotFoundException {
        System.out.println("Hello and welcome to the Alumni program");
        login();
        loggedIn();

    }

    public void login() throws FileNotFoundException {
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("1. Login to existing account \n2. Create a new account \n3. Exit");
            int choice = io.intInput();
            switch (choice) {
            case 1:
                System.out.println("Please log in by entering you ID and Password:");
                System.out.println("ID: ");
                id = io.intInput();
                try {
                    if (!io.checkId(id))
                        throw new InvalidEntry("NOT AN EXISTING ID NUMBER");
                } catch (InvalidEntry p) {
                    System.out.println(p.getMessage());
                    break;
                }

                System.out.println("Password: ");
                String password = io.stringInput();
                String expectedPw = io.getPassword(id);

                try {
                    if (password.equals(expectedPw)) {
                        loggedIn = true;
                    } else {
                        throw new InvalidEntry("INVALID PASSWORD");
                    }
                } catch (InvalidEntry p) {
                    System.out.println(p.getMessage());
                }
                break;
            case 2:
                newAlumniInfo();
                loggedIn = true;
                break;
            case 3:
                io.closeEverythingAndSave();
                System.exit(0);
                break;
            }
        }
    }

    public void loggedIn() throws FileNotFoundException {
        boolean run = true;
        while (run) {
            System.out.println(" ----------------------------------------------------- ");
            System.out.println("Welcome " + io.getAlumniName(id) + " what would you like to do?");
            System.out.println(
                    "Please enter a number for what you want to do \n1. for Alumni settings(Display, edit, and delete) \n"
                            + "2. for event settings(join, create) \n3. to exit the program");
            int choice = io.intInput();
            switch (choice) {
            case 1:
                alumniInterface();
                break;
            case 2:
                eventInterface();
                break;
            case 3:
                System.out.println("Thanks for using the premium Alumni service \uD83E\uDD70");
                io.closeEverythingAndSave();
                System.exit(0);
                break;
            }
        }
    }


    public void alumniInterface() {
        System.out.println("Alumni Interface\n" + "please enter a choice");
        boolean run = true;
        while (run) {
            System.out.println(" ----------------------------------------------------- ");
            System.out.println("1. See a list of current Alumni \n2. Edit your profile info \n"
                    + "3. Delete your account \n4. Go back to the main menu");
            int choice = io.intInput();
            switch (choice) {
            case 1:
                // list of alumni
                io.displayAlumni();
                break;
            case 2:
                // edit Alumni
                editAlumni();
                break;
            case 3:
                // Delete Alumni
                String confirmation = io.stringInput().toLowerCase();
                if (confirmation.charAt(0) == 'y') {
                    io.deleteAlumni(id);
                }
                break;
            case 4:
                run = false;
                // go back to main menu
                break;
            }

        }
    }

    public void eventInterface() {
        System.out.println("Event Interface");
        boolean run = true;
        while (run) {
            System.out.println(" ----------------------------------------------------- ");
            System.out.println("1. See a list of events \n2. Sign up to attend an Event \n3. Make a donation \n"
                    + "4. See my donations \n5. Sign up to speak\n6. Delete Event \n7. Edit event \n8. Exit");
            int choice = io.intInput();
            switch (choice) {
            case 1:
                // list of events
                io.displayEvents();
                break;
            case 2:
                // sign up for events
                io.joinEvent(id, io.getAlumniName(id));
                break;
            case 3:
                // make donation
                System.out.println("enter the id of the event you want to donate to");
                // id var?
                System.out.println("how much would you like to donate");
                // donation call int id, int donation amount
                break;
            case 4:
                // see my donations
                displayDonationsAlumni();
                break;
            case 5:
                // create event
                createEvent();
                break;
            case 6:
                System.out.println("Are you sure you want to delete this event y/n");
                String confirmation = io.stringInput().toLowerCase();
                if (confirmation.charAt(0) == 'y') {
                    System.out.println("please enter the id of the event that you want to delete");
                    io.deleteEvent(io.intInput());
                }
                break;
            case 7:
                // edits events
                editEvents();
                break;
            case 8:
                // go back to main menu
                run = false;
                break;
            }
        }
    }

    public void editAlumni() {
        boolean run = true;

        while (run) {
            System.out.println(
                    "what would you like to change? \n1. edit name \n2. edit address \n3. edit major \n4. edit gradYear \n5. edit job \n6. edit organization \n7. change password \n8. exit");
            int choice = io.intInput();

            switch (choice) {
            case 1:
                // edit name
                System.out.println("Enter a new name:");
                io.setAlumniName(id, io.stringInput());
                break;

            case 2:
                // edit address
                System.out.println("Enter a new Address");
                io.setAlumniAddress(id, io.stringInput());
                break;

            case 3:
                // edit major
                System.out.println("Enter a new Major:");
                io.setAlumniMajor(id, io.stringInput());
                break;

            case 4:
                // edit gradYear
                System.out.println("Enter a new Graduation Year:");
                io.setAlumniGradYear(id, io.stringInput());
                break;

            case 5:
                // edit job
                System.out.println("Enter a new Job:");
                io.setAlumniJob(id, io.stringInput());
                break;

            case 6:
                // edit organization
                System.out.println("Enter a new organization");
                io.setAlumniOrg(id, io.stringInput());
                break;
            case 7:
                // change password
                System.out.println("Enter a new password: ");
                io.setPassword(id, io.stringInput());
                break;
            case 8:
                // exit
                System.out.println("NO CHANGES");
                io.displayAlumni();
                run = false;
                break;
            }

        }

    }

    public void editEvents() {
        boolean run = true;
        boolean owner = false;
        int eventID = 0;
        while (!owner) {
            io.displayEvents();
            System.out.println("Enter the event id:");
            eventID = io.intInput();
            try {
                if (id != io.getHostId(eventID)) {
                    throw new InvalidEntry("YOU DID NOT OWN THIS EVENT");
                } else
                    owner = true;
            } catch (InvalidEntry e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        while (run) {
            System.out.println(
                    "Enter Event ID: \n1. event name \n2. event time \n3. event room \n4. Number of participants \n5. event date \n6. exit");
            int choice = io.intInput();

            switch (choice) {
            case 1:
                // edit name
                System.out.println("Enter name of event:");
                io.setName(eventID, io.stringInput());
                break;

            case 2:
                // edit time
                System.out.println("Enter time of event");
                io.setTime(eventID, io.stringInput());
                break;

            case 3:
                // edit room
                System.out.println("Enter event room:");
                io.setRoom(eventID, io.intInput());
                break;

            case 4:
                // edit number of participants
                System.out.println("Enter number of participants:");
                io.setNumberOfParticipants(eventID, io.intInput());
                break;

            case 5:
                // edit date
                System.out.println("Enter date of event:");
                io.setDate(eventID, io.stringInput());
                break;

            case 7:
                // exit
                System.out.println("NO CHANGES");
                run = false;
                break;
            }
        }
    }

    public void newAlumniInfo() {
        System.out.println("Enter the name of the Alumni");
        String name = io.stringInput();
        System.out.println("Enter the Address of the Alumni");
        String address = io.stringInput();
        System.out.println("Enter the Major of the Alumni");
        String major = io.stringInput();
        System.out.println("Enter the Alumni's graduation year");
        String gradYear = io.stringInput();
        System.out.println("Enter the Alumni's current occupation");
        String job = io.stringInput();
        System.out.println("Enter the company that the Alumni Currently works for");
        String organization = io.stringInput();
        System.out.println("Enter a password");
        String password = io.stringInput();
        io.addAlumni(name, address, major, gradYear, job, organization, password);

    }

    public void createEvent() {
        System.out.println("Enter the name of Event");
        String name = io.stringInput();
        System.out.println("Enter the time of Event");
        String time = io.stringInput();
        System.out.println("Enter the room of Event");
        int room = io.intInput();
        System.out.println("Enter number of participants");
        int numberOfParticipants = io.intInput();
        System.out.println("Enter the date of Event");
        String eventDate = io.stringInput();
        System.out.println("Enter the topic of the Event:");
        String topic = io.stringInput();
        System.out.println("Enter a phone number where you can be reached: ");
        int phone = io.intInput();
        System.out.println("Enter an email where you can be reached: ");
        String email = io.stringInput();
        // TODO unfuck this mess (there's got to be a better way, this copying of an
        // alumni into a host is dumb)
        Host host = new Host(id, io.getAlumniName(id), io.getAlumniAddress(id), io.getAlumniMajor(id),
                io.getAlumniGradYear(id), io.getAlumniJob(id), io.getAlumniOrg(id), topic, phone, email);
        io.createEvent(name, time, room, numberOfParticipants, eventDate, host);
    }

    // ----- donation stuff ------
    public void addDonation() {
        System.out.println("Enter the event ID");
        int eventID = io.intInput();
        System.out.println("Enter the amount you would like to donate: ");
        double amountDonated = io.doubleInput();
        io.addDonationToList(id, eventID, amountDonated);
    }

    public void displayDonationsAlumni() {
        io.displayDonationsAlumni(id);
    }

    public void displayDonationsEvents() {
        System.out.println("Enter the event id");
        int id = io.intInput();
        io.displayDonationsEvents(id);
    }

}
