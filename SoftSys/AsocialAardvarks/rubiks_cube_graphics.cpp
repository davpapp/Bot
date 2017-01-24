#include <GL/glut.h>
#include <iostream>

struct Cube {
	float vertices[8][3];
	float face_colors[6][3];
};

Cube cubes[3][3][3];

void quad(Cube cube)
{
	glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
      // Top face (y = 1.0f)
      // Define vertices in counter-clockwise (CCW) order with normal pointing out
      glColor3f(0.0f, 1.0f, 0.0f);     // Green
      glVertex3f(cube.vertices[2][0], cube.vertices[2][1], cube.vertices[2][2]);
      glVertex3f(cube.vertices[5][0], cube.vertices[5][1], cube.vertices[5][2]);
      glVertex3f(cube.vertices[7][0], cube.vertices[7][1], cube.vertices[7][2]);
      glVertex3f(cube.vertices[4][0], cube.vertices[4][1], cube.vertices[4][2]);
 
      // Bottom face (y = -1.0f)
      glColor3f(1.0f, 0.5f, 0.0f);     // Orange
      glVertex3f(cube.vertices[0][0], cube.vertices[0][1], cube.vertices[0][2]);
      glVertex3f(cube.vertices[3][0], cube.vertices[3][1], cube.vertices[3][2]);
      glVertex3f(cube.vertices[6][0], cube.vertices[6][1], cube.vertices[6][2]);
      glVertex3f(cube.vertices[1][0], cube.vertices[1][1], cube.vertices[1][2]);
 
      // Front face  (z = 1.0f)
      glColor3f(1.0f, 0.0f, 0.0f);     // Red
      glVertex3f(cube.vertices[0][0], cube.vertices[0][1], cube.vertices[0][2]);
      glVertex3f(cube.vertices[1][0], cube.vertices[1][1], cube.vertices[1][2]);
      glVertex3f(cube.vertices[5][0], cube.vertices[5][1], cube.vertices[5][2]);
      glVertex3f(cube.vertices[2][0], cube.vertices[2][1], cube.vertices[2][2]);
 
      // Back face (z = -1.0f)
      glColor3f(1.0f, 1.0f, 0.0f);     // Yellow
      glVertex3f(cube.vertices[3][0], cube.vertices[3][1], cube.vertices[3][2]);
      glVertex3f(cube.vertices[4][0], cube.vertices[4][1], cube.vertices[4][2]);
      glVertex3f(cube.vertices[7][0], cube.vertices[7][1], cube.vertices[7][2]);
      glVertex3f(cube.vertices[6][0], cube.vertices[6][1], cube.vertices[6][2]);
 
      // Left face (x = -1.0f)
      glColor3f(0.0f, 0.0f, 1.0f);     // Blue
      glVertex3f(cube.vertices[0][0], cube.vertices[0][1], cube.vertices[0][2]);
      glVertex3f(cube.vertices[2][0], cube.vertices[2][1], cube.vertices[2][2]);
      glVertex3f(cube.vertices[4][0], cube.vertices[4][1], cube.vertices[4][2]);
      glVertex3f(cube.vertices[3][0], cube.vertices[3][1], cube.vertices[3][2]);
 
      // Right face (x = 1.0f)
      glColor3f(1.0f, 0.0f, 1.0f);     // Magenta
      glVertex3f(cube.vertices[1][0], cube.vertices[1][1], cube.vertices[1][2]);
      glVertex3f(cube.vertices[6][0], cube.vertices[6][1], cube.vertices[6][2]);
      glVertex3f(cube.vertices[7][0], cube.vertices[7][1], cube.vertices[7][2]);
      glVertex3f(cube.vertices[5][0], cube.vertices[5][1], cube.vertices[5][2]);
   glEnd();  // End of drawing color-cube
}

void initializeCubes() {
	for (int x = 0; x < 3; x++) {
		for (int y = 0; y < 3; y++) {
			for (int z = 0; z < 3; z++) {
				cubes[x][y][z].vertices[0][0] = x;
				cubes[x][y][z].vertices[0][1] = y;
				cubes[x][y][z].vertices[0][2] = z;

				cubes[x][y][z].vertices[1][0] = x + 0.9;
				cubes[x][y][z].vertices[1][1] = y;
				cubes[x][y][z].vertices[1][2] = z;

				cubes[x][y][z].vertices[2][0] = x;
				cubes[x][y][z].vertices[2][1] = y + 0.9;
				cubes[x][y][z].vertices[2][2] = z;

				cubes[x][y][z].vertices[3][0] = x;
				cubes[x][y][z].vertices[3][1] = y;
				cubes[x][y][z].vertices[3][2] = z + 0.9;

				cubes[x][y][z].vertices[4][0] = x;
				cubes[x][y][z].vertices[4][1] = y + 0.9;
				cubes[x][y][z].vertices[4][2] = z + 0.9;

				cubes[x][y][z].vertices[5][0] = x + 0.9;
				cubes[x][y][z].vertices[5][1] = y + 0.9;
				cubes[x][y][z].vertices[5][2] = z;

				cubes[x][y][z].vertices[6][0] = x + 0.9;
				cubes[x][y][z].vertices[6][1] = y;
				cubes[x][y][z].vertices[6][2] = z + 0.9;

				cubes[x][y][z].vertices[7][0] = x + 0.9;
				cubes[x][y][z].vertices[7][1] = y + 0.9;
				cubes[x][y][z].vertices[7][2] = z + 0.9;
			}
		}
	}
}

void colorcube() {
	//quad(cubes[0][0][0]);
	for (int x = 0; x < 3; x++) {
		for (int y = 0; y < 3; y++) {
			for (int z = 0; z < 3; z++) {
				quad(cubes[x][y][z]);
			}
		}
	}
}

void setVertex(Cube& cube, int vertex1, int vertex2) {
	cube.vertices[vertex1][0] = cube.vertices[vertex2][0];
	cube.vertices[vertex1][1] = cube.vertices[vertex2][1];
	cube.vertices[vertex1][2] = cube.vertices[vertex2][2];
}

void rotateIndividualCube(Cube& cube, int direction) {
	// Rotate individual cube left
	if (direction == 1) {
		float temp_a = cube.vertices[0][0];
		float temp_b = cube.vertices[0][1];
		float temp_c = cube.vertices[0][2];

		setVertex(cube, 0, 2);
		setVertex(cube, 2, 5);
		setVertex(cube, 5, 1);

		cube.vertices[1][0] = temp_a;
		cube.vertices[1][1] = temp_b;
		cube.vertices[1][2] = temp_c;

		temp_a = cube.vertices[3][0];
		temp_b = cube.vertices[3][1];
		temp_c = cube.vertices[3][2];

		setVertex(cube, 3, 4);
		setVertex(cube, 4, 7);
		setVertex(cube, 7, 6);

		cube.vertices[6][0] = temp_a;
		cube.vertices[6][1] = temp_b;
		cube.vertices[6][2] = temp_c;
	}
	// Rotate individual cube up
	else if (direction == 3) {
		float temp_a = cube.vertices[0][0];
		float temp_b = cube.vertices[0][1];
		float temp_c = cube.vertices[0][2];

		setVertex(cube, 0, 3);
		setVertex(cube, 3, 4);
		setVertex(cube, 4, 2);

		cube.vertices[2][0] = temp_a;
		cube.vertices[2][1] = temp_b;
		cube.vertices[2][2] = temp_c;

		temp_a = cube.vertices[1][0];
		temp_b = cube.vertices[1][1];
		temp_c = cube.vertices[1][2];

		setVertex(cube, 1, 6);
		setVertex(cube, 6, 7);
		setVertex(cube, 7, 5);

		cube.vertices[5][0] = temp_a;
		cube.vertices[5][1] = temp_b;
		cube.vertices[5][2] = temp_c;
	}
	// Clockwise rotation
	else if (direction == 5) {
		float temp_a = cube.vertices[0][0];
		float temp_b = cube.vertices[0][1];
		float temp_c = cube.vertices[0][2];

		setVertex(cube, 0, 2);
		setVertex(cube, 2, 5);
		setVertex(cube, 5, 1);

		cube.vertices[1][0] = temp_a;
		cube.vertices[1][1] = temp_b;
		cube.vertices[1][2] = temp_c;

		temp_a = cube.vertices[3][0];
		temp_b = cube.vertices[3][1];
		temp_c = cube.vertices[3][2];

		setVertex(cube, 3, 4);
		setVertex(cube, 4, 7);
		setVertex(cube, 7, 6);

		cube.vertices[6][0] = temp_a;
		cube.vertices[6][1] = temp_b;
		cube.vertices[6][2] = temp_c;
	}
}

void setCubeLocation(Cube& cube, Cube cube2) {
	for (int i = 0; i < 8; i++) {
		cube.vertices[i][0] = cube2.vertices[i][0];
		cube.vertices[i][1] = cube2.vertices[i][1];
		cube.vertices[i][2] = cube2.vertices[i][2];
	}
}

void rotateFront() {
	// Corners
	Cube temp_cube = cubes[0][0][0];
	setCubeLocation(cubes[0][0][0], cubes[0][2][0]);
	setCubeLocation(cubes[0][2][0], cubes[2][2][0]);
	setCubeLocation(cubes[2][2][0], cubes[2][0][0]);
	setCubeLocation(cubes[2][0][0], temp_cube);

	rotateIndividualCube(cubes[0][0][0], 1);
	rotateIndividualCube(cubes[0][2][0], 1);
	rotateIndividualCube(cubes[2][0][0], 1);
	rotateIndividualCube(cubes[2][2][0], 1);

	// Edges
	temp_cube = cubes[1][0][0];
	setCubeLocation(cubes[1][0][0], cubes[0][1][0]);
	setCubeLocation(cubes[0][1][0], cubes[1][2][0]);
	setCubeLocation(cubes[1][2][0], cubes[2][1][0]);
	setCubeLocation(cubes[2][1][0], temp_cube);

	rotateIndividualCube(cubes[1][0][0], 1);
	rotateIndividualCube(cubes[0][1][0], 1);
	rotateIndividualCube(cubes[2][1][0], 1);
	rotateIndividualCube(cubes[1][2][0], 1);
}

void rotateTop() {
	// Corners
	/*
	Cube temp_cube = cubes[0][2][0];
	setCubeLocation(cubes[0][0][0], cubes[0][2][0]);
	setCubeLocation(cubes[0][2][0], cubes[2][2][0]);
	setCubeLocation(cubes[2][2][0], cubes[2][0][0]);
	setCubeLocation(cubes[2][0][0], temp_cube);

	rotateIndividualCube(cubes[0][0][0], 1);
	rotateIndividualCube(cubes[0][2][0], 1);
	rotateIndividualCube(cubes[2][0][0], 1);
	rotateIndividualCube(cubes[2][2][0], 1);

	// Edges
	temp_cube = cubes[1][0][0];
	setCubeLocation(cubes[1][0][0], cubes[0][1][0]);
	setCubeLocation(cubes[0][1][0], cubes[1][2][0]);
	setCubeLocation(cubes[1][2][0], cubes[2][1][0]);
	setCubeLocation(cubes[2][1][0], temp_cube);

	rotateIndividualCube(cubes[1][0][0], 1);
	rotateIndividualCube(cubes[0][1][0], 1);
	rotateIndividualCube(cubes[2][1][0], 1);
	rotateIndividualCube(cubes[1][2][0], 1);*/
}

double rotate_y = 0; 
double rotate_x = 0;
void specialKeys( int key, int x, int y ) 
{
    if (key == GLUT_KEY_RIGHT) {
        rotate_y += 5;
    }
    else if (key == GLUT_KEY_LEFT)
        rotate_y -= 5;
    else if (key == GLUT_KEY_UP)
        rotate_x += 5;
    else if (key == GLUT_KEY_DOWN)
        rotate_x -= 5;
    else if (key == GLUT_KEY_F5) {
    	rotateFront();
    }
    glutPostRedisplay();
}

void display()
{
    glClearColor( 0, 0, 0, 1 );
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    glMatrixMode( GL_PROJECTION );
    glLoadIdentity();
    int w = glutGet( GLUT_WINDOW_WIDTH );
    int h = glutGet( GLUT_WINDOW_HEIGHT );
    gluPerspective( 60, w / h, 0.1, 100 );

    glMatrixMode( GL_MODELVIEW );
    glLoadIdentity();
    gluLookAt
        ( 
        3, 3, 3, 
        0, 0, 0,
        0, 0, 1
        );

    glRotatef( rotate_x, 1.0, 0.0, 0.0 );
    glRotatef( rotate_y, 0.0, 1.0, 0.0 );
    
    colorcube();

    glutSwapBuffers();
}

int main( int argc, char **argv )
{
	initializeCubes();
    glutInit( &argc, argv );
    glutInitDisplayMode( GLUT_RGBA | GLUT_DEPTH | GLUT_DOUBLE );
    glutInitWindowSize( 640, 480 );
    glutCreateWindow( "Rubik's Cube" );
    glutDisplayFunc( display );
    glutSpecialFunc( specialKeys );
    glEnable( GL_DEPTH_TEST );
    glutMainLoop();
    return 0;
}