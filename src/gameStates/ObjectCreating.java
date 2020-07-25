package gameStates;

@FunctionalInterface
public interface ObjectCreating<A, B, C, D, E, F, G> {
	A create(B xStart, C yStart, D world, E addInf, F level, G offset);
}
