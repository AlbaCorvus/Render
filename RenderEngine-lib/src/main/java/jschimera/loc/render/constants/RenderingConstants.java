package jschimera.loc.render.constants;

public class RenderingConstants {
	
	public static final int POS_SIZE = 2;
	public static final int COLOR_SIZE = 4;
	public static final int TEX_COORDS_SIZE = 2;
	public static final int TEX_ID_SIZE = 1;
	public static final int ENTITY_ID_SIZE = 1;

	public static final int POS_OFFSET = 0;
	public static final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
	public static final int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
	public static final int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
	public static final int ENTITY_ID_OFFSET = TEX_ID_OFFSET + TEX_ID_SIZE * Float.BYTES;
	public static final int VERTEX_SIZE = 10;
	public static final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;
}
