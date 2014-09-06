package helpers;

import javax.media.opengl.GL2;

public class SquareModel extends Model {

	@Override
	public void setInternals(GL2 gl) {
		this.points = new float[4*3];
		points[0] = 0;	//Lower Left
		points[1] = 0;
		points[2] = 0;
		points[3] = 1;	//Lower Right
		points[4] = 0;
		points[5] = 0;
		points[6] = 1;	//Upper Right
		points[7] = 0;
		points[8] = 1;
		points[9] = 0;	//Upper Left
		points[10] = 0;
		points[11] = 1;
		
		this.texCoords = new float[4*2];
		texCoords[0] = 0;	//Lower Left
		texCoords[1] = 0;
		texCoords[2] = 1;	//Lower Right
		texCoords[3] = 0;
		texCoords[4] = 1;	//Upper Right
		texCoords[5] = 1;
		texCoords[6] = 0;	//Upper Left
		texCoords[7] = 1;
		
		this.indexes = new int[6];
		indexes[0] = 0;		//Lower Left
		indexes[1] = 2;		//Lower Right
		indexes[2] = 1;		//Upper Right
		indexes[3] = 0;		//Lower Left
		indexes[4] = 2;		//Upper Right
		indexes[5] = 3;		//Upper Left
	}

}
