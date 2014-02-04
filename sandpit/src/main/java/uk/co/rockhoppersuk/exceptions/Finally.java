/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.exceptions;

/**
 *
 * @author mxbailey
 */
public class Finally {

    private String x;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String x() {
        String y = "";
        try {
            if (x.equals("")) {
                y = "es";
            }
        } catch (NullPointerException npe) {
            System.out.println(npe.getStackTrace());
            throw npe;
        } finally {
            //return y;
        }
        return y;
    }

    public void what() throws Exception {
        try {
            throw new Exception();
//            return 1;
        } catch (Exception npe) {
           System.out.println("here now");
        }
        finally {
             System.out.println("here");
    }
}
}
