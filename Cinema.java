package cinema;
import com.sun.source.tree.TryTree;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int numRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row");
        int numSeats = scanner.nextInt();
        
        char[][] seats = new char[numRows][numSeats];
        initSeats(seats);
        int currentIncome = 0;

        do {
            showMenu();
            int response = scanner.nextInt();

            switch (response) {
                case 1:
                    printSeats(seats);
                    break;
                case 2:
                    currentIncome += buyTicket(seats);
                    break;
                case 3:
                    displayStats(seats,numRows, numSeats,currentIncome);
                    break;
                case 0:
                    exit = true;
                    break;
            }

        } while(!exit);

    }

    public static void showMenu() {

        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

    }

    public static void displayStats(char[][] seats, int nRows, int nSeats,int currentIncome) {
        int ticketsAmount = 0;

        for (int row = 0 ; row < seats.length; row++) {
            for(int seat = 0; seat < seats[row].length; seat++ ) {
                char c = seats[row][seat];
                if(c == 'B'){
                    ticketsAmount++;
                }
            }
        }

        float percentage =  ((float) ticketsAmount/(nRows * nSeats)) * 100  ;


        System.out.println("Number of purchased tickets: " + ticketsAmount );
        System.out.println("Percentage: " + String.format("%.2f",percentage)+"%");
        System.out.println("Current income: $"+currentIncome);
        System.out.println("Total income $" + calculate(nRows, nSeats));
    }

    public static void updateCinema(char[][]seats, int rowNum, int seatNum) {
        seats[rowNum-1][seatNum-1] = 'B';
    }


    public static int buyTicket(char[][] seats){
        int rows = seats.length;
        int nSeats = seats[0].length;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a row number:");
        int row = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = scanner.nextInt();


        if((row < 1 || row > rows || seat < 1 || seat > nSeats)){
            System.out.println("Wrong input!");
            buyTicket(seats);
            return 0;
        }


        if (alreadyPurchased(seats, row, seat)){
            System.out.println("That ticket has already been purchased!");
            buyTicket(seats);
            return 0;
        }

        int ticketPrice = 0;


        int totalNumberSeats = rows * nSeats;

        if(totalNumberSeats < 60) {
            ticketPrice = 10;
        }

        if(rows % 2 != 0) {
            int firstHalfRows = (rows / 2) ;
            int secondHalfRows = (rows - (rows / 2)) ;

            if (row <= firstHalfRows) {
                ticketPrice = 10;
            } else if (row >= secondHalfRows){
                ticketPrice = 8;
            }

        } else {
            if(row <= rows / 2){
                ticketPrice = 10;
            } else {
                ticketPrice = 8;
            }
        }

        String ticketMessage = String.format("Ticket price: $%d", ticketPrice);
        System.out.println(ticketMessage);
        updateCinema(seats, row, seat);
        return  ticketPrice;

    }

    public static boolean alreadyPurchased (char[][] seats, int row, int seat){
        return seats[row-1][seat-1] == 'B' ? true : false;
    }

    public static void initSeats(char[][] seats) {
        for (int row = 0 ; row < seats.length; row++) {
            for(int seat = 0; seat < seats[row].length; seat++ ) {
                seats[row][seat] = 'S';
            }
        }
    }

    public static void printSeats(char[][] seats) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ");

        for (int row = 1 ; row <= seats[0].length; row++) {
                builder.append(String.format(" %s", (row)));
        }



        System.out.println("Cinema:");
        System.out.println(builder);
        for(int row = 0; row < seats.length; row++){
            System.out.print(row +  1 + " ");

            for(int seat = 0; seat < seats[row].length; seat++) {
                System.out.print(seats[row][seat]);
                System.out.print(' ');
            }
            System.out.println();
        }

        System.out.println("");
    }

    public static int calculate(int rows, int seats) {

        int numAvailableSeats = rows * seats;

        if(numAvailableSeats < 60) {
            return numAvailableSeats * 10;
        }

        if(rows % 2 != 0) {
            int firstHalfRows = (rows / 2) * seats;
            int secondHalfRows = (rows - (rows / 2)) * seats ;
            return (firstHalfRows * 10) + (secondHalfRows * 8);
        } else {
            return (rows / 2) * seats * 10 + (rows / 2) * seats * 8;
        }

    }


}