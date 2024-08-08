package org.redlich.random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * An application to randomly select a winner (or winners) from a list of registered attendees.
 */
public class RandomDrawing {
	private int numberOfItems; // the number of items for the random drawings
	private static final String ATTENDEES = "attendees.txt";
	private static final String MEETING = "July 2024";

	/**
	 *
	 * @param args the number of items to give away for this drawing
	 */
	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Usage: RandomDrawing numberOfItems");
			System.exit(1);
			}

		RandomDrawing drawing = new RandomDrawing(Integer.parseInt(args[0]));

		displaySplashScreen(" Garden State Java User Group IntelliJ IDEA Drawing ");
		displaySplashScreen(" " + drawing.MEETING + " ");
		displaySplashScreen(" Registered Attendees ");

		List<String> attendeeList = drawing.getAttendeeList(drawing.ATTENDEES);
		drawing.writeToList(attendeeList);

		displaySplashScreen(" Winner(s)");
		drawing.pauseApplication(2);

		List<String> winnerList = drawing.getWinnerList(attendeeList);
		drawing.writeToList(winnerList);
		System.out.println();
		}

	/**
	 * Constructor that calls the setNumberOfItems() method.
	 * @param numberOfItems the number of items to give away for a particular drawing.
	 */
	public RandomDrawing(int numberOfItems) {
		this.setNumberOfItems(numberOfItems);
		}

	/**
	 *
	 * @param numberOfItems the number of items to give away for a particular drawing.
	 */
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
		}

	/**
	 *
	 * @param filename the filename containing the registered attendees.
	 */
	public List<String> getAttendeeList(String filename) {
		List<String> attendeeList = new ArrayList<>();
		try {
			String line;
			BufferedReader file = new BufferedReader(new FileReader(filename));
			while((line = file.readLine()) != null) {
				StringTokenizer in = new StringTokenizer(line,"\n-%()�");
				while(in.hasMoreTokens()) {
					String token = in.nextToken();
					attendeeList.add(token);
					}
				}
			file.close();
			}
		catch(IOException exception) {
				System.out.println(exception.getMessage());
				}
		return attendeeList;
		}

	/**
	 *
	 * @param attendeeList the list of attendees.
	 * @return the list of winners.
	 */
	public List<String> getWinnerList(List<String> attendeeList) {
		List<String> winnerList = new ArrayList<>();
		int numberOfAttendees = attendeeList.size();
		List<Integer> numberList = new ArrayList<>(); // an array of numbers already randomly drawn
		for(int i = 0;i < numberOfItems;++i) {
			int number = (int)(Math.random() * numberOfAttendees);
			if(numberList.contains(number)) {
				--i;
				}
			else {
				String output = "The winner for drawing " + i + " is " + attendeeList.get(number) + " (attendee no. " + number + ").";
				winnerList.add(output);
				numberList.add(number);
				}
			}
		return winnerList;
		}

	/**
	 *
	 * @param list the list of registered attendees or the list of winners.
	 */
	public void writeToList(List<String> list) {
		int i = 0;
		for(String s: list) {
			System.out.println("[APP] " + i + ": " + s);
			++i;
			}
		}

	/**
	 *
	 * @param seconds the number of seconds for the application to pause.
	 */
	public void pauseApplication(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
			}
		catch(InterruptedException exception) {
			exception.getStackTrace();
			}
		}

	/**
	 *
	 * @param string the String to display in the opening splash screen.
	 */
	public static void displaySplashScreen(String string) {
		int length = string.length();

		System.out.println();
		System.out.print("[APP] ");
		for (int i = 0; i < length; ++i) {
			System.out.print("-");
			}
		System.out.println();
		System.out.println("[APP] " + string);
		System.out.print("[APP] ");
		for (int i = 0; i < length; ++i) {
			System.out.print("-");
			}
		System.out.println();
		}
	}
