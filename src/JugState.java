/*
 * The states in the water-jug problem will be objects
 * which they have 3 attributes, A, B, and C, which represent
 * the amount of water in the jugs.
 */

public class JugState {
	public int A;
	public int B;
	public int C;

	public JugState(int A, int B, int C) {
		this.A = A;
		this.B = B;
		this.C = C;
	}
}
