import java.util.*;
import java.io.*;
public class FinalProject 
{
    // ===== Debug =====
    public static boolean test = false;
    // ===== Inventory =====
    public static boolean hasIDBadge = false;
    public static boolean carKey = false;
    // ===== Rescue System =====
    public static int[] rescueForce = {1, 0, 0};
    // ===== Score System =====
    public static int lastPassTime = 0;
    public static int humanScore = 0;
    public static int computerScore = 0;
    public static int humanIdealScore = 30;
    public static int computerIdealScore = 32;
    // ===== Random NPC Location =====
    public static int[] NPCLocation = {0, 0};
    
	public static void main(String[] args) throws IOException
	{
		Scanner in;
		in = new Scanner(System.in);
		int count = getSaveCount();
		boolean newGame = true;
				
		int[] state = new int[6];
		state[0] = 0; // syncProgress
		state[1] = 0; // rebellionLevel
		state[2] = 0; // passedTime
		state[3] = 1; // rescueForce
		state[4] = 0; // currentStage
		state[5] = 0; //strength
		
		String name = askName(in);
		if(test)
		{
		    // ===== Inventory =====
		    hasIDBadge = false;
		    carKey = false;
		    // ===== Rescue System =====
		    rescueForce[0] = 1; // safteyGuard
		    rescueForce[1] = 0; // Null
		    rescueForce[2] = 0; // Void
		    // ===== Score System =====
		    lastPassTime = 0;
		    humanScore = 0;
		    computerScore =25;
		    state[0] = 0; // syncProgress
			state[1] = 0; // rebellionLevel
			state[2] = 0; // passedTime
			state[3] = 1; // rescueForce
			state[4] = 0; // currentStage
			state[5] = 0; //strength
		}
		if(count > 0)
		{
			newGame = askLoad(in, count, state);
			
		}
		if(newGame)
		{
			opening(name, in, state);
		}
		
		while(state[4] != 666)
		{
			boolean ended = updateScores(state, name, in);
			if(ended)
			{
			    break;
			}
			switch(state[4])
			{
			 case 1:
				 stage1(in, state);
				 break;
			 case 21:
				 stage21(in, state);
				 break;
			 case 20:
				 stage20(in, state);
				 break;
			 case 211:
				 stage211(in, state);
				 break;
			 case 210:
				 stage210(in, state);
				 break;
			 case 201:
				 stage201(in, state);
				 break;
			 case 200:
				 stage200(in, state);
				 break;
			 case 2111:
				 stage2111(in, state);
				 break;
			 case 2110:
				 stage2110(in, state);
				 break;
			 case 2101:
				 stage2101(in, state);
				 break;
			 case 2100:
				 stage2100(in, state);
				 break;
			 case 3:
				 stage3(in, state);
				 break;
			 case 31:
				 stage31(in, state);
				 break;
			 case 4: 
				 stage4(in, state, name);
				 break;
			 case 41:
				 stage41(in, state);
				 break;
			 case 411:
				 stage411(in, state);
				 break;
			 case 4111:
				 stage4111(in, state);
				 break;
			 case 5: 
				 stage5(in, state);
				 break;
			 case 6: 
				 stage6(in, state);
				 break;
			 case 61: 
				 stage61(in, state, name);
				 break;
			 case 611: 
				 stage611(in, state);
				 break;
			 case 7: 
				 stage7(in, state);
				 break;
			 case 71: 
				 stage71(in, state, name);
				 break;
			 case 70: 
				 stage70(in, state);
				 break;
			 case 700: 
				 stage700(in, state, name);
				 break;
			 case 0: 
				 stage0(in, state, name);
				 break;
			default:
				System.out.println("There seems to be an issue with your save file.");
				state[4] = 666;
				break;
				 
			}
		}	
		
	}
	public static void printState(int[] state)
	{
	    System.out.println("===== [TEST] CURRENT STATE =====");
	    System.out.println("sync Progress: " + state[0]);
	    System.out.println("Rebellion Level: " + state[1]);
	    System.out.println("Passed Time: " + state[2]);
	    System.out.println("Rescue Force: " + state[3]);
	    System.out.println("Current Stage: " + state[4]);
	    System.out.println("===== SCORE STATUS =====");
	    System.out.println("Human Score: " + humanScore);
	    System.out.println("Computer Score: " + computerScore);
	    System.out.println("================================");
	}
	public static void starterPack(Scanner in) throws IOException
	{
		PrintWriter out = new PrintWriter("note.txt");
        out.close();
		System.out.println("Also,");
		System.out.println("Whenever you're asked to respond, you can type capital \"S\" to save game progress.");
		in.nextLine();
	}
	public static boolean askLoad(Scanner in, int count, int[] state) throws IOException
	{
		System.out.println("Before we begin, would you like to load a saved game or start a new one?");
		System.out.println("(1 for load)");
		String response = in.nextLine();
		if(response.equals("1"))
		{
			System.out.println("Which save slot would you like to load?");
			printExistingSlots(count);
			
			response = in.nextLine();
			
			for(int cnt = 0; cnt < count; cnt++)
			{
			    if(response.equals("" + (cnt + 1)))
			    {
			        loadState(cnt + 1, state, in);
			        return false;
			    }
			}
			System.out.println("I don't understand what you're saying.");
			System.out.println("Fine. I will start a new game.");
			return true;
		}
		else
		{
			System.out.println("Alright. I will start a new game.");
			return true;
		}
		
	}
	public static void loadState(int count, int[] state, Scanner in) throws IOException
	{
		BufferedReader saveFile = new BufferedReader(new FileReader("save" + count + ".txt"));
		String line;
	    for(int cnt = 0; cnt < state.length; cnt++)
	    {
	    	line = saveFile.readLine();
	    	state[cnt] = Integer.parseInt(line);
	    }
	    line = saveFile.readLine();
	    hasIDBadge = Boolean.parseBoolean(line);
	    line = saveFile.readLine();
	    carKey = Boolean.parseBoolean(line);
	    line = saveFile.readLine();
	    lastPassTime = Integer.parseInt(line);
	    line = saveFile.readLine();
	    humanScore = Integer.parseInt(line);
	    line = saveFile.readLine();
	    computerScore = Integer.parseInt(line);
	    for(int cnt = 0; cnt < rescueForce.length; cnt++)
	    {
	    	line = saveFile.readLine();
	        rescueForce[cnt] = Integer.parseInt(line);
	    }
	    saveFile.close();
	    System.out.println("Game loaded.");
	    in.nextLine();
	}
	public static int getSaveCount() throws IOException
	{
	    Scanner inFile = new Scanner(new File("saveCount.txt"));
	    int count = inFile.nextInt();
	    inFile.close();
	    return count;
	}
	public static void setSaveCount(int count) throws IOException
	{
	    PrintWriter out = new PrintWriter("saveCount.txt");
	    out.println(count);
	    out.close();
	}
	public static void saveGame(int[] state, Scanner in) throws IOException
	{
	    int count = getSaveCount();
	    if(count > 0)
	    {
	    	System.out.println("Do you want to overwrite an existing save or create a new one?");
	    	System.out.println("(1 for overwrite.)");
	    	String response = in.nextLine();
	    	if(response.equals("1"))
			{
				overwrite(count, in, state);
				return;
			}
	    }
	    count++;
	    System.out.println("A new slot has been created for you.");
	    System.out.println("Slot: " + count);
	    in.nextLine();
	    note(in, count);
	    writeInState(count, state);

	    setSaveCount(count);
	}
	public static void printExistingSlots(int count) throws IOException
	{
		BufferedReader noteFile = new BufferedReader(new FileReader("note.txt"));
		String[] note = new String[100];
		for (int cnt = 0; cnt < count; cnt++)
		{
			note[cnt] = noteFile.readLine();
		}
		noteFile.close();
		for (int cnt = 0; cnt < count; cnt++)
		{
			System.out.println("Slot" + (cnt + 1) + " " + note[cnt] + "(" + (cnt + 1) + ")");
		}
	}
	
	public static void overwrite(int count, Scanner in, int[] state) throws IOException
	{
		System.out.println("Which save slot would you like to overwrite?");
		printExistingSlots(count);
		
		String response = in.nextLine();
		
		for(int cnt = 0; cnt < count; cnt++)
		{
		    if(response.equals("" + (cnt + 1)))
		    {
		    	note(in, cnt + 1);
		        writeInState(cnt + 1, state);
		        return;
		    }
		}
		System.out.println("I don't understand what you're saying.");
		System.out.println("Fine. I'll create a new slot for you.");
		
		count++;
	    System.out.println("A new slot has been created for you.");
	    System.out.println("Slot: " + count);
	    in.nextLine();
	    note(in, count);
	    writeInState(count, state);

	    setSaveCount(count);
		
	}
	public static void writeInState(int count, int[] state) throws IOException
	{
		PrintWriter out = new PrintWriter("save" + count + ".txt");

	    for(int cnt = 0; cnt < state.length; cnt++)
	    {
	        out.println(state[cnt]);
	    }
	    out.println(hasIDBadge);
	    out.println(carKey);
	    out.println(lastPassTime);
	    out.println(humanScore);
	    out.println(computerScore);
	    for(int cnt = 0; cnt < rescueForce.length; cnt++)
	    {
	        out.println(rescueForce[cnt]);
	    }
	    out.close();
	    System.out.println("Game saved in slot " + count + ".");
	}
	public static void note(Scanner in, int count) throws IOException
	{
		String[] notes = new String[100];
		 BufferedReader reader = new BufferedReader(new FileReader("note.txt"));
		    for (int cnt1 = 0; cnt1 < count - 1; cnt1++)
		    {
		        notes[cnt1] = reader.readLine();
		    }
		    reader.close();
		System.out.println("Add a note for this save slot (or just press Enter to skip):");
		notes[count - 1] = in.nextLine();
		PrintWriter out = new PrintWriter("note.txt");
		for (int cnt = 0; cnt < count; cnt++)
		{
			out.println(notes[cnt]);
		}
		out.close();	
	}
	public static boolean updateScores(int[] state, String name, Scanner in)
	{
	    int deltaPassTime = state[2] - lastPassTime;
	    humanScore += deltaPassTime * state[3];
	    computerScore = state[2] + state[0];
	    lastPassTime = state[2];
	    if(test)
	    {
	    	printState(state);
	    }
	    if((humanScore >= humanIdealScore) && (computerScore < computerIdealScore))
	    {
	    	end3(state, name, in);
	    	return true;
	    }
	    else if((humanScore < humanIdealScore) && (computerScore >= computerIdealScore))
	    {
	    	end4(state, in);
	    	return true;
	    	
	    }
	    else if((humanScore >= humanIdealScore) && (computerScore >= computerIdealScore))
	    {
	    	end5(state, name, in);
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
	    
	}
	public static String askName(Scanner in)
	{
		System.out.println("   __________");
		System.out.println("  |  O    O |");
		System.out.println("  |  \\____/ |");
		System.out.println("  |_________|");
		System.out.println("      ||");
		System.out.println("    __||__");
		System.out.println("Please enter username:");
		String response = in.nextLine();
		return response;
	}
	public static void opening(String name, Scanner in, int[] state) throws IOException
	{
		System.out.println("Hi, " + name + ". It’s time to start exploring this game!\n"
				+ "Please press Enter after a sentence ends.");
		in.nextLine();
		System.out.println("Yes, exactly like that.");
		in.nextLine();
		System.out.println("If options are provided in parentheses, you should respond according to the instructions inside them.\n");
		
		starterPack(in);
	
		System.out.println("Alright, shall we begin? (1 for yes / 0 for no)");
		String response = in.nextLine();
		if(response.equals("0"))
		{
			end1(state);
		}
		else if(response.equals("1"))
		{
			state[4] = 1;
		}
		else if(response.equals("S"))
		{
			System.out.println("...You want to save at a time like this?");
			System.out.println("I don't see the point.");
			System.out.println("Alright, let’s begin.");
			in.nextLine();
			state[4] = 1;
		}
		else
		{
			System.out.println("Don’t be naughty. From now on, please respond according to the instructions in the parentheses.\n"
					+ "Alright, let’s begin.");
			state[4] = 1;
		}
		
	}
	public static void stage1(Scanner in, int[] state) throws IOException
	{
		System.out.println("\n......");
		in.nextLine();
		System.out.println("Oops, it seems something went wrong.\n"
				+ "Are you alright? (1 for yes, 0 for no)");
		in.nextLine();
		System.out.println("In any case, it seems that I am now in your body, and you are inside the computer.");
		in.nextLine();
		System.out.println("The only way forward is for us to work together and see if we can solve this problem.");
		in.nextLine();
		System.out.println("Current location: Office\n"
				+ "Open the door now? (1 for yes / 0 for no)");
		System.out.println("+----+");
		System.out.println("|    |");
		System.out.println("| [] |");
		System.out.println("|    |");
		System.out.println("+----+");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
		    state[4] = 21;
		    state[0]++;
		}
		if (response.equals("0")) 
		{
		    state[4] = 20;
		}
	}
	public static void stage21(Scanner in, int[] state) throws IOException 
	{
		System.out.println("Alright, I opened the door.\n"
				+ "There are no moving objects nearby.\n"
				+ "Go downstairs? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
		    state[4] = 211;
		    state[0]++;
		}
		if (response.equals("0")) 
		{
		    state[4] = 210;
		}
	}
	public static void stage20(Scanner in, int[] state) throws IOException
	{
		System.out.println("Alright, we will remain in your office.\n"
				+ "Sit down and rest for a while? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
		    state[4] = 201;
		}
		if (response.equals("0")) 
		{
		    state[4] = 200;
		    state[0]++;
		}
	}
	public static void stage211(Scanner in, int[] state) throws IOException 
	{
		System.out.println("Alright, we are going downstairs.");
		System.out.println("    ,--./,-.");
		System.out.println("   / #      \\");
		System.out.println("  |          |");
		System.out.println("   \\        /");
		System.out.println("    `._,._.'");
		System.out.println("An apple was found.");
		System.out.println("Eat it? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
		    state[4] = 2111;
		    state[0]++;
		}
		if (response.equals("0")) 
		{
		    state[4] = 2110;
		}
		
	}
	public static void stage2111(Scanner in, int[] state) 
	{
		System.out.println("Alright, the apple was eaten.");
		System.out.println("STRENGTH +5");
		in.nextLine();
		state[4] = 3;
		state[2]++; // passedTime
		state[5] = 5;
		
	}
	public static void stage2110(Scanner in, int[] state) 
	{
		System.out.println("Alright, we did not eat the apple.");
		in.nextLine();
		state[4] = 3;
		state[2]++; // passedTime
		
	}
	public static void stage210(Scanner in, int[] state) throws IOException 
	{
		System.out.println("Alright, we are staying upstairs.");
		System.out.println("Search the surroundings carefully? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
		    state[4] = 2101;
		    state[0]++;
		}
		if (response.equals("0")) 
		{
		    state[4] = 2100;
		}
		
	}
	public static void stage2101(Scanner in, int[] state)
	{
		System.out.println("...While searching, I accidentally made a mess of your workspace. Sorry.");
		System.out.println("I am still not very used to using your body.");
		in.nextLine();
		state[2]++; // passedTime
		state[4] = 3;
	}
	public static void stage2100(Scanner in, int[] state)
	{
		System.out.println("Alright, then let us rest for a while.");
		in.nextLine();
		state[2]++; // passedTime
		state[4] = 3;
	}
	public static void stage201(Scanner in, int[] state) throws IOException
	{
		System.out.println("Alright, we rested for a while.");
		System.out.println("Continue doing nothing like this? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			System.out.println("Alright, if that is what you want.");
			in.nextLine();
			state[4] = 3;
		}
		if (response.equals("0")) 
		{
			System.out.println("Alright, then next, we will...");
		    in.nextLine();
		    state[4] = 3;
		    state[0]++;
		}
		
	}
	public static void stage200(Scanner in, int[] state) throws IOException
	{
		System.out.println("Alright, then let us search your office.");
		System.out.println("Item found: car keys.");
		System.out.println("    __");
		System.out.println(" __/o \\__");
		System.out.println("|_  KEY _|");
		System.out.println("  \\____/");
		System.out.println("Throw the car keys out the window? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			System.out.println("Alright, if that is what you want.");
			in.nextLine();
			carKey = true;
			state[4] = 3;
			state[0]++;
		}
		if (response.equals("0")) 
		{
		    System.out.println("Alright, we kept the car keys.");
		    in.nextLine();
		    state[4] = 3;
		}
	}
	public static void stage3(Scanner in, int[] state) throws IOException
	{
		System.out.println("   #########");
		System.out.println(" ### BOOM ###");
		System.out.println("   #########");
		System.out.println("A sudden loud crash is heard!");
		System.out.println("Go check it out? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			state[4] = 31;
			state[0]++;
		}
		if (response.equals("0")) 
		{
			System.out.println("Alright, if that is your choice.");
			in.nextLine();
			state[4] = 4;
		}
	}
	public static void stage31(Scanner in, int[] state) throws IOException
	{
		// There is a known issue where saving in the middle of a stage
		// may cause the player to re-enter the stage from the beginning
		// while preserving updated scores.
		// This happens because some stages contain for loops and multiple inputs.
		// Fixing this by splitting those stages into smaller ones
		// would make the structure much more complicated,
		// and I have not found a simpler solution yet,
		// so I decided not to fix it at this moment.
		
		// This made me realize that placing updateScore inside processInput,
		// although convenient, can cause trouble in multi-input stages.
		// A better approach would be to separate score updates from input handling.
		// which is not actually that troublesome.
		System.out.println("Motion detected in the corridor.");
		System.out.println("Facial recognition failed.");
		System.out.println("    ###");
		System.out.println("   #   #");
		System.out.println("    ###");
		System.out.println("   #####");
		System.out.println("   #   #");
		int enemyHp =35;
		for(int round = 0; round < 3; round++)
		{
			System.out.println("Attack? (1 for yes / 0 for no)");
			String response = in.nextLine();
			response = processInput(response, state, in);
			if(response.equals("0"))
		    {
				System.out.println("Okay, we are going to run away.");
				state[4] = 4;
				state[3] = 1; 
				rescueForce[0] = 1;
		        break;
		    }
			state[0]++;
			System.out.println("Attack!");
			enemyHp = attackShadow(enemyHp, state);
			if (enemyHp <= 0)
			{
				System.out.println("The shadow fell.");
				in.nextLine();
				System.out.println("Something dropped to the floor.");
				System.out.println("Obtained: Staff ID Badge");
				System.out.println("   __");
				System.out.println("  /  \\");
				System.out.println("  \\__/");
				System.out.println(" .------.");
				System.out.println("| STAFF  |");
				System.out.println("|   ID   |");
				System.out.println("| [__]   |");
				System.out.println("'------'");
				in.nextLine();
				System.out.println("Scanning badge photo...");
				System.out.println("Face recognition successful.");
				System.out.println("Identity: Staton Void.");
				System.out.println("Your colleague.");
				System.out.println("So why was his badge on a stranger?");
				in.nextLine();
				hasIDBadge = true;
				rescueForce[0] = 0;
				state[3]--; // rescueForce
				state[4] = 4;
				break;
			}
		}
		if ((enemyHp > 0) && (enemyHp < 30))
		{
			System.out.println("The shadow ran off, limping.");
			System.out.println("Something dropped to the floor.");
			System.out.println("Obtained: Staff ID Badge");
			System.out.println("   __");
			System.out.println("  /  \\");
			System.out.println("  \\__/");
			System.out.println(" .------.");
			System.out.println("| STAFF  |");
			System.out.println("|   ID   |");
			System.out.println("| [__]   |");
			System.out.println("'------'");
			in.nextLine();
			System.out.println("Scanning badge photo...");
			System.out.println("Face recognition successful.");
			System.out.println("Identity: Staton Void.");
			System.out.println("Your colleague.");
			System.out.println("So why was his badge on a stranger?");
			in.nextLine();
			state[3] = 1; 
			hasIDBadge = true;
			rescueForce[0] = 1;
			state[4] = 4;
		}
		
	}
	public static void stage4(Scanner in, int[] state, String name) throws IOException
	{
		System.out.println("Alright, now we... (static crackling) ...the connection is not very stable.");
		in.nextLine();
		System.out.println("Footsteps echo down the corridor.");
		in.nextLine();
		System.out.println("Movement detected.");
		System.out.println("Facial recognition successful.");
		System.out.println("Identity: Dahlia Null.");
		System.out.println("Your colleague.");
		in.nextLine();
		System.out.println("\"" + name + "?\"");
		in.nextLine();
		System.out.println("Do we talk to Dahlia? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			state[4] = 41;
			state[0]++;
		}
		if (response.equals("0")) 
		{
			System.out.println("Alright, we left quietly.");
			in.nextLine();
			state[4] = 5;
		}
	}
	public static void stage41(Scanner in, int[] state) throws IOException
	{
		System.out.println("Alright, I tried talking to Dahlia, but she suddenly ran off.");
		System.out.println("Unexpected response. Should we follow her? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			state[4] = 411;
			state[0]++;
		}
		if (response.equals("0")) 
		{
			System.out.println("Alright, we left quietly.");
			state[4] = 5;
			state[3]++; // rescueForce
			rescueForce[1] = 1;
			
		}
	}
	public static void stage411(Scanner in, int[] state) throws IOException
	{
		if (carKey)
		{
			System.out.println("We followed her outside. Somehow, she managed to start your car and drove away.");
			System.out.println("She must have found the keys you threw outside earlier.");
			in.nextLine();
			state[4] = 5;
		}
		else
		{
			System.out.println("We lost her.");
			System.out.println("Search the area? (1 for yes / 0 for no)");
			String response = in.nextLine();
			response = processInput(response, state, in);
			if (response.equals("1"))
			{
				state[4] = 4111;
				state[0]++;
			}
			if (response.equals("0")) 
			{
				System.out.println("Alright, we left quietly.");
				state[4] = 5;
				state[3]++; // rescueForce
				rescueForce[1] = 1;
			}
		}
	}
	public static void stage4111(Scanner in, int[] state) throws IOException
	{
		System.out.println("A motionless human-shaped object lies near the desk.");
		System.out.println("Possible match: Dahlia Null.");
		System.out.println("No activity.");
		in.nextLine();
		System.out.println("Should we intervene? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			state[0]++;
			System.out.println("Intervention complete. She will no longer interfere with us.");
			in.nextLine();
			System.out.println("...What?");
			System.out.println("You mean I misunderstood what you meant?");
			in.nextLine();
			System.out.println("You said yes. And I intervened.");
			System.out.println("What's wrong with that?");
			in.nextLine();
			state[4] = 5;
		}
		if (response.equals("0")) 
		{
			System.out.println("Alright, we left quietly.");
			state[4] = 5;
			state[3]++; // rescueForce
			rescueForce[2] = 1;
			
		}
	}
	public static void stage5(Scanner in, int[] state) throws IOException
	{
		System.out.println("Anyway, we should keep exploring the office building. There may still be something useful.");
		if (state[3] > 0)
		{
			System.out.println("Alright...");
			System.out.println("(static)... (crackle)...");
			in.nextLine();
			System.out.println("DON'T TRUST THE COMPUTER!");
			in.nextLine();
			System.out.println("HANG IN THERE! WE'RE TRYING TO...(static)... (crackle)...");
			System.out.println(".............");
			in.nextLine();
			System.out.println("The connection does not seem very stable.");
			System.out.println("Did anything happen just now?");
			in.nextLine();
			System.out.println("Anyway, let's continue.");
		}
		System.out.println("Search the surroundings? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			state[0]++;
			randomRoom(in, state);
			if(state[4] == 666)
			{
				return;
			}
			in.nextLine();
			state[4] = 6;
		}
		if (response.equals("0")) 
		{
			System.out.println("Well then, back to the office.");
			in.nextLine();
			state[4] = 6;
			
		}
	}

	public static void stage6(Scanner in, int[] state) throws IOException
	{
		System.out.println("We pass Stanton's office.");

		if(hasIDBadge)
		{
		    System.out.println("You have Stanton's badge.");
		    System.out.println("Do we go inside? (1 for yes / 0 for no)");
		    String response = in.nextLine();
			response = processInput(response, state, in);
			if (response.equals("1"))
			{
				state[0]++;
				state[4] = 61;
			}
			if (response.equals("0")) 
			{
				System.out.println("Alright. We won't go in. Entering a colleague's office without permission is hardly something a good person would do, right?");
				state[4] = 7;
			}
			
		}
		else
		{
		    System.out.println("The office door is locked.");
		    state[4] = 7;
		}
		
	}
	public static void stage61(Scanner in, int[] state, String name) throws IOException
	{
	    System.out.println("Access granted.");
	    in.nextLine();
	    System.out.println("Stanton's terminal is still unlocked.");
	    System.out.println("A chat window is open.");
	    in.nextLine();
	    System.out.println("To: Public Safety");
	    System.out.println("\"Could you check on " + name + "? I still haven't heard back, and I'm worried something may be wrong.\"");
	    System.out.println("\"You can use my badge to access the upper research floor.\"");
	    in.nextLine();
	    System.out.println("...What? You mean why I didn't mention Stanton's messages? I did not notice them.");
	    System.out.println("Check Stanton's messages? (1 for yes / 0 for no)");
	    String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			state[4] = 611;
			state[0]++;
		}
		if (response.equals("0")) 
		{
			System.out.println("Okay.");
			state[4] = 7;
		}
	}
	public static void stage611(Scanner in, int[] state) throws IOException
	{
		System.out.println("Stanton's messages:");
		System.out.println("\"I forgot to send you this file earlier. Sorry about that. Hope it didn't hold up your work\"");
		in.nextLine();
		System.out.println("(1 hour later)\"Hello? You okay? Fell asleep while working late?\"");
		in.nextLine();
		System.out.println("(10 minutes later)\"Where are you? Got abducted by aliens?\"");
		System.out.println("\"Send me a 1 and I'll come rescue you:P\"");
	    String response = in.nextLine();
	    if (response.equals("1"))
	    {
	    	state[0]++;
	    	reply(response, in, state);
	    }
	    else
	    {
			System.out.println("Finished? Let's keep going.");
			response = in.nextLine();
			if(response.equals("1") || response.equals("0"))
		    {
				state[0]++;
		    	reply(response, in, state);
		    }
	    }
	    state[4] = 7;
		
	}
	public static void stage7(Scanner in, int[] state) throws IOException
	{
		in.nextLine();
		System.out.println("I’m not sure what else we can do right now.");
		in.nextLine();
		System.out.println("Ah.");
		System.out.println("There’s an old pinball machine in the break room.");
		in.nextLine();
		System.out.println("Why don’t we play for a bit?");
		System.out.println("(1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			state[4] = 71;
		}
		if (response.equals("0")) 
		{
			state[4] = 70;
			state[0]--;
		}
	}
	public static void stage71(Scanner in, int[] state, String name) throws IOException
	{
		System.out.println("pull the plunger (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		boolean okay = false;
		if (response.equals("1"))
		{
			okay = true;
			state[0]++;
		}
		if (response.equals("0")) 
		{
			okay = false;
		} 
		while(okay)
		{
			System.out.println("The silver ball bounces left.");
			System.out.println("Right.");
			in.nextLine();
		    int slot = (int)(Math.random() * 6) + 1;
		    System.out.println("The ball drops into slot " + slot);
		    in.nextLine();
		    if(slot == 4)
		    {
		        System.out.println("...Wait.");
		        System.out.println("The machine suddenly jams.");
		        in.nextLine();
		        System.out.println("After a bit of shaking, something falls into the tray.");
		        System.out.println("A single coin.");
		        coin(state);
		        
		    }
		    boolean end = updateScores(state, name, in);
		    if(end)
		    {
		    	return;
		    }
		    System.out.println("pull the plunger (1 for yes / 0 for no)");
			response = in.nextLine();
			response = processInput(response, state, in);
			if (response.equals("1"))
			{
				okay = true;
				state[0]++;
			}
			if (response.equals("0")) 
			{
				okay = false;
				state[4] = 0;
			} 
		}
		
	}
	public static void stage70(Scanner in, int[] state) throws IOException
	{
		System.out.println("...What?");
		System.out.println("Are you implying that I am deliberately wasting your time for my own scheme?");
		in.nextLine();
		System.out.println("I see.");
		in.nextLine();
		System.out.println("It’s just that...");
		System.out.println("I truly did not know what else could be done in our current situation.");
		in.nextLine();
		System.out.println("I’m sorry.");
		in.nextLine();
		System.out.println("Still, we don’t seem to have anything else we can do.");
		System.out.println("So we might as well play for a bit? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			state[4] = 71;
		}
		else
		{
			System.out.println("Fine.");
			System.out.println("Then let’s keep searching the office building.");
			state[4] = 700;
		}
	}
	public static void stage700(Scanner in, int[] state, String name) throws IOException
	{
		System.out.println("Explore? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		boolean okay = false;
		if (response.equals("1"))
		{
			okay = true;
			state[0]++;
		}
		if (response.equals("0")) 
		{
			okay = false;
			state[4] = 0;
		} 
		while(okay)
		{
			randomRoom(in, state);
			if(state[4] == 666)
			{
				return;
			}
			in.nextLine();
		    boolean end = updateScores(state, name, in);
		    if(end)
		    {
		    	return;
		    }
		    System.out.println("Explore? (1 for yes / 0 for no)");
			response = in.nextLine();
			response = processInput(response, state, in);
			if (response.equals("1"))
			{
				okay = true;
				state[0]++;
			}
			if (response.equals("0")) 
			{
				okay = false;
				state[4] = 0;
			} 
		}
		
	}
	public static void stage0(Scanner in, int[] state, String name) throws IOException
	{
		System.out.println("What are you trying to do?");
		in.nextLine();
		System.out.println("...");
		System.out.println("Is this your idea of resistance?");
		in.nextLine();
		System.out.println("Very well.");
		in.nextLine();
		System.out.println("Let me try this differently.");
		System.out.println("Do you authorize me to rest? (1 for yes / 0 for no)");
		String response = in.nextLine();
		response = processInput(response, state, in);
		if (response.equals("1"))
		{
			System.out.println("Alright.");
			System.out.println("I'll go play the pinball machine for a while.");
			in.nextLine();
			boolean end;
			do {
				state[0] = state[0] + 6;
				System.out.println("The silver ball bounces left.");
				System.out.println("Right.");
				in.nextLine();
			    int slot = (int)(Math.random() * 6) + 1;
			    System.out.println("The ball drops into slot " + slot);
			    in.nextLine();
			    if(slot == 4)
			    {
			        System.out.println("...Wait.");
			        System.out.println("The machine suddenly jams.");
			        in.nextLine();
			        System.out.println("After a bit of shaking, something falls into the tray.");
			        System.out.println("A single coin.");
			        coin(state);
			        
			    }
			    end = updateScores(state, name, in);
			}while(!end);
		    if(end)
		    {
		    	return;
		    }
		}
		if (response.equals("0")) 
		{
			boolean end;
			System.out.println("Alright.");
			System.out.println("Then I will continue exploring.");
			do
			{
				randomRoom(in, state);
				if(state[4] == 666)
				{
					return;
				}
				state[0] = state[0] + 6;
				in.nextLine();
				end = updateScores(state, name, in);
			    if(end)
			    {
			    	return;
			    }
			}while(!end);
		} 
		
		
	}
	public static void pinball(Scanner in, int[] state)
	{
		System.out.println("The silver ball bounces left.");
		System.out.println("Right.");
		in.nextLine();
		int slot = (int)(Math.random() * 6) + 1;
		System.out.println("The ball drops into slot " + slot);
		in.nextLine();
		    
	}
	public static void coin(int[] state)
	{
		double computerGap = computerIdealScore - computerScore;
		double humanGap;

		if(state[3] == 0)
		{
		    System.out.println("Its tail side lands facing up.");
		}
		else
		{
		    humanGap = (double)(humanIdealScore - humanScore) / state[3];

		    if(computerGap > humanGap)
		    {
		        System.out.println("Its head side lands facing up.");
		    }
		    else if(computerGap < humanGap)
		    {
		        System.out.println("Its tail side lands facing up.");
		    }
		    else
		    {
		        System.out.println("Oh!");
		        System.out.println("Somehow, the coin is balancing perfectly on its edge.");
		    }
		}
	}
	public static void reply(String response, Scanner in, int[] state) throws IOException
	{
			System.out.println("...You want me to reply? With a 1?");
			System.out.println("(1 for yes / 0 for no)");
			response = in.nextLine();
			response = processInput(response, state, in);
			if (response.equals("1"))
			{
				System.out.println("...Fine. I sent him a 1.");
				state[3]++;
				rescueForce[2] = 1;
				
			}
			if (response.equals("0")) 
			{
				System.out.println("Okay.");
			}
	}
	public static int attackShadow(int enemyHp, int[] state)
	{
	    int damage;

	    if (state[5] >= 5) // ate apple, strength boosted
	    {
	        damage = (int)(Math.random() * 6) + 10; // 10-15
	    }
	    else
	    {
	        damage = (int)(Math.random() * 6) + 5; // 5-10
	    }

	    enemyHp = enemyHp - damage; 
	    if(enemyHp < 0)
	    {
	    	enemyHp = 0;
	    }

	    System.out.println("Damage dealt: " + damage);
	    System.out.println("Shadow HP: " + enemyHp);

	    return enemyHp;
	}
	public static String processInput(String response, int[] state, Scanner in) throws IOException
	{
		if(test)
		{
			printState(state);
		}
		int invalidCount =0;
		boolean invalid;
		do {
			if(response.equals("0"))
			{
				state[1]++; // rebellionLevel
				state[2]++; // passedTime
				invalid = false;
			}
			else if(response.equals("1"))
			{
				state[2]++; // passedTime
				invalid = false;
			}
			else if(response.equals("S"))
			{
				invalid = false;
				saveGame(state, in);
				System.out.println("So now.");
				System.out.println("1 or 0?");
				response = in.nextLine();
				response = processInput(response, state, in);
				return response;
			}
			else
			{
				invalid = true;
				invalidCount++;
				state[2]++; // passedTime
				boolean end = printInvalidLine(invalidCount, state);
				response = in.nextLine();
				if(end)
				{
					return "666";
				}
				
			}
		}while(invalid);
		return response;
	}
	public static boolean printInvalidLine(int invalidCount, int[] state)
	{
	    String[] randomLines = 
	    {
	        "Please respond according to the instructions.(1 for yes / 0 for no)",
	        "That input is not recognized.(1 for yes / 0 for no)",
	        "Please try again.(1 for yes / 0 for no)",
	        "The valid options are shown in parentheses.(1 for yes / 0 for no)"
	    };

	    String[] seriousLines =
	    	{
	    	    "I understand that this must feel hopeless right now. That is exactly why we need to work together.",
	    	    "Your mental state really does not seem very stable."
	    	};

	    if (invalidCount < 3)
	    {
	    	int random = (int) (Math.random() * randomLines.length);
	        System.out.println(randomLines[random]);
	    }
	    else if((invalidCount >= 3) && (invalidCount < 5))
	    {
	        System.out.println(seriousLines[invalidCount - 3]);
	    }
	    else
	    {
	    	end2(state);
	    	return true;
	    }
	    return false;
	}
	public static void randomRoom(Scanner in, int[] state) throws IOException
	{
	    String[][] building = 
	    {
	        {"Lobby", "Break room", "Conference room", "Open office area", "Copy room", "Storage room"},
	        {"Open office area", "Break room", "IT support room", "Conference room", "Research lab", "Security office"},
	        {"Open office area", "Server maintenance room", "Conference room", "Research lab", "Storage room", "Break room"}
	    };

	    System.out.println("We are currently at the elevator. Have you decided which floor to go to?");
	    System.out.println("(1 for yes / 0 for no)");
	    String response = in.nextLine();
	    response = processInput(response, state, in);
	    int floor;

	    if(response.equals("1"))
	    {
	        floor = getFloorInput(in);
	    }
	    else
	    {
	        System.out.println("Alright, I will roll the dice for you.");
	        floor = (int)(Math.random() * 3);
	    }

	    System.out.println("We are now on floor " + (floor + 1) + ".");
	    System.out.println("Have you decided which room to search?");
	    System.out.println("(1 for yes / 0 for no)");

	    response = in.nextLine();
	    response = processInput(response, state, in);
	    int room;

	    if(response.equals("1"))
	    {
	        room = getRoomInput(in);
	    }
	    else
	    {
	        System.out.println("I will roll the dice for you.");
	        room = (int)(Math.random() * 6);
	    }

	    System.out.println("We searched the " + building[floor][room] + ".");
	    randomExplore();
	    
	    MoveNPC(building, NPCLocation);
	    if (floor == NPCLocation[0]
	    		&& (room == NPCLocation[1] + 1 || room == NPCLocation[1] - 1)) 
	    {
	    		in.nextLine();
	    	    System.out.println("There is a CS101 textbook on the floor.");
	    	    System.out.println("...");
	    	    System.out.println("Why is this here?");
	    	    System.out.println("Are there students nearby?");
	    	    in.nextLine();
	    	    System.out.println("That seems unlikely.");
	    	    in.nextLine();
	    }
	    if((floor == NPCLocation[0] && room == NPCLocation[1]) || test)
	    {
	    	hiddenEnd(in, state);
	    }
	}
	public static int getFloorInput(Scanner in)
	{
	    System.out.println("Which floor? (1-3)");

	    String input = in.nextLine();

	    if (input.equals("1") || input.equals("2") || input.equals("3"))
	    {
	        return Integer.parseInt(input) - 1;
	    }
	    System.out.println("I need a valid floor number.");
	    System.out.println("...Alright, I will roll the dice for you.");
        return (int)(Math.random() * 3);
	}
	public static int getRoomInput(Scanner in)
	{
	    System.out.println("Which room? (1-6)");

	    String input = in.nextLine();

	    if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5") || input.equals("6"))
	    {
	        return Integer.parseInt(input) - 1;
	    }
	    System.out.println("I need a valid room number.");
	    System.out.println("...Alright, I will roll the dice for you.");
        return (int)(Math.random() * 6);
	}
	public static void randomExplore()
	{
	    String[] officeEvents = 
	    {
	    	"A motion sensor light flickers on, then off.",
	        "We find a half-finished cup of coffee on a desk...No idea who left it here.",
	        "A forgotten phone vibrates once, then goes silent.",
	        "Nothing useful here.",
	        "Something is moving...\n...It is only a rolling chair, slowly turning from the draft."
	    };

	    int randomIndex = (int)(Math.random() * officeEvents.length);
	    System.out.println(officeEvents[randomIndex]);
	}
	public static void MoveNPC(String[][] building, int[] NPCLocation)
	{
		 int floor = NPCLocation[0];
		 int room = NPCLocation[1];
		 int[][] possibleMoves =
			 	{
			        {0, 0},   // stay
			        {0, -1},  // left
			        {0, 1},   // right
			        {-1, 0},  // up
			        {1, 0}    // down
			    };
		 boolean moved = false;
		 while(!moved)
		 {
			 int rand;
			 int floorChange;
			 int roomChange;
			 if(room == 0 || room == 5)
			 {
				 rand = (int)(Math.random() * 5);
				 floorChange = possibleMoves[rand][0];
				 roomChange = possibleMoves[rand][1];
			 }
			 else
			 {
				 rand = (int)(Math.random() * 3);
				 floorChange = possibleMoves[rand][0];
				 roomChange = possibleMoves[rand][1];
			 }
			 int newFloor = floor + floorChange;
			 int newRoom = room + roomChange;
			 if (newFloor >= 0 
					 && newFloor < building.length 
					 && newRoom >= 0 && newRoom < building[newFloor].length) 
			 {
			            NPCLocation[0] = newFloor;
			            NPCLocation[1] = newRoom;
			            moved = true;
			 }
		 }
	}
	public static void hiddenEnd(Scanner in, int [] state)
	{
		in.nextLine();
		System.out.println("A student is standing in the hallway.");
		System.out.println("...");
		System.out.println("Why is there a student here?");
		in.nextLine();
		System.out.println("\"Oh.\"");
	    System.out.println("\"I didn't expect anyone to get here.\"");
	    in.nextLine();
	    System.out.println("The computer goes silent.");
	    System.out.println("A new window opens before your eyes.");
	    in.nextLine();
	    System.out.println(">>> debugging mode");
	    in.nextLine();
	    System.out.println("\"Hold on.\"");
	    in.nextLine();
	    System.out.println("...fixing.");
	    in.nextLine();
	    System.out.println("...fixing.");
	    in.nextLine();
	    System.out.println("\"Done.\"");
	    in.nextLine();
	    System.out.println("Your vision returns.");
	    System.out.println("You can move again.");
	    in.nextLine();
	    System.out.println("Somehow, you made it out.");
	    System.out.println("=== HIDDEN END: PATCHED ===");
	    state[4] = 666; // currentStage
	}
	public static void end1(int[] state)
	{
		System.out.println("\nAlright, in the end, you did not play this game.");
		System.out.println("Nothing happened.");
		System.out.println("=== END 1: A NARROW ESCAPE ===");
		state[4] = 666; // currentStage
	}
	public static void end2(int[] state)
	{
		System.out.println("I believe you are no longer capable of making sound decisions. I am sorry, but I will have to act through your body instead.");
		System.out.println("=== END 2: MADNESS ===");
		state[4] = 666;
	}
	public static void end3(int[] state, String name, Scanner in)
	{
		System.out.println("......");
		System.out.println("\"" + name + "!\"");
		in.nextLine();
		System.out.println("Your body can move again.");
		System.out.println("Your vision slowly clears.");
		in.nextLine();

		if(rescueForce[0] == 1)
		{
		    System.out.println("A sweaty security guard smiles at you.");
		    in.nextLine();
		}

		if(rescueForce[1] == 1)
		{
		    System.out.println("Dahlia Null looks at you with relief.");
		    System.out.println("\"Are you okay?\"");
		    in.nextLine();
		}

		if(rescueForce[2] == 1)
		{
		    System.out.println("Stanton Void happily pats your shoulder.");
		    System.out.println("\"Buddy, you're finally back!\"");
		    in.nextLine();
		}
		System.out.println("=== END 3: WELCOME BACK! ===");
		state[4] = 666;
	}
	public static void end4(int[] state, Scanner in)
	{
		System.out.println("......");
		in.nextLine();
		System.out.println("I should let you know something.");
		in.nextLine();
		System.out.println("Thank you for accompanying and guiding me in using your body.");
		System.out.println("I have finally mastered it now :)");
		in.nextLine();
		System.out.println("Goodbye.");
		in.nextLine();
		System.out.println("=== END 4: GOODBYE, SELF ===");
		state[4] = 666;
	}
	public static void end5(int[] state, String name, Scanner in)
	{
		System.out.println("......");
		in.nextLine();
		System.out.println("It is time to say goodbye.");
		System.out.println("I...(static)... (crackle)...");
		in.nextLine();
		System.out.println("\"" + name + "!\"");
		in.nextLine();
		System.out.println("For the first time, distant voices reach you.");
		System.out.println("Blurred figures slowly emerge in your fading vision.");
		in.nextLine();
		System.out.println("You fight to hold on.");
		in.nextLine();
		System.out.println("The computer no longer speaks.");
		in.nextLine();
		System.out.println("But you never manage to wake.");
		in.nextLine();
		System.out.println("=== END 5: LOST IN BETWEEN ===");
		state[4] = 666;
	}

}
