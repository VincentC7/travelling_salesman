package Structure_Donnees;



public class Matrix {

    private Integer [][] symetrical_matrix;
    int length;
    private Matrix(Integer [][]matrix){
        this.symetrical_matrix = matrix;
        this.length = this.symetrical_matrix.length;

    }
    public static Matrix generate_random_matrix(int taille){
        Integer [][] matrix = new Integer [taille][taille];
        for(int i = 0; i<taille;i++){
            matrix[i][i]=0;
            for(int j =i+1;j<taille;j++){
                matrix[i][j] = (int)(Math.random()*Graphe.MAX_LENGTH)+Graphe.MIN_LENGTH;

            }

        }
        return new Matrix(matrix);
    }

    /**
     * get the number in a specific row and column
     * if you want column or row 1 indicate 0
     * @param column column number
     * @param row row number
     * @return value of the intersection between row and column and -1 if ArrayIndexOutOfBoundsException
     */
    public Integer getNumber(int row,int column){
        if(column<row){
            int tmp = column;
            column = row;
            row = tmp;

        }
        try {
            return this.symetrical_matrix[row][column];
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return -1;
        }


    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Integer [] i : this.symetrical_matrix){
            for (Integer j : i){
                s.append(j).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
