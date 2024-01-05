package es.unex.pi.util;

public class Pair<F, S> {
	private F first; // first member of triplet
	private S second; // second member of triplet

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public void setFirst(F first) {
		this.first = first;
	}

	public void setSecond(S second) {
		this.second = second;
	}

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}
}