/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fatima;

import java.util.*;
import java.util.Properties;
import javax.mail.Session;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


interface Notifiable {
    void send(String message);
}


class EmailNotification implements Notifiable {

    private final String fromEmail = "your_email@gmail.com";
    private final String password = "your_app_password";
    private final String toEmail = "recipient_email@gmail.com";

    public void send(String message) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            }
        );

        try {
            Message email = new MimeMessage(session);
            email.setFrom(new InternetAddress(fromEmail));
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            email.setSubject("RPMS Alert");
            email.setText(message);
            Transport.send(email);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}


class SMSNotification implements Notifiable {
    public void send(String message) {
        System.out.println("SMS Notification Sent: " + message);
    }
}

class NotificationService {
    private List<Notifiable> channels = new ArrayList<>();

    public void addChannel(Notifiable channel) {
        channels.add(channel);
    }

    public void sendAlert(String message) {
        for (Notifiable channel : channels) {
            channel.send(message);
        }
    }
}

class PanicButton {
    public void trigger() {
        System.out.println("Panic Button Pressed! Emergency Alert Triggered!");
    }
}

class EmergencyAlert {
    private NotificationService notificationService;
    private Map<String, Double> vitals;

    public EmergencyAlert(NotificationService service) {
        this.notificationService = service;
        this.vitals = new HashMap<>();
    }

    public void inputVitals() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter Heart Rate: ");
            vitals.put("Heart Rate", scanner.nextDouble());
            System.out.print("Enter Blood Pressure: ");
            vitals.put("Blood Pressure", scanner.nextDouble());
            System.out.print("Enter Oxygen Level: ");
            vitals.put("Oxygen Level", scanner.nextDouble());
            System.out.print("Enter Temperature: ");
            vitals.put("Temperature", scanner.nextDouble());
            System.out.print("Enter Respiration Rate: ");
            vitals.put("Respiration Rate", scanner.nextDouble());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numeric values.");
        }
    }

    public void checkVitals() {
        for (Map.Entry<String, Double> entry : vitals.entrySet()) {
            String vital = entry.getKey();
            double value = entry.getValue();
            boolean alert = false;
            switch (vital) {
                case "Heart Rate":
                    if (value < 60 || value > 100) alert = true;
                    break;
                case "Blood Pressure":
                    if (value < 80 || value > 120) alert = true;
                    break;
                case "Oxygen Level":
                    if (value < 95) alert = true;
                    break;
                case "Temperature":
                    if (value < 36.1 || value > 37.2) alert = true;
                    break;
                case "Respiration Rate":
                    if (value < 12 || value > 20) alert = true;
                    break;
            }
            if (alert) {
                notificationService.sendAlert("Critical Alert: " + vital + " is out of normal range: " + value);
            }
        }
    }
}

class ChatServer {
    private static List<String> chatMessages = new ArrayList<>();

    public static void sendMessage(String sender, String message) {
        chatMessages.add(sender + ": " + message);
    }

    public static void displayMessages() {
        System.out.println("\n--- Chat Messages ---");
        for (String msg : chatMessages) {
            System.out.println(msg);
        }
        System.out.println("----------------------\n");
    }
}

class ChatClient {
    private String role;
    private Scanner scanner;

    public ChatClient(String role) {
        this.role = role;
        this.scanner = new Scanner(System.in);
    }

    public void chatInterface() {
        while (true) {
            ChatServer.displayMessages();
            System.out.print(role + " - Type your message (or 'exit' to logout): ");
            String msg = scanner.nextLine();
            if (msg.equalsIgnoreCase("exit")) break;
            ChatServer.sendMessage(role, msg);
        }
    }
}

class VideoCall {
    public static void startCall() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to start a Zoom or Google Meet call? (Z/GM)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("Z")) {
            System.out.println("Starting Zoom Call... Join Zoom link: https://zoom.us/j/1234567890\n");
        } else if (choice.equalsIgnoreCase("GM")) {
            System.out.println("Starting Google Meet Call... Join link: https://meet.google.com/xyz-abc-def\n");
        } else {
            System.out.println("Invalid choice. Exiting call...\n");
        }
    }
}

class ReminderService {
    private NotificationService notificationService;

    public ReminderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void sendReminder(String message) {
        notificationService.sendAlert("Reminder: " + message);
    }
}

class Appointment {
    private String patientName;
    private String doctorName;
    private String dateTime;
    private String status; // "Pending", "Approved", "Rejected"

    public Appointment(String patientName, String doctorName, String dateTime) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.dateTime = dateTime;
        this.status = "Pending";
    }

    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public String getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Appointment: " + patientName + " with Dr. " + doctorName + 
               " at " + dateTime + " - Status: " + status;
    }
}

class Admin {
    private List<String> doctors = new ArrayList<>();
    private List<String> patients = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void adminInterface() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Patient");
            System.out.println("3. Show Doctors");
            System.out.println("4. Show Patients");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Enter Doctor Name: ");
                    doctors.add(scanner.nextLine());
                    System.out.println("Doctor added successfully!");
                    break;
                case "2":
                    System.out.print("Enter Patient Name: ");
                    patients.add(scanner.nextLine());
                    System.out.println("Patient added successfully!");
                    break;
                case "3":
                    System.out.println("\nList of Doctors:");
                    for (String doctor : doctors) {
                        System.out.println("- " + doctor);
                    }
                    break;
                case "4":
                    System.out.println("\nList of Patients:");
                    for (String patient : patients) {
                        System.out.println("- " + patient);
                    }
                    break;
                case "5":
                    System.out.println("Logging out...\n");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}

class AppointmentSystem {
    private static List<Appointment> appointments = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void requestAppointment(String patientName) {
        System.out.print("Enter Doctor Name: ");
        String doctorName = scanner.nextLine();
        System.out.print("Enter Date and Time (e.g., 2023-12-25 14:30): ");
        String dateTime = scanner.nextLine();
        appointments.add(new Appointment(patientName, doctorName, dateTime));
        System.out.println("Appointment requested successfully!");
    }

    public static void viewPatientAppointments(String patientName) {
        System.out.println("\nYour Appointments:");
        boolean found = false;
        for (Appointment app : appointments) {
            if (app.getPatientName().equalsIgnoreCase(patientName)) {
                System.out.println(app);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments found.");
        }
    }

    public static void viewDoctorAppointments(String doctorName) {
        System.out.println("\nAppointment Requests:");
        boolean found = false;
        for (Appointment app : appointments) {
            if (app.getDoctorName().equalsIgnoreCase(doctorName)) {
                System.out.println(app);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointment requests found.");
        }
    }

    public static void manageAppointments(String doctorName) {
        viewDoctorAppointments(doctorName);
        System.out.print("\nEnter patient name to manage appointment (or 'cancel'): ");
        String patientName = scanner.nextLine();
        if (patientName.equalsIgnoreCase("cancel")) return;

        for (Appointment app : appointments) {
            if (app.getPatientName().equalsIgnoreCase(patientName) && 
                app.getDoctorName().equalsIgnoreCase(doctorName) &&
                app.getStatus().equals("Pending")) {
                System.out.print("Approve or Reject? (A/R): ");
                String decision = scanner.nextLine();
                if (decision.equalsIgnoreCase("A")) {
                    app.setStatus("Approved");
                    System.out.println("Appointment approved!");
                } else if (decision.equalsIgnoreCase("R")) {
                    app.setStatus("Rejected");
                    System.out.println("Appointment rejected!");
                }
                return;
            }
        }
        System.out.println("No pending appointment found for this patient.");
    }
}

public class RPMSApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NotificationService notificationService = new NotificationService();
        notificationService.addChannel(new EmailNotification());
        notificationService.addChannel(new SMSNotification());
        PanicButton panicButton = new PanicButton();
        EmergencyAlert alertSystem = new EmergencyAlert(notificationService);
        ReminderService reminderService = new ReminderService(notificationService);
        Admin admin = new Admin();

        while (true) {
            System.out.print("Are you an Admin, Doctor, or Patient? (A/D/P or exit): ");
            String roleInput = scanner.nextLine();
            if (roleInput.equalsIgnoreCase("exit")) break;

            if (roleInput.equalsIgnoreCase("A")) {
                admin.adminInterface();
            } 
            else if (roleInput.equalsIgnoreCase("P")) {
                System.out.print("Enter your name: ");
                String patientName = scanner.nextLine();
                while (true) {
                    System.out.println("\n1. Press Panic Button\n2. Enter Vitals\n3. Chat\n4. Video Call\n5. Get Reminder\n6. Book Appointment\n7. View Appointments\n8. Logout");
                    System.out.print("Choose an option: ");
                    String option = scanner.nextLine();

                    try {
                        switch (option) {
                            case "1": panicButton.trigger(); break;
                            case "2": alertSystem.inputVitals(); alertSystem.checkVitals(); break;
                            case "3": new ChatClient("Patient").chatInterface(); break;
                            case "4": VideoCall.startCall(); break;
                            case "5": reminderService.sendReminder("Take your medicine at 9 PM."); break;
                            case "6": AppointmentSystem.requestAppointment(patientName); break;
                            case "7": AppointmentSystem.viewPatientAppointments(patientName); break;
                            case "8": System.out.println("Logging out...\n"); break;
                            default: System.out.println("Invalid option.");
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }

                    if (option.equals("8")) break;
                }
            } 
            else if (roleInput.equalsIgnoreCase("D")) {
                System.out.print("Enter your name: ");
                String doctorName = scanner.nextLine();
                while (true) {
                    System.out.println("\n1. Chat with Patient\n2. Video Call\n3. Send Reminder\n4. View Appointments\n5. Manage Appointments\n6. Logout");
                    System.out.print("Choose an option: ");
                    String option = scanner.nextLine();

                    try {
                        switch (option) {
                            case "1": new ChatClient("Doctor").chatInterface(); break;
                            case "2": VideoCall.startCall(); break;
                            case "3": reminderService.sendReminder("Appointment tomorrow at 10 AM."); break;
                            case "4": AppointmentSystem.viewDoctorAppointments(doctorName); break;
                            case "5": AppointmentSystem.manageAppointments(doctorName); break;
                            case "6": System.out.println("Logging out...\n"); break;
                            default: System.out.println("Invalid option.");
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }

                    if (option.equals("6")) break;
                }
            } 
            else {
                System.out.println("Invalid role selected.");
            }
        }
    }
}
