package socialmagnet.controller;

import socialmagnet.view.Session;

/**
 * This class is the entry point to start the Social Magnet application.
 * @version 5th April 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class App {

    /* This is the starting point of the program.
       The main method creates an instance of the Session class and starts the session here. */
    public static void main(String[] args) {
        Session session = new Session();
        session.start();
    }
}
