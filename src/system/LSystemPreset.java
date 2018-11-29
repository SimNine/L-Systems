package system;

public enum LSystemPreset {
	ALGAE,
	BINARY_TREE,
	CANTOR_SET,
	KOCH_CURVE,
	KOCH_SNOWFLAKE,
	SIERPINSKI,
	DRAGON_CURVE,
	FRACTAL_PLANT,
	RIVER_LONG,
	RIVER_BRANCHING,
	CUSTOM;
	
    private static LSystemPreset[] vals = values();
    
    public LSystemPreset next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}