/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026


 Program Objective: Build a Formula 1 Management System to maintain and keep record of drivers and race results. It will also calculate championship standings based on the driver's gained points.
 This includes adding, removing, and displaying a list of current drivers and races. It should also be able to exit the program.
 The user will be asked to input driver or race details to either add/remove.
 The program will return the driver list, and a confirmation.

 Class: LMSApp (Main class): Will launch the main console-based application.

 */

import java.util.Scanner;
public class F1App {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}