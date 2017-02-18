/* 
 * The MIT License
 *
 * Copyright 2017 Andreas Agapitos.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
            }
        } finally {
            listener.close();
        }

    }
    
}
