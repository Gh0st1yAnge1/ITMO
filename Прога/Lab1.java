package ProgaLabs;

public class Lab1 {
    public static void main(String[] args) {

        short[] w = new short[16];
        for(int i = 0; i < 16; i++){
            w[i] = (short) (i+5);
        }

        double[] x = new double[20];
        for (int i = 0; i < x.length; i++){
            x[i] = -15.0 + (12.0 - (-15.0)) * Math.random();
        }

        double[][] wx = new double[16][20];
        for(int i = 0; i < wx.length; i++){
            for(int j = 0; j < wx[i].length; j++){
                switch (w[i]){
                    case 7:
                        wx[i][j] = Math.cos(Math.tan(Math.pow(x[j], x[j]*(x[j] + 0.25))));
                        break;
                    case 9,10,11,13,14,15,16,18:
                        wx[i][j] = Math.cos(Math.log(Math.abs(x[j])));
                        break;
                    default:
                        double ground  = 1/(Math.pow(Math.E, 2*((Math.pow(2*((Math.abs(x[j]) + 1)/Math.abs(x[j])), 2)) + 1) / Math.PI));
                        double sky = Math.pow(Math.atan(Math.pow(Math.E, -Math.abs(x[j]))), (Math.pow(2/(x[j] - 1/3), x[j]/3)) + 1/2);
                        wx[i][j] = Math.atan(Math.pow(ground, sky));
                        break;
                }
            }
        }

        for(int i = 0; i < wx.length; i++) {
            for (int j = 0; j < wx[i].length; j++) {
                String result = String.format("%.3f", wx[i][j]);
                System.out.print(result + " ");
            }
            System.out.println(" ");
        }
    }
}
