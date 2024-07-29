

import java.util.ArrayList;

public class main2 {
    public static void main(String[] args) throws Exception {

        main voltdb = new main("localhost:32776");

        ArrayList<Subscriber> subscribers  = voltdb.getAllCustomers();

        System.out.println("\n\n");
        subscribers.forEach(System.out::println);


    }
}