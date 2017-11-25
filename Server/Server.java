
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


/**
 *
 * @author Andreas Agapitos
 */
public class Server {
    private static ServerSocket listener;

    /**
     *
     */
    protected static ArrayList<Account> accounts=new ArrayList<>();
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
        int port=Integer.parseInt(args[0]);
        accounts.add(new Account("andreas","1234k"));
        accounts.add(new Account("secondPerson","123"));
        accounts.get(0).addEmail("secondPerson", "andreas", "Subject", "Message");
        accounts.get(0).addEmail("secondPerson", "andreas", "Subject2", "Message2-Comments");
        accounts.get(0).addEmail("secondPerson", "andreas", "Subject3", "Message3");
        accounts.get(1).addEmail("andreas", "secondPerson", "Subject", "Message-secondPerson");
        accounts.get(1).addEmail("andreas", "secondPerson", "Subject2", "Message2-Comments-secondPerson");
        accounts.get(1).addEmail("andreas", "secondPerson", "Subject3", "Message3-secondPerson");
        System.out.println("The server is running.");
        listener = new ServerSocket(port);
        try {
            while (true) {
                new Handler(listener.accept()).start();
                System.out.println("Welcome");
            }
        } finally {
            listener.close();
        }

    }
    
}
