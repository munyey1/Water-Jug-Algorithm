/*
 * This is a program that implements a depth-first search algorithm 
 * into the water-jug problem, that takes A, B, and C as input and 
 * generates the set of all possible states that can be reached from 
 * the start state (0,0,0).
 * 
 * A stack and a visited list will be implemented, where the visited
 * list is a 3D boolean array. The stack will store the states that 
 * are next to be explored, and the visited list will hold all the 
 * states that have already been previously explored. 
 * 
 * The visited list is a 3D boolean array because we are considering
 * a water-jug problem with three jugs. The lists indexes will 
 * correspond to the states of the jugs. E.g. If [4][3][2] is true 
 * in the visited list, then the state (4, 3, 2) is a possible state
 * in the problem.
 * 
 * The steps are:
 * 1)	Gather inputs from user
 * 2)	Initialise starting state (0,0,0) and push to stack
 * 3)	Pop state off stack 
 * 4)	Apply all operations to state if not in visited list, else go to 4)
 * 5)	Add popped state to visited list
 * 6)	Push all generated states to stack
 * 7)	Repeat steps 3-6 until stack is empty
 * 8)	Print all possible states, the count and time taken
 */

import java.util.*;

public class F128784Source {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		/*
		 * Gather user inputs where.
		 * A = Max capacity of jug 1,
		 * B = Max capacity of jug 2,
		 * C = Max capacity of jug 3.
		 */
		System.out.print("A: ");
		int jug1 = s.nextInt();
		
		System.out.print("B: ");
		int jug2 = s.nextInt();
		
		System.out.print("C: ");
		int jug3 = s.nextInt();
		
		long startTime = System.currentTimeMillis();
		
		//Enter the search function with the user inputs
		search(jug1, jug2, jug3);
		
		long totalTime = System.currentTimeMillis() - startTime;
		
		System.out.printf("Time Taken: %dms", totalTime);
		
		s.close();
	}
	
	//Search function
	public static void search(int jug1, int jug2, int jug3) {
		//Initialise the stack of type JugState as we are working with Objects
		Stack <JugState> stack = new Stack<JugState>();
		
		//Initialise the visited list
		boolean[][][] visited = new boolean[jug1 + 1][jug2 + 1][jug3 + 1];
		
		int count = 0;
		String result = "{";
		
		//Initialise the stating state (0, 0, 0) and push to the stack
		JugState initialState = new JugState(0, 0, 0);
		stack.push(initialState);
		
		//Repeat until stack is empty
		while(!stack.empty()) {
			//Remove top element and explore the returned state
			JugState currentState = stack.pop();
			
			/*
			 * If the current state has been not been visited explore it,
			 * else ignore it
			 */
			if(!visited[currentState.A][currentState.B][currentState.C]) {
				
				//Add to visited list as we will be exploring it
				visited[currentState.A][currentState.B][currentState.C] = true;
				
				//Add the states to the string
				result += "(" + currentState.A + "," + currentState.B + "," + currentState.C + "),";
				
				count += 1;
				
				//All possible operations/actions that can be performed
				//Fill Jugs but keep other jugs the same
				stack.push(new JugState(jug1, currentState.B, currentState.C));
				stack.push(new JugState(currentState.A, jug2, currentState.C));
				stack.push(new JugState(currentState.A, currentState.B, jug3));
				
				/*
				 * Transfer water to and from jugs
				 * 
				 * waterRemaining - how much remaining water 
				 * is left in the receiving jug
				 * 
				 * waterToTransfer - amount of water to transfer, used Math.min
				 * to choose the correct amount to transfer to avoid spillages and 
				 * negative results
				 * 
				 * a1 - amount of water in jug that loses water
				 * 
				 * a2 - amount of water in jug that gains water
				 * 
				 * A to B
				 */
				int waterRemaining = jug2 - currentState.B;
				int waterToTransfer = Math.min(currentState.A, waterRemaining);
				int a1 = currentState.A - waterToTransfer;
				int a2 = currentState.B + waterToTransfer;
				stack.push(new JugState(a1, a2, currentState.C));
				
				//A to C
				waterRemaining = jug3 - currentState.C;
				waterToTransfer = Math.min(currentState.A, waterRemaining);
				a1 = currentState.A - waterToTransfer;
				a2 = currentState.C + waterToTransfer;
				stack.push(new JugState(a1, currentState.B, a2));
				
				//B to A
				waterRemaining = jug1 - currentState.A;
				waterToTransfer = Math.min(currentState.B, waterRemaining);
				a1 = currentState.B - waterToTransfer;
				a2 = currentState.A + waterToTransfer;
				stack.push(new JugState(a2, a1, currentState.C));
				
				//B to C
				waterRemaining = jug3 - currentState.C;
				waterToTransfer = Math.min(currentState.B, waterRemaining);
				a1 = currentState.B - waterToTransfer;
				a2 = currentState.C + waterToTransfer;
				stack.push(new JugState(currentState.A, a1, a2));
				
				//C to A
				waterRemaining = jug1 - currentState.A;
				waterToTransfer = Math.min(currentState.C, waterRemaining);
				a1 = currentState.C - waterToTransfer;
				a2 = currentState.A + waterToTransfer;
				stack.push(new JugState(a2, currentState.B, a1));
				
				//C to B
				waterRemaining = jug2 - currentState.B;
				waterToTransfer = Math.min(currentState.C, waterRemaining);
				a1 = currentState.C - waterToTransfer;
				a2 = currentState.B + waterToTransfer;
				stack.push(new JugState(currentState.A, a2, a1));
				
				//Empty Jugs but keep the others the same
				stack.push(new JugState(0, currentState.B, currentState.C));
				stack.push(new JugState(currentState.A, 0, currentState.C));
				stack.push(new JugState(currentState.A, currentState.B, 0));
			}	
		}
		//Print all possible states, remove the last comma
		if(result.length() > 9) {
			result += result.substring(0, result.length() - 1) + "}";
		}else {
			result = "{}";
		}
		System.out.println(result);
		System.out.printf("\nTotal States: %d\n", count);
	}
}
