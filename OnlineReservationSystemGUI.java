import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Reservation {
    String passengerName;
    int trainNo;
    String trainName;
    String classType;
    String dateOfJourney;
    String from;
    String to;
    int pnr;

    public Reservation(String passengerName, int trainNo, String trainName,
                       String classType, String dateOfJourney, String from, String to, int pnr) {
        this.passengerName = passengerName;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
        this.pnr = pnr;
    }

    public String toString() {
        return "PNR: " + pnr + "\nPassenger: " + passengerName + "\nTrain No: " + trainNo +
                "\nTrain Name: " + trainName + "\nClass: " + classType +
                "\nDate: " + dateOfJourney + "\nFrom: " + from + "\nTo: " + to;
    }
}

public class OnlineReservationSystemGUI {
    private static HashMap<Integer, Reservation> reservations = new HashMap<>();
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OnlineReservationSystemGUI::showLogin);
    }

    // ---------------- LOGIN FORM ----------------
    static void showLogin() {
        frame = new JFrame("Online Reservation System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblUser = new JLabel("Login ID:");
        JLabel lblPass = new JLabel("Password:");
        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        frame.add(lblUser);
        frame.add(txtUser);
        frame.add(lblPass);
        frame.add(txtPass);
        frame.add(new JLabel());
        frame.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            if (user.equals("admin") && pass.equals("1234")) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid ID or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    // ---------------- MAIN MENU ----------------
    static void showMainMenu() {
        frame = new JFrame("Online Reservation System - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnReserve = new JButton("Make Reservation");
        JButton btnCancel = new JButton("Cancel Reservation");
        JButton btnView = new JButton("View All Reservations");
        JButton btnExit = new JButton("Exit");

        frame.add(btnReserve);
        frame.add(btnCancel);
        frame.add(btnView);
        frame.add(btnExit);

        btnReserve.addActionListener(e -> {
            frame.dispose();
            showReservationForm();
        });

        btnCancel.addActionListener(e -> {
            frame.dispose();
            showCancellationForm();
        });

        btnView.addActionListener(e -> showAllReservations());

        btnExit.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Thank you for using Online Reservation System!");
            System.exit(0);
        });

        frame.setVisible(true);
    }

    // ---------------- RESERVATION FORM ----------------
    static void showReservationForm() {
        frame = new JFrame("Reservation Form");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(9, 2, 5, 5));

        JTextField txtName = new JTextField();
        JTextField txtTrainNo = new JTextField();
        JComboBox<String> cmbClass = new JComboBox<>(new String[]{"First", "Second", "Sleeper"});
        JTextField txtDate = new JTextField();
        JTextField txtFrom = new JTextField();
        JTextField txtTo = new JTextField();
        JLabel lblTrainName = new JLabel();
        JButton btnInsert = new JButton("Insert");
        JButton btnBack = new JButton("Back");

        frame.add(new JLabel("Passenger Name:"));
        frame.add(txtName);
        frame.add(new JLabel("Train No:"));
        frame.add(txtTrainNo);
        frame.add(new JLabel("Train Name:"));
        frame.add(lblTrainName);
        frame.add(new JLabel("Class Type:"));
        frame.add(cmbClass);
        frame.add(new JLabel("Date of Journey (DD-MM-YYYY):"));
        frame.add(txtDate);
        frame.add(new JLabel("From:"));
        frame.add(txtFrom);
        frame.add(new JLabel("To:"));
        frame.add(txtTo);
        frame.add(btnInsert);
        frame.add(btnBack);

        txtTrainNo.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    int trainNo = Integer.parseInt(txtTrainNo.getText());
                    lblTrainName.setText(getTrainName(trainNo));
                } catch (Exception ex) {
                    lblTrainName.setText("");
                }
            }
        });

        btnInsert.addActionListener(e -> {
            try {
                String name = txtName.getText();
                int trainNo = Integer.parseInt(txtTrainNo.getText());
                String trainName = lblTrainName.getText();
                String classType = (String) cmbClass.getSelectedItem();
                String date = txtDate.getText();
                String from = txtFrom.getText();
                String to = txtTo.getText();

                if (name.isEmpty() || trainName.isEmpty() || date.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                    return;
                }

                int pnr = new Random().nextInt(900000) + 100000;
                Reservation r = new Reservation(name, trainNo, trainName, classType, date, from, to, pnr);
                reservations.put(pnr, r);
                JOptionPane.showMessageDialog(frame, "Reservation Successful!\nYour PNR: " + pnr);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input!");
            }
        });

        btnBack.addActionListener(e -> {
            frame.dispose();
            showMainMenu();
        });

        frame.setVisible(true);
    }

    // ---------------- CANCELLATION FORM ----------------
    static void showCancellationForm() {
        frame = new JFrame("Cancellation Form");
        frame.setSize(350, 200);
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        JTextField txtPNR = new JTextField();
        JButton btnSearch = new JButton("Search");
        JButton btnBack = new JButton("Back");

        frame.add(new JLabel("Enter PNR Number:"));
        frame.add(txtPNR);
        frame.add(btnSearch);
        frame.add(btnBack);

        btnSearch.addActionListener(e -> {
            try {
                int pnr = Integer.parseInt(txtPNR.getText());
                if (reservations.containsKey(pnr)) {
                    Reservation r = reservations.get(pnr);
                    int confirm = JOptionPane.showConfirmDialog(frame, r + "\n\nDo you want to cancel this ticket?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        reservations.remove(pnr);
                        JOptionPane.showMessageDialog(frame, "Ticket Cancelled Successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "PNR Not Found!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid PNR!");
            }
        });

        btnBack.addActionListener(e -> {
            frame.dispose();
            showMainMenu();
        });

        frame.setVisible(true);
    }

    // ---------------- VIEW ALL ----------------
    static void showAllReservations() {
        if (reservations.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No reservations found!");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Reservation r : reservations.values()) {
                sb.append(r).append("\n--------------------\n");
            }
            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            JOptionPane.showMessageDialog(frame, new JScrollPane(area), "All Reservations", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    static String getTrainName(int trainNo) {
        switch (trainNo) {
            case 101:
                return "Rajdhani Express";
            case 102:
                return "Shatabdi Express";
            case 103:
                return "Duronto Express";
            case 104:
                return "Garib Rath";
            default:
                return "Unknown Train";
        }
    }
}
